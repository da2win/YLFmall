package cn.e3mall.order.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.mapper.TbOrderItemMapper;
import cn.e3mall.mapper.TbOrderMapper;
import cn.e3mall.mapper.TbOrderShippingMapper;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbOrderItem;
import cn.e3mall.pojo.TbOrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Order handle service.
 * @author Darwin
 * @date 2018/4/27
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${ORDER_ID_GEN_KEY}")
    private String ORDER_ID_GEN_KEY;
    @Value("${ORDER_ID_START}")
    private String ORDER_ID_START;
    @Value("${ORDER_DETAIL_ID_GEN_KEY}")
    private String ORDER_DETAIL_ID_GEN_KEY;

    @Override
    public E3Result createOrder(OrderInfo orderInfo) {
        // Generate order id.
        if (!jedisClient.exists(ORDER_ID_GEN_KEY)) {
            jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_START);
        }
        String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
        orderInfo.setOrderId(orderId);
        // 1. Unpaid, 2. Paid, 3. Unshipped. 4. Shipped, 5. Success 6. Closed
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        // Insert into Order table.
        orderMapper.insert(orderInfo);
        // Insert into OrderItem table.
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem orderItem : orderItems) {
            // Generate Detail Id.
            String odId = jedisClient.incr(ORDER_DETAIL_ID_GEN_KEY).toString();
            orderItem.setId(odId);
            orderItem.setOrderId(orderId);
            orderItemMapper.insert(orderItem);
        }

        // Insert into Shipping table.
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);
        return E3Result.ok(orderId);
    }
}
