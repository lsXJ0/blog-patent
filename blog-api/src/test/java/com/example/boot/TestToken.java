package com.example.boot;

import com.example.boot.dao.domain.SysUser;
import com.example.boot.utils.JWTUtils;
import com.example.boot.utils.UserThreadLocal;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * @author Luo_xiuxin
 * @create 2021-10-02 16:06
 */
@SpringBootTest
public class TestToken {

    @Test
    public void TestToken(){
        String salt="LuoXiuXin@qq.com";
        String number="luoxiuxin";
        String password = DigestUtils.md5Hex(number + salt);
        System.out.println("----------------------");
        System.out.println(password);
        System.out.println("21a1417f1a18e45d683a9060eb056edb");
        System.out.println("----------------------");

    }

    @Test
    public void TestToken2(){

        String token="eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzQxMzU2MjIsInVzZXJJZCI6MTQzNzYwMzg4NTYyMjUxNzc2MSwiaWF0IjoxNjMzMjQ2NTg5fQ.kmKqcQ3GL_Ms-jZvmRb76dMCdwytFg4l3jEVKFStT1k";
        Map<String, Object> map = JWTUtils.checkToken(token);
        System.out.println("-----------------------");
        System.out.println(map);
        System.out.println("-----------------------");
    }

    @Test
    public void testThrows(){
        SysUser user = UserThreadLocal.get();
        System.out.println("--------------------------");
        System.out.println(user);
        System.out.println("--------------------------");
    }
}
