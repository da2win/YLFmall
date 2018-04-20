package cn.e3mall.item.controller;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * Product details page display.
 * @author Darwin
 * @date 2018/4/19
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model) {
        // Invoke the service to take the basic info of the goods.
        TbItem tbItem = itemService.getItemById(itemId);
        Item item = new Item(tbItem);
        // Get the desc of the goods.
        TbItemDesc itemDesc = itemService.getItemDescById(itemId);
        // Pass info to the page.
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDesc);
        // Return view.
        return "item";
    }

}
