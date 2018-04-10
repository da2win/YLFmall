package cn.e3mall.content.service.impl;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Darwin
 * @date 2018/4/10
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCatList(long parentId) {
        // 根据parentId查询子节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        // 设置查询条件
        criteria.andParentIdEqualTo(parentId);
        // 执行查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        // 转换easyuitreenode的列表
        List<EasyUITreeNode> nodeList = new ArrayList<>();
        for (TbContentCategory item : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(item.getId());
            node.setText(item.getName());
            node.setState(item.getIsParent() ? "closed": "open");
            // 添加到列表
            nodeList.add(node);
        }
        return nodeList;
    }

    @Override
    public E3Result addContentCategory(long parentId, String name) {
        // 创建一个表对应的pojo
        TbContentCategory contentCategory = new TbContentCategory();
        // 设置pojo的属性
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        // 1(正常), 2(删除)
        contentCategory.setStatus(1);
        // 默认排序就是1
        contentCategory.setSortOrder(1);
        // 新添加的节点一定是叶子节点
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        // 插入到数据库
        contentCategoryMapper.insert(contentCategory);
        // 判断父节点的isparent属性, 如果不是改为true
        // 根据parentid查询父节点
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parent);
        }
        // 返回E3result, 包含pojo
        return E3Result.ok(contentCategory);
    }

    @Override
    public E3Result delContentCategory(long id) {
        // 查询是否有子节点
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        // 判断是否有子节点
        int count = contentCategoryMapper.countByExample(example);
        if (count > 0) {
            return E3Result.build(500, "删除失败");
        }
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        contentCategory.setStatus(2);
        contentCategoryMapper.updateByPrimaryKey(contentCategory);
        criteria = example.createCriteria();
        criteria.andIdEqualTo(contentCategory.getParentId());
        // 查看是否有子节点
        int childCount = contentCategoryMapper.countByExample(example);
        if (childCount == 0) {
            TbContentCategory parent = new TbContentCategory();
            parent.setId(contentCategory.getParentId());
            parent.setUpdated(new Date());
            parent.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKeySelective(parent);
        }
        return E3Result.ok();
    }

    @Override
    public E3Result updateContentCategory(long id, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setId(id);
        contentCategory.setUpdated(new Date());
        contentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        return E3Result.ok();
    }
}
