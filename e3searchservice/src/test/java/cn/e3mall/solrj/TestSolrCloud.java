package cn.e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Darwin on 2018/4/13.
 */
public class TestSolrCloud {

    @Test
    public void testAddDoc() throws IOException, SolrServerException {

        // create a cluster solr connection,
        CloudSolrServer solrServer = new CloudSolrServer("192.168.25.131:2181,192.168.25.131:2182,192.168.25.131:2183");
        // zkHost: zookeeper's list of address.
        // setting a defaultCollection property.
        solrServer.setDefaultCollection("collection2");
        // create a doc instance.
        SolrInputDocument doc = new SolrInputDocument();
        // add field to doc.
        doc.setField("id", "solrcloud01");
        doc.setField("item_title", "测试商品01");
        doc.setField("item_price", 123);

        solrServer.add(doc);
        // commit.
        solrServer.commit();
    }

    @Test
    public void testQuery() throws SolrServerException {
        CloudSolrServer solrServer = new CloudSolrServer("192.168.25.131:2181,192.168.25.131:2182,192.168.25.131:2183");
        solrServer.setDefaultCollection("collection2");
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        QueryResponse response = solrServer.query(query);
        // get query result.
        SolrDocumentList list = response.getResults();
        System.out.println("总记录数:" + list.getNumFound());
        for (SolrDocument document : list) {
            System.out.println(document.get("id"));
            System.out.println(document.get("title"));
            System.out.println(document.get("item_title"));
            System.out.println(document.get("item_price"));
        }

    }

}
