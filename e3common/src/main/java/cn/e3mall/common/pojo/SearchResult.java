package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Darwin
 * @date 2018/4/12
 */
public class SearchResult implements Serializable {

    private long recordCount;
    private int totalPage;
    private List<SearchItem> itemList;

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}
