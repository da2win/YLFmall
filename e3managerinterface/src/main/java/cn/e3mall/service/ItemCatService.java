package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 *
 * @author Darwin
 * @date 2018/4/4
 */
public interface ItemCatService {

    List<EasyUITreeNode> getItemCatlist(long parentId);
}
