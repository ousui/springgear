package org.springgear.exception;

import org.springgear.support.constants.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author SHUAI.W
 * @since 2020/12/11
 **/
public class SpringGearException extends SpringGearError {

    @Getter
    private final Object code;


    public SpringGearException(String msg) {
        this(msg, HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    public SpringGearException(String msg, Object code) {
        super(msg);
        this.code = code;
    }

}
