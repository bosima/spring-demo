package cn.bossma.springdemo.jdbcexception;

import org.springframework.dao.DuplicateKeyException;

public class CustomDuplicateKeyException extends DuplicateKeyException {
    public CustomDuplicateKeyException(String msg) {
        super(msg);
    }

    public CustomDuplicateKeyException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
