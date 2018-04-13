package cn.e3mall.search.service;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Darwin
 * @date 2018/4/11
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public E3Result importAllItems() {

        try {
            // 查询商品列表
            List<SearchItem> itemList = itemMapper.getItemList();
            // 遍历商品列表
            for (SearchItem item : itemList) {
                // 创建文档对象
                SolrInputDocument doc = new SolrInputDocument();
                // 向文档对象中添加域
                doc.addField("id", item.getId());
                doc.addField("item_title", item.getTitle());
                doc.addField("item_price", item.getPrice());
                doc.addField("item_sell_point", item.getSell_point());
                doc.addField("item_image", item.getImage());
                doc.addField("item_category_name", item.getCategory_name());
                // 把文档对象写入到索引库
                solrServer.add(doc);
            }

            // 提交
            solrServer.commit();
            // 返回导入成功
            return E3Result.ok();
        } catch (Exception e) {
            return E3Result.build(500, "数据导入时异常");
        }
    }
}
