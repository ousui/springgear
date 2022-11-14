package org.springgear.core.engine.execute;

import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.exception.SpringGearContinueException;
import org.springgear.exception.SpringGearInterruptException;
import org.springgear.exception.SpringGearException;
import org.springgear.support.constants.HttpStatus;

/**
 * 接口 工作流，继承于 pipeline, 有自己独特的实现。
 *
 * @author SHUAI.W 2017-12-13
 **/
public interface SpringGearEngineInterface<REQ, RESP, CTX extends SpringGearContext> {


    /**
     * 简化方式，只传递 request 即可。
     *
     * @param request 入参
     * @return 返回出参
     */
    RESP execute(REQ request, long timestamp, Object... others) throws SpringGearException;

    default void onException(Throwable ex) {
    };

    default void onFinally() {

    };


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
     * 异常处理
     *
     * @param context
     * @param e
     * @throws SpringGearException
     */
    default void onThrowable(SpringGearContext<?, ?> context, Throwable e) throws SpringGearException {
        // 捕获到 continue 异常，返回继续向下执行代码。
        if (e instanceof SpringGearContinueException) {
        } else if (e instanceof SpringGearInterruptException) {
            throw (SpringGearInterruptException) e;
        } else {
            throw new SpringGearException(e.getLocalizedMessage(), HttpStatus.SC_INTERNAL_SERVER_ERROR, System.currentTimeMillis());
        }
    }

    ;

    /**
     * 获取来源，用于记录，实现部分可以复写内容
     *
     * @return
     * @throws SpringGearInterruptException
     */
    default String getSource() throws SpringGearInterruptException {
        return this.getClass().getSimpleName();
    }
}
