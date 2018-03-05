package cn.biiduu.biiduu.protocol;

public class UpdateProtocol {
    private String updateUri;
    private boolean mustUpdate;
    private boolean last;

    public String getUpdateUri() {
        return updateUri;
    }

    public void setUpdateUri(String updateUri) {
        this.updateUri = updateUri;
    }

    public boolean isMustUpdate() {
        return mustUpdate;
    }

    public void setMustUpdate(boolean mustUpdate) {
        this.mustUpdate = mustUpdate;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "UpdateProtocol{" +
                "updateUri='" + updateUri + '\'' +
                ", mustUpdate=" + mustUpdate +
                ", last=" + last +
                '}';
    }
}
