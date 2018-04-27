package cn.e3mall.order.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author Darwin
 * @date 2018/4/26
 */
@Controller
public class OrderController {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request) {
        // Get user id.
        TbUser user = (TbUser) request.getAttribute("user");
        List<TbItem> cartList = cartService.getCartList(user.getId());
        request.setAttribute("cartList", cartList);
        // Return to page.
        return "order-cart";
    }

    @RequestMapping(value = "/order/create", method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, HttpServletRequest request) {
        // Get User-Inf
        TbUser user = (TbUser) request.getAttribute("user");
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        E3Result result = orderService.createOrder(orderInfo);
        if (result.getStatus() == 200) {
            // Empty cart.
            cartService.clearCartItem(user.getId());
        }
        // Pass the order Id to the page.
        request.setAttribute("orderId", result.getData());
        request.setAttribute("payment", orderInfo.getPayment());
        // return to page.
        return "success";
    }
}
