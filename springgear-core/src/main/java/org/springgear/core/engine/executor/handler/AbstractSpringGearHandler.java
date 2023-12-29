package org.springgear.core.engine.executor.handler;

import lombok.extern.slf4j.Slf4j;
import org.springgear.exception.SpringGearExceptionThrows;

/**
 * handler 的抽象实现，适用了 异常抛出功能，方便功能的使用
 *
 * @author SHUAI.W 2018-01-10
 **/
@Slf4j
public abstract class AbstractSpringGearHandler<REQ, RESP> implements SpringGearHandlerInterface<REQ, RESP>, SpringGearExceptionThrows<Object> {


}