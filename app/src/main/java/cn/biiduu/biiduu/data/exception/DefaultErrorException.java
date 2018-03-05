package cn.biiduu.biiduu.data.exception;

public class DefaultErrorException extends Exception {
    public String code;
    public String type;

    public DefaultErrorException(String error, String detailMessage) {
        this(error, detailMessage, "-1");
    }

    private DefaultErrorException(String error, String detailMessage, String type) {
        super(detailMessage);
        this.code = error;
        this.type = type;
    }
}
