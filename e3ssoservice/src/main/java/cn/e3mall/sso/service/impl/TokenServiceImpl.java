package cn.e3mall.sso.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.common.util.JsonUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Darwin
 * @date 2018/4/23
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private JedisClient jedisClient;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public E3Result getUserByToken(String token) {
        // 取不到用户信息, 登录已经过期
        String json = jedisClient.get("SESSION:" + token);
        if (StringUtils.isBlank(json)) {
            return E3Result.build(201, "用户登录已经过期");
        }
        // 取到用户信息, 更新过期时间
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);

        return E3Result.ok(user);
    }
}
