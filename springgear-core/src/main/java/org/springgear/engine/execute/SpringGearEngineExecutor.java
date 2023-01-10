package org.springgear.engine.execute;

import org.springgear.exception.SpringGearError;

/**
 * 接口 工作流，继承于 pipeline, 有自己独特的实现。
 *
 * @author SHUAI.W 2017-12-13
 **/
public interface SpringGearEngineExecutor<RESP> {


    /**
     * 简化方式，只传递 request 即可。
     *
     * @param parts 入参
     * @return 返回出参
     */
    RESP execute(SpringGearEngineParts parts) throws SpringGearError;

}
