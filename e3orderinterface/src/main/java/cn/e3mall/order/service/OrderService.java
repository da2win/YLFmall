package cn.e3mall.order.service;

import cn.e3mall.common.util.E3Result;
import cn.e3mall.order.pojo.OrderInfo;

/**
 *
 * @author Darwin
 * @date 2018/4/27
 */
public interface OrderService {

    E3Result createOrder(OrderInfo orderInfo);
}
