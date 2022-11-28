package org.springgear.core.engine.handler;

import org.springgear.core.engine.context.SpringGearContext;

/**
 * 抽象实现 AbstractApiWorkflowHandler，对工程结尾做一个了断。
 *
 * @author SHUAI.W 2017-12-13
 **/
public abstract class AbstractSpringGearEndingHandler<REQ, RESP> extends AbstractSpringGearHandler<REQ, RESP> {

    /**
     * 调用结束方法，返回一个值，抽象方法会将此值放入 response
     *
     * @param context 上下文
     * @param req     请求
     * @return 想要返回的 response
     * @throws Exception
     */
    public abstract RESP end(SpringGearContext<REQ, RESP> context, REQ req) throws Exception;

        @Override
    public void handle(SpringGearContext<REQ, RESP> context) throws Exception {
        RESP resp = this.end(context, context.getRequest());
        context.setResponse(resp);
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
