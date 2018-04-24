package cn.e3mall.sso.service;

import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbUser;

/**
 *
 * @author Darwin
 * @date 2018/4/23
 */
public interface RegisterService {

    E3Result checkData(String param, int type);

    E3Result register(TbUser user);
}
