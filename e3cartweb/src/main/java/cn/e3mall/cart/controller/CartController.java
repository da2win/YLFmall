package cn.e3mall.cart.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.util.CookieUtils;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.common.util.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Darwin
 * @date 2018/4/24
 */
@Controller
public class CartController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CartService cartService;

    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;

    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                          HttpServletRequest request, HttpServletResponse response) {
        // Determine if the user is logged in.
        TbUser user = (TbUser) request.getAttribute("user");
        // YES. write to redis.
        if (user != null) {
            // Save to server.
            cartService.addCart(user.getId(), itemId, num);
            return "cartSuccess";
        }
        // NO go on.
        // Pick up a list of shopping cart from cookie.
        List<TbItem> cartList = getCartListFromCookie(request);
        // Determine whether a commodity exists in the list of goods.
        boolean flag = false;
        for (TbItem item : cartList) {
            if (item.getId() == itemId.longValue()) {
                // If there is, the num is added.
                item.setNum(item.getNum() + num);
                flag = true;
                break;
            }
        }
        // If it doesn't exist, inquire about the commodity info according to the commidty ID.
        if (!flag) {
            // Get a TbItem obj.
            TbItem item = itemService.getItemById(itemId);
            // Set up the quantity of the goods.
            item.setNum(num);
            // Take one picture.
            String image = item.getImage();
            if (StringUtils.isNotBlank(image)) {
                item.setImage(image.split(",")[0]);
            }
            // Add goods to a list of goods.
            cartList.add(item);
        }
        // Write to cookie.
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),
                COOKIE_CART_EXPIRE, true);
        // Return to add a successful page.
        return "cartSuccess";
    }

    /**
     * Display shop cart
     * @param request
     * @return
     */
    @RequestMapping("/cart/cart")
    public String showCart(HttpServletRequest request, HttpServletResponse response) {
        List<TbItem> cartList = getCartListFromCookie(request);
        // Determine if the user is logged in.
        TbUser user = (TbUser) request.getAttribute("user");
        // YES.
        if (user != null) {
            // Get cart from cookie.
            // Merge the goods in redis and in cookie.
            cartService.mergeCart(user.getId(), cartList);
            // Remove shop cart from cookie.
            CookieUtils.deleteCookie(request, response, "cart");
            cartList = cartService.getCartList(user.getId());
        }
        // No.
        request.setAttribute("cartList", cartList);
        return "cart";
    }

    /**
     * Get a list of Goods from cookie.
     * @param request
     * @return
     */
    private List<TbItem> getCartListFromCookie(HttpServletRequest request) {
        String json = CookieUtils.getCookieValue(request, "cart", true);
        if (StringUtils.isBlank(json)) {
            return new ArrayList<>();
        }
        // Convert JSON into a list of Goods.
        List<TbItem> goodsList = JsonUtils.jsonToList(json, TbItem.class);
        return goodsList;
    }

    /**
     * Update shop cart commodity num.
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateCartNum(@PathVariable Long itemId, @PathVariable Integer num,
                                  HttpServletRequest request, HttpServletResponse response) {
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            cartService.updateCartNum(user.getId(), itemId, num);
            return E3Result.ok();
        }
        // Get shop cart
        List<TbItem> cartList = getCartListFromCookie(request);
        // Iterating the list of goods to find the corresponding goods.
        for (TbItem item : cartList) {
            if (item.getId().longValue() == itemId) {
                // Update num
                item.setNum(num);
                break;
            }
        }
        // Write to cookie.
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),
                COOKIE_CART_EXPIRE, true);

        return E3Result.ok();
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            cartService.deleteCartItem(user.getId(), itemId);
            return "redirect:/cart/cart.html";
        }
        List<TbItem> cartList = getCartListFromCookie(request);
        for (TbItem item : cartList) {
            if (item.getId().longValue() == itemId) {
                cartList.remove(item);
                break;
            }
        }
        // Write to cookie.
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),
                COOKIE_CART_EXPIRE, true);
        return "redirect:/cart/cart.html";
    }
}
