package cn.biiduu.biiduu.protocol;

import java.util.Date;
import java.util.List;

public class CoinCircleDetailsProtocol {
    private int id;
    private String title;
    private String banner;
    private String des;
    private String author;
    private Date releaseTime;
    private String path;
    private Date createTime;
    private List<String> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "CoinCircleDetailsProtocol{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", banner='" + banner + '\'' +
                ", des='" + des + '\'' +
                ", author='" + author + '\'' +
                ", releaseTime=" + releaseTime +
                ", path='" + path + '\'' +
                ", createTime=" + createTime +
                ", tags=" + tags +
                '}';
    }
}
