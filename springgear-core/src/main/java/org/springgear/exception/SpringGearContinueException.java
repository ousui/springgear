package org.springgear.exception;


import org.springgear.support.constants.HttpStatus;

/**
 * 不影响主业务流程的异常，可以看做 warning 异常，需要在这里打印日志给出提示。
 *
 * @author SHUAI.W 2017-12-12
 **/
public class SpringGearContinueException extends SpringGearException {

    public SpringGearContinueException(String msg) {
        super(msg, HttpStatus.SC_CONTINUE);
    }
}
