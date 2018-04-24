package cn.e3mall.sso.service;

import cn.e3mall.common.util.E3Result;

/**
 *
 * @author Darwin
 * @date 2018/4/23
 */
public interface LoginService {
    /**
     * Parameter: username, password
     *
     * Business logic
     * 1. Determine whether the username and password are correct.
     * 2. If it's not correct. return login failed.
     * 3. If it's correct, generate token.
     * 4. Write user info to redis. Key: token, value: user info.
     * 5. Set the expiration time of the Session.
     * 6. Return token.
     *
     * Response value: E3Result. It contains token info.
     */
    E3Result userLogin(String username, String password);
}
