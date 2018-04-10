package cn.e3mall.content.service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.util.E3Result;

import java.util.List;

/**
 *
 * @author Darwin
 * @date 2018/4/10
 */
public interface ContentCategoryService {

    List<EasyUITreeNode> getContentCatList(long parentId);

    E3Result addContentCategory(long parentId, String name);

    E3Result delContentCategory(long id);

    E3Result updateContentCategory(long id, String name);
}
