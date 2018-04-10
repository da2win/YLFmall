package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 内容分类管理Controller
 * @author Darwin
 * @date 2018/4/10
 */
@Controller
@RequestMapping("/content/category")
public class ContentCatController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(name="id", defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> contentCatList = contentCategoryService.getContentCatList(parentId);
        return contentCatList;
    }

    /**
     * 添加分类节点
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public E3Result createContentCategory(long parentId, String name) {
        E3Result e3Result = contentCategoryService.addContentCategory(parentId, name);
        return e3Result;
    }

    /**
     * 修改子节点
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public E3Result updateContentCategory(Long id, String name) {
        E3Result e3Result = contentCategoryService.updateContentCategory(id, name);
        return e3Result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public E3Result deleteContentCategory(Long id) {
        E3Result e3Result = contentCategoryService.delContentCategory(id);
        return e3Result;
    }
}
