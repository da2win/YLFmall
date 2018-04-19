package cn.e3mall.search.message;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 监听商品添加消息, 接收消息后, 将对应的商品信息添加到索引库
 *
 * @author Darwin
 * @date 2018/4/19
 */
public class ItemAddMessageListener implements MessageListener {
    @Autowired
    private ItemMapper mapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {
        // Get ID from the message.
        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            Long itemId = new Long(text);
            // Waiting for the transaction submission.
            Thread.sleep(1000);
            // Inquire commodity info according to ID.
            SearchItem item = mapper.getItemById(itemId);
            // Create a doc obj.
            SolrInputDocument doc = new SolrInputDocument();
            // Adding a domain to the document obj.
            doc.addField("id", item.getId());
            doc.addField("item_title", item.getTitle());
            doc.addField("item_price", item.getPrice());
            doc.addField("item_sell_point", item.getSell_point());
            doc.addField("item_image", item.getImage());
            doc.addField("item_category_name", item.getCategory_name());
            // Writing the doc into index lib.
            solrServer.add(doc);
            // Commit.
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
