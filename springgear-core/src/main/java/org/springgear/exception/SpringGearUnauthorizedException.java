package org.springgear.exception;


import org.springgear.support.constants.HttpStatus;

/**
 * 未授权异常，反馈给前端未 401 http status code
 *
 * @author SHUAI.W 2017-12-12
 **/
public class SpringGearUnauthorizedException extends SpringGearException {

    public SpringGearUnauthorizedException(String msg) {
        super(msg, HttpStatus.SC_UNAUTHORIZED);
    }
}
