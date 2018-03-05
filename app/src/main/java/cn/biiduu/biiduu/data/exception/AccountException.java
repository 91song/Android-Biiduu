package cn.biiduu.biiduu.data.exception;

import java.io.IOException;

public class AccountException extends IOException {
    public AccountException(String detailMessage) {
        super(detailMessage);
    }
}
