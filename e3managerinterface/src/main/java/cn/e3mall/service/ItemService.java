package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbItem;

/**
 *
 * @author Darwin
 * @date 2018/3/30
 */
public interface ItemService {

    TbItem getItemById(long itemId);
    
    EasyUIDataGridResult getItemList(int page, int rows);

    E3Result addItem(TbItem item, String desc);

    E3Result editItem(TbItem item, String desc);

    E3Result deleteItem(Long itemId);
}
