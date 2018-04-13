package cn.e3mall.content.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbContent;

import java.util.List;

/**
 *
 * @author Darwin
 * @date 2018/4/10
 */
public interface ContentService {

    EasyUIDataGridResult getContentList(long categoryId, int page, int rows);

    E3Result addContent(TbContent content);

    List<TbContent> getContentListByCid(long cid);
}

