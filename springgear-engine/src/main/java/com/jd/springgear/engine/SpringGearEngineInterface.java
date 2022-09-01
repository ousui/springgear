package com.jd.springgear.engine;

import com.jd.springgear.exception.GearInterruptException;
import com.jd.springgear.exception.SpringGearException;

/**
 * 接口 工作流，继承于 pipeline, 有自己独特的实现。
 *
 * @author SHUAI.W 2017-12-13
 **/
public interface SpringGearEngineInterface<REQ, RESP> {

    /**
     * 简化方式，只传递 request 即可。
     *
     * @param request 入参
     * @return 返回出参
     */
    RESP execute(REQ request, long timestamp, Object... others) throws SpringGearException;


    /**
     * 对执行结果进行包裹返回
     *
     * @param req
     * @param resp
     * @param timestamp
     * @param code
     * @param msg
     * @param others
     * @return
     */
    Object wrap(REQ req, RESP resp, long timestamp, int code, String msg, Object... others);

    /**
     * 获取来源，用于记录，实现部分可以复写内容
     *
     * @return
     * @throws GearInterruptException
     */
    default String getSource() throws GearInterruptException {
        return this.getClass().getSimpleName();
    }
}
