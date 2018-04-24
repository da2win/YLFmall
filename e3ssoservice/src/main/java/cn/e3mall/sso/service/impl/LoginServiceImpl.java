package cn.e3mall.sso.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.common.util.JsonUtils;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author Darwin
 * @date 2018/4/23
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public E3Result userLogin(String username, String password) {
        //1. Determine whether the username and password are correct.
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(example);
        //2. If it's not correct. return login failed.
        if (list == null || list.size() == 0) {
            // Return login failed.
            E3Result.build(400, "用户名或密码错误!");
        }
        TbUser user = list.get(0);
        // Judge whether the password is correct or not.
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            E3Result.build(400, "用户名或密码错误!");
        }
        //3. If it's correct, generate token.
        String token = UUID.randomUUID().toString();
        //4. Write user info to redis. Key: token, value: user info.
        user.setPassword(null);
        jedisClient.set("SESSION:" + token, JsonUtils.objectToJson(user));
        //5. Set the expiration time of the Session.
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        //6. Return token.
        return E3Result.ok(token);
    }
}
