package cn.e3mall.order.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request) {
        // Get user id.
        TbUser user = (TbUser) request.getAttribute("user");
        List<TbItem> cartList = cartService.getCartList(user.getId());
        request.setAttribute("cartList", cartList);
        // Return to page.
        return "order-cart";
    }
}
