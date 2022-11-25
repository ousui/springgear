package org.springgear.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springgear.support.constants.HttpStatus;

/**
 * @author SHUAI.W
 * @since 2020/12/11
 **/
public class SpringGearError extends Exception {

    public SpringGearError(Exception e) {
        super(e);
    }

    public SpringGearError(String msg) {
        super(msg);
    }

}
