package cn.e3mall.service.impl;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Darwin
 * @date 2018/4/4
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatlist(long parentId) {
        // 根据parentid查询子节点列表
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        // 设置查询操作
        criteria.andParentIdEqualTo(parentId);
        // 执行查询
        List<TbItemCat> itemCatlist = itemCatMapper.selectByExample(example);
        List<EasyUITreeNode> resultList = new ArrayList<>();

        for (TbItemCat tbItemCat : itemCatlist) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            node.setState(tbItemCat.getIsParent() ? "closed" : "open");
            resultList.add(node);
        }
        // 把列表转换为EasyUITreeNode列表
        return resultList;
    }
}
