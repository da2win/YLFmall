package cn.e3mall.cart.service;

import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbItem;

import java.util.List;

/**
 *
 * @author Darwin
 * @date 2018/4/24
 */
public interface CartService {

    E3Result addCart(long userId, long itemId, int num);
    E3Result mergeCart(long userId, List<TbItem> itemList);
    List<TbItem> getCartList(long userId);
    E3Result updateCartNum(long userId, long itemId, int num);
    E3Result deleteCartItem(long userId, long itemId);
}
