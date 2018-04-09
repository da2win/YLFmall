package cn.e3mall.service.impl;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.common.util.IDUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Darwin
 * @date 2018/3/30
 */
@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Override
    public TbItem getItemById(long itemId) {
        // 根据条件查询
//        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        // 设置查询条件
        criteria.andIdEqualTo(itemId);
        // 执行查询
        List<TbItem> list = itemMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        // 设置分页信息
        PageHelper.startPage(page, rows);
        // 执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> itemList = itemMapper.selectByExample(example);
        // 创建一个返回值对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(itemList);
        // 取分页结果
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(itemList);
        // 取总记录数
        long total = pageInfo.getTotal();
        result.setTotal(total);

        return result;
    }

    @Override
    public E3Result addItem(TbItem item, String desc) {

        // 生成商品id
        long itemId = IDUtils.genItemId();
        // 补全item属性
        item.setId(itemId);
        // 1-正常, 2-下架, 3-删除
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        // 向商品表插入数据
        itemMapper.insert(item);
        // 创建一个商品描述表对应的pojo
        TbItemDesc itemDesc = new TbItemDesc();
        // 补全属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(new Date());
        itemDesc.setCreated(new Date());
        // 向商品描述表插入数据
        itemDescMapper.insert(itemDesc);
        // 返回成功
        return E3Result.ok();
    }

    @Override
    public E3Result editItem(TbItem item, String desc) {

        return E3Result.ok();
    }

    @Override
    public E3Result deleteItem(Long itemId) {
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        item.setStatus((byte)3);
        itemMapper.updateByPrimaryKey(item);
        return E3Result.ok();
    }
}
