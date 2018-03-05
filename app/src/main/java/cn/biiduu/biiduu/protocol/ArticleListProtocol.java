package cn.biiduu.biiduu.protocol;

import java.util.List;

public class ArticleListProtocol {
    private int total;
    private int totalPage;
    private int page;
    private int pageLength;
    private List<HostArticleProtocol> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageLength() {
        return pageLength;
    }

    public void setPageLength(int pageLength) {
        this.pageLength = pageLength;
    }

    public List<HostArticleProtocol> getRows() {
        return rows;
    }

    public void setRows(List<HostArticleProtocol> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "ArticleListProtocol{" +
                "total=" + total +
                ", totalPage=" + totalPage +
                ", page=" + page +
                ", pageLength=" + pageLength +
                ", rows=" + rows +
                '}';
    }
}
