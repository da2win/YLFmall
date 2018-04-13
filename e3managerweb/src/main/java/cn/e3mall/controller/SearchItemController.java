package cn.e3mall.controller;

import cn.e3mall.common.util.E3Result;
import cn.e3mall.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Darwin
 * @date 2018/4/11
 */
@Controller
public class SearchItemController {

    @Autowired
    private SearchItemService itemService;

    @RequestMapping("/index/item/import")
    @ResponseBody
    public E3Result importItemList() {
        E3Result e3Result = itemService.importAllItems();
        return e3Result;
    }
}

