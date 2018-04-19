package cn.e3mall.search.controller;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 商品搜索Controller
 *
 * @author Darwin
 * @date 2018/4/12
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;
    @Value("${SEARCH_RESULT_ROWS}")
    private int SEARCH_RESULT_ROWS;

    @RequestMapping("/search")
    public String searchItemList(String keyword,
                                 @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception {
        keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
        // query list of goods.
        SearchResult searchResult = searchService.search(keyword, page, SEARCH_RESULT_ROWS);
        // Pass the results to the page.
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", searchResult.getTotalPage());
        model.addAttribute("page", page);
        model.addAttribute("recordCount", searchResult.getRecordCount());
        model.addAttribute("itemList", searchResult.getItemList());

        // Exception test.
        //int a = 1/0;
        // return logical view.
        return "search";
    }
}
