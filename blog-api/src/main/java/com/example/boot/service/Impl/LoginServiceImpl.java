package com.example.boot.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.boot.dao.domain.LoginParams;
import com.example.boot.dao.domain.SysUser;
import com.example.boot.dao.vo.ErrorCode;
import com.example.boot.dao.vo.LoginUserVo;
import com.example.boot.dao.vo.Result;
import com.example.boot.mapper.SySUserMapper;
import com.example.boot.service.LoginService;
import com.example.boot.service.SySUserService;
import com.example.boot.utils.JWTUtils;
import com.example.boot.utils.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Luo_xiuxin
 * @create 2021-10-02 11:17
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SySUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SySUserService sysuserservice;

    //加密盐，用于将密码信息加密存储和查询
    private static final String salt="LuoXiuXin@qq.com";

    @Override
    public Result Login(LoginParams loginParams) {
        /**
         * 1.检查参数是否合法
         * 2.验证账号密码是否存在于user中
         * 3.如果不存在则登陆失败
         * 4.如果存在则用jwt生成对应的token返回给前端
         * 5.token放入Redis中，Redis token：user信息 设置过期时间
         * (登陆认证的时候先认证token是否存合法，到Redis中认证是否存在)
         */
        String account = loginParams.getAccount();
        String password = DigestUtils.md5Hex(loginParams.getPassword() + salt);
        if (StringUtils.isBlank(account) ||StringUtils.isBlank(loginParams.getPassword())){
            //如果不存在则输出错误提示
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),
                    ErrorCode.PARAMS_ERROR.getMsg());
        }
        //根据账户密码查询用户
        SysUser user=sysUserMapper.SelectByUsernameOrPassword(account,password);
        if (user==null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),
                    ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        //账户存在，则用userId来生成token
        String token = JWTUtils.createToken(user.getId());
        //将token当做key来绑定账户信息,然后将token信息存储在redis中
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);
        //将用户信息存入userthreadLocal中
        log.info("The Token is:{"+token+"}");
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        /**
         * 1.判断token是否合法，
         *      判断token是否为空
         *     若不为空则解析该token
         *     并且判断解析token是否成功
         *     判断redis是否存在该token
         *若错误则返回空值，成功则返回user
         */

        if (StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> checkToken = JWTUtils.checkToken(token);
        if (checkToken==null){
            return null;
        }
        //将存在token里的信息取出来
        String userJson = (String) redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        //若成功则返回loginUserVo信息
        SysUser user = JSON.parseObject(userJson, SysUser.class);
        return user;
    }

    @Override
    public Result LoginOut(String token) {

        redisTemplate.delete("TOKEN_" + token);
        //将存入的user移除掉
        return Result.success(null);
    }

    @Override
    public Result register(LoginParams loginParams) {
        /**
         * 1.判断数据是否合法
         * 2、判断账户是否存在
         * 3.若是不存在则开始注册
         * 4.生成token
         * 5、生成Redis并保存
         * 6.添加事物、一旦发生错误事物回滚，停止注册
         */
        String password = loginParams.getPassword();
        String account = loginParams.getAccount();
        String nickname = loginParams.getNickname();
        if (StringUtils.isBlank(account)||StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        //将数据的密码加密
        String PSW=DigestUtils.md5Hex(loginParams.getPassword() + salt);
        SysUser sysUser = sysUserMapper.SelectByUsernameOrPassword(account, PSW);
        //若账户存在则返回错误信息
        if (sysUser!=null){
            return Result.fail(ErrorCode.USER_IS_EXIST.getCode(), ErrorCode.USER_IS_EXIST.getMsg());
        }
        //将数据填入User中并且创建user对象
        SysUser user = SetUserAll(account, nickname, PSW);
        //生成对应的token
        String token = JWTUtils.createToken(user.getId());
        //将user绑定到对应的token中
        redisTemplate.opsForValue().set("TOKEN_"+token,JSON.toJSONString(user),1,TimeUnit.DAYS);
        //添加@Transactional为指定事物回滚
        return Result.success(token);
    }
    //创建user用户
    private SysUser SetUserAll(String account, String nickname, String PSW) {
        SysUser user=new SysUser();
        user.setAccount(account);
        user.setNickname(nickname);
        user.setPassword(PSW);
        user.setAdmin(1);
        user.setCreateDate(System.currentTimeMillis());
        user.setSalt("LuoXiuXin@qq.com");
        user.setLastLogin(System.currentTimeMillis());
        user.setEmail("");
        user.setDeleted(0);
        //添加随机头像
        int num=(int)(1+Math.random()*93);
        user.setAvatar("/static/img/random/"+num+".jpg");
        //存入user库中
        this.sysuserservice.save(user);
        return user;
    }
}
