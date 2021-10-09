package com.example.boot.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.boot.dao.domain.SysUser;
import com.example.boot.dao.vo.ErrorCode;
import com.example.boot.dao.vo.LoginUserVo;
import com.example.boot.dao.vo.Result;
import com.example.boot.dao.vo.UserVo;
import com.example.boot.mapper.SySUserMapper;
import com.example.boot.service.LoginService;
import com.example.boot.service.SySUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Luo_xiuxin
 * @create 2021-10-01 22:39
 */
@Service
public class SySUserServiceImpl implements SySUserService {
    @Autowired
    private SySUserMapper sysusermapper;
    @Autowired
    private LoginService loginService;
    @Override
    public SysUser getSysUserByAuthorId(Long authorId) {
        return sysusermapper.selectById(authorId);
    }

    @Override
    public Result findUserByToken(String token) {
        /**
         * 1.判断token是否合法，
         *      判断token是否为空
         *      若不为空则解析该token
         *      并且判断解析token是否成功
         *      判断redis是否存在该token
         * 2.不合法则放回错误信息
         * 3.如果合法则放回到User中
         *
         */
        SysUser user =loginService.checkToken(token);
        if (user==null){
            //若错误则返回未登录信息
            return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }
        //将user信息转换为uservo信息
        LoginUserVo loginUserVo=new LoginUserVo();
        loginUserVo.setAccount(user.getAccount());
        loginUserVo.setAvatar(user.getAvatar());
        loginUserVo.setId(user.getId());
        loginUserVo.setNickname(user.getNickname());
        return Result.success(loginUserVo);
    }

    @Override
    public void save(SysUser user) {
        sysusermapper.insert(user);
    }

    @Override
    public UserVo getUserVoById(Long Id) {
        SysUser user = sysusermapper.selectById(Id);
        UserVo userVo=new UserVo();
        userVo.setAvatar(user.getAvatar());
        userVo.setNickname(user.getNickname());
        userVo.setId(user.getId());
        return userVo;
    }

}
