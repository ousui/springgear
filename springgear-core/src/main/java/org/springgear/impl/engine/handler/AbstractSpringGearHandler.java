package org.springgear.impl.engine.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springgear.engine.handler.SpringGearEngineInterface;
import org.springgear.exception.SpringGearExceptionThrows;

/**
 * handler 的抽象实现，适用了 异常抛出功能，方便功能的使用
 *
 * @author SHUAI.W 2018-01-10
 **/
@Slf4j
public abstract class AbstractSpringGearHandler<REQ, RESP> implements SpringGearEngineInterface<REQ, RESP>, SpringGearExceptionThrows<Object> {


}