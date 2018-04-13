package cn.e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Darwin on 2018/4/11.
 */
public class TestSolrj {

    @Test
    public void addDocument() throws IOException, SolrServerException {
        // 创建一个SolrServer对象, 创建一个连接, 参数solr服务的url
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
        // 创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        // 向文档对象中添加域, 文档中必须包含一个id域, 所有的域必须再schema.xml中定义
        document.addField("id", "doc1");
        document.addField("item_title", "测试商品01");
        document.addField("item_price", 1000);
        // 把文档写入索引库
        solrServer.add(document);
        // 提交
        solrServer.commit();
    }

    @Test
    public void deleteDocument() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
        // delete document
//        solrServer.deleteById("doc1");
        solrServer.deleteByQuery("id:doc1");
        // commit
        solrServer.commit();
    }


    @Test
    public void queryIndex() throws SolrServerException {
        // create a solrserver
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
        // create a solrquery
        SolrQuery query = new SolrQuery();
        //query.setQuery("*:*");
        query.set("q", "*:*");
        // execute query condition
        QueryResponse response = solrServer.query(query);
        // get doc list, get total
        SolrDocumentList results = response.getResults();
        System.out.println("records total:" + results.getNumFound());
        // iterate doc list, get field content.
        for (SolrDocument item : results) {
            System.out.println(item.get("id"));
            System.out.println(item.get("item_title"));
            System.out.println(item.get("item_sell_point"));
            System.out.println(item.get("item_price"));
            System.out.println(item.get("item_image"));
            System.out.println(item.get("item_category_name"));
        }

    }

    @Test
    public void testComplexQuery() throws Exception {
        // create a solrserver
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
        // create a solrquery
        SolrQuery query = new SolrQuery();
        query.setQuery("手机");
        query.setStart(0);
        query.setRows(20);
        query.set("df", "item_title");
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");

        // execute query condition
        QueryResponse response = solrServer.query(query);
        // get doc list, get total
        SolrDocumentList results = response.getResults();
        System.out.println("records total:" + results.getNumFound());
        // iterate doc list, get field content.
        for (SolrDocument item : results) {
            System.out.println(item.get("id"));
            // 取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(item.get("id")).get("item_title");
            String title = "";
            if (list != null && list.size() > 0) {
                title = list.get(0);
            } else {
                title = (String) item.get("item_title");
            }
            System.out.println(title);
            System.out.println(item.get("item_sell_point"));
            System.out.println(item.get("item_price"));
            System.out.println(item.get("item_image"));
            System.out.println(item.get("item_category_name"));
        }
    }
}
