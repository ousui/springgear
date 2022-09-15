package org.springgear.exception;

import org.springgear.support.constants.HttpStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 影响主业务流程的中断异常，遇到此异常，需要继续向上抛出。
 * 异常会将已经写入 response 的内容拿到一起 set。
 *
 * @author SHUAI.W 2017-12-12
 **/
public class SpringGearInterruptException extends SpringGearException {

    @Setter
    @Getter
    private Object response;

    public SpringGearInterruptException(String msg, int code) {
        super(msg, code);
    }

    public SpringGearInterruptException(String msg) {
        super(msg, HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
