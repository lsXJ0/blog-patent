package com.example.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.boot.dao.domain.SysUser;
import org.springframework.stereotype.Service;

/**
 * @author Luo_xiuxin
 * @create 2021-10-01 22:37
 */
@Service
public interface SySUserMapper extends BaseMapper<SysUser> {
    SysUser SelectByUsernameOrPassword(String account, String password);

}
