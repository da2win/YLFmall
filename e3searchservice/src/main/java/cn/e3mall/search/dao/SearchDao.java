package cn.e3mall.search.dao;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品搜索dao
 * @author Darwin
 * @date 2018/4/12
 */
@Repository
public class SearchDao {
    @Autowired
    private SolrServer solrServer;

    /**
     * 根据查询条件查询索引库
     * @return
     */
    public SearchResult search(SolrQuery query) throws SolrServerException {
        QueryResponse response = solrServer.query(query);
        // get query result.
        SolrDocumentList results = response.getResults();
        // get total
        long numFound = results.getNumFound();
        SearchResult searchResult = new SearchResult();
        searchResult.setRecordCount(numFound);
        // get item list, show highlight
        // highlighting
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        List<SearchItem> itemList = new ArrayList<>();
        for (SolrDocument doc : results) {
            SearchItem item = new SearchItem();
            item.setId((String) doc.get("id"));
            item.setCategory_name((String) doc.get("item_category_name"));
            item.setImage((String) doc.get("item_image"));
            // highlighting
            List<String> list = highlighting.get(doc.get("id")).get("item_titile");
            String title;
            if (list != null && list.size() > 0) {
                title = list.get(0);
            } else {
                title = (String) doc.get("item_title");
            }
            item.setTitle(title);
            item.setSell_point((String) doc.get("item_sell_point"));
            item.setPrice((long) doc.get("item_price"));
            itemList.add(item);
        }
        searchResult.setItemList(itemList);
        return searchResult;

    }
}
