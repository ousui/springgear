package org.springgear.engine.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springgear.exception.SpringGearExceptionThrows;

/**
 * handler 的抽象实现。
 *
 * @author SHUAI.W 2018-01-10
 **/
@Slf4j
public abstract class AbstractSpringGearHandler<REQ, RESP> implements SpringGearEngineInterface<REQ, RESP> {


}