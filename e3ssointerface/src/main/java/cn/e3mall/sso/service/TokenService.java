package cn.e3mall.sso.service;

import cn.e3mall.common.util.E3Result;

/**
 *
 * Inquiry user-info by token
 * @author Darwin
 * @date 2018/4/23
 */
public interface TokenService {

    E3Result getUserByToken(String token);
}
