package cn.biiduu.biiduu.protocol;

import java.util.List;

public class ArticleDetailsProtocol {
    private String content;
    private String cover;
    private long createTime;
    private String id;
    private long publishTime;
    private int rank;
    private int shareTimes;
    private String source;
    private String status;
    private String summary;
    private String title;
    private long updateTime;
    private List<String> classify;
    private List<String> tags;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getShareTimes() {
        return shareTimes;
    }

    public void setShareTimes(int shareTimes) {
        this.shareTimes = shareTimes;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public List<String> getClassify() {
        return classify;
    }

    public void setClassify(List<String> classify) {
        this.classify = classify;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ArticleDetailsProtocol{" +
                "content='" + content + '\'' +
                ", cover='" + cover + '\'' +
                ", createTime=" + createTime +
                ", id='" + id + '\'' +
                ", publishTime=" + publishTime +
                ", rank=" + rank +
                ", shareTimes=" + shareTimes +
                ", source='" + source + '\'' +
                ", status='" + status + '\'' +
                ", summary='" + summary + '\'' +
                ", title='" + title + '\'' +
                ", updateTime=" + updateTime +
                ", classify=" + classify +
                ", tags=" + tags +
                '}';
    }
}
