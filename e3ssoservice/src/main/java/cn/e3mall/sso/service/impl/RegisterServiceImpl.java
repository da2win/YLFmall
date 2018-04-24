package cn.e3mall.sso.service.impl;

import cn.e3mall.common.util.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * User register handler
 * @author Darwin
 * @date 2018/4/23
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbUserMapper userMapper;

    @Override
    public E3Result checkData(String param, int type) {
        // Generate different query conditions according to different types.
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        // 1. username 2. phone-number 3. email
        if (type == 1) {
            criteria.andUsernameEqualTo(param);
        } else if (type == 2) {
            criteria.andPhoneEqualTo(param);
        } else if (type == 3) {
            criteria.andEmailEqualTo(param);
        } else {
            E3Result.build(400, "数据类型错误");
        }
        // execute query.
        List<TbUser> users = userMapper.selectByExample(example);
        // The judgement result contains data.
        if (users != null && users.size() > 0) {
            return E3Result.ok(false);
        }
        // if there is data returned true, other it returns true.
        return E3Result.ok(true);
    }

    @Override
    public E3Result register(TbUser user) {
        // Data validity check,
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())
                || StringUtils.isBlank(user.getPhone())) {
            return E3Result.build(400, "用户数据不完整, 插入失败!");
        }
        E3Result e3Result = checkData(user.getUsername(), 1);
        if (! (boolean)e3Result.getData()) {
            return E3Result.build(400, "此用户名已被占用!");
        }
        e3Result = checkData(user.getPhone(), 2);
        if (! (boolean)e3Result.getData()) {
            return E3Result.build(400, "此手机号已被占用!");
        }
        // complementing the attributes of POJO.
        user.setCreated(new Date());
        user.setUpdated(new Date());
        // Encrypt the password.
        String encrptedPwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(encrptedPwd);
        // Insert into database.
        userMapper.insert(user);
        // return to insert success.
        return E3Result.ok();
    }
}
