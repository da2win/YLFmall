package cn.e3mall.search.service;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.dao.SearchDao;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Darwin
 * @date 2018/4/12
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String keyword, int page, int rows) throws Exception {
        // create a solrserver instance.
        SolrQuery query = new SolrQuery();
        // set query condition
        query.setQuery(keyword);
        // setting paging condition.
        if (page <= 0) page = 1;
        query.setStart((page - 1) * rows);
        query.setRows(rows);
        // setting default search domain.
        query.set("df", "item_title");
        // turn on highlighting display.
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");
        // invoke dao, execute the query
        SearchResult search = searchDao.search(query);
        // calculate total page.
        long recordCount = search.getRecordCount();
        int totalPage = (int) (recordCount / rows);
        if (recordCount % rows > 0)
            totalPage ++;
        // add to the return result.
        search.setTotalPage(totalPage);
        return search;
    }
}
