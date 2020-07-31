package cn.caishen.domain.auth;

import cn.caishen.domain.auth.entity.Payload;
import cn.caishen.domain.auth.entity.UserInfo;
import cn.caishen.domain.auth.jwtUtils.JwtUtil;
import cn.caishen.domain.auth.jwtUtils.RsaUtils;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class AuthTest {

    private String privateFilePath = "D:\\Code\\dayo_parent\\temp\\ssh\\id_rsa";
    private String publicFilePath = "D:\\Code\\dayo_parent\\temp\\ssh\\id_rsa.pub";

    @Test
    public void testRSA() throws Exception {
        // 生成密钥对
        RsaUtils.generateKey(publicFilePath, privateFilePath, "hello", 2048);

        // 获取私钥
        PrivateKey privateKey = RsaUtils.getPrivateKey(privateFilePath);
        System.out.println("privateKey = " + privateKey);
        // 获取公钥
        PublicKey publicKey = RsaUtils.getPublicKey(publicFilePath);
        System.out.println("publicKey = " + publicKey);
    }


    @Test
    public void testJWT() throws Exception {
        // 获取私钥
        PrivateKey privateKey = RsaUtils.getPrivateKey(privateFilePath);
        // 生成token
        String token = JwtUtil.generateTokenExpireInMinutes(new UserInfo("123", "18801031984", "guest"), privateKey, 5);
        System.out.println("token = " + token);

        // 获取公钥
        PublicKey publicKey = RsaUtils.getPublicKey(publicFilePath);
        // 解析token
        Payload<UserInfo> info = JwtUtil.getInfoFromToken(token, publicKey, UserInfo.class);

        System.out.println("info.getExpiration() = " + info.getExpiration());
        System.out.println("info.getUserInfo() = " + info.getUserInfo());
        System.out.println("info.getId() = " + info.getId());
    }
}
