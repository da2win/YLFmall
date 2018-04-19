package cn.e3mall.search.mapper;

import cn.e3mall.common.pojo.SearchItem;

import java.util.List;

/**
 * Created by Darwin on 2018/4/11.
 */
public interface ItemMapper {

    List<SearchItem> getItemList();
    SearchItem getItemById(long itemId);
}
