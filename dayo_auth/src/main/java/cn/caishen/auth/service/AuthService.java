package cn.caishen.auth.service;

import cn.caishen.domain.utils.LbMap;

public interface AuthService {

    LbMap loginAuth(String username, String password);
}
