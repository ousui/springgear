package org.springgear.core.engine.execute.executors;

import lombok.extern.slf4j.Slf4j;
import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.exception.SpringGearContinueException;
import org.springgear.exception.SpringGearException;
import org.springgear.exception.SpringGearInterruptException;
import org.springgear.support.constants.HttpStatus;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

/**
 * AbstractSpringGearEngineExecutor 的默认实现
 *
 * @author SHUAI.W
 * @since 2020/12/13
 **/
@Slf4j
public class DefaultSpringGearEngineExecutor extends AbstractSpringGearEngineExecutor {

    /**
     * 针对各种异常的处理。
     * TODO 将异常及对应的 code 做成 mapping 形式，进行自动化。
     *
     * @param context
     * @param e
     * @throws SpringGearException
     */

    @Override
    protected void onThrowable(SpringGearContext context, Throwable e) throws SpringGearException {
        String source = context.getSource();
        long timestamp = context.getTimestamp();
        Object response = context.getResponse();
        // 打印堆栈
        String localizedMessage = e.getLocalizedMessage();

        // TODO
        String logMessage = String.format("TS[%s-%s] %s：%s | %s | STACK TRACE: ",
                source, timestamp, e.getClass().getSimpleName(), localizedMessage, context);

        if (!(e instanceof SpringGearException)) {
            log.error(logMessage, e);
            int status = HttpStatus.SC_INTERNAL_SERVER_ERROR;

            if (e instanceof IllegalArgumentException) { // 参数一场，标记 status
                status = HttpStatus.SC_BAD_REQUEST;
            } else if (e instanceof NullPointerException) { // 空指针异常，给一个友好文案。
                localizedMessage = "System Exception, NPE";
            } else if (e instanceof SocketTimeoutException || e instanceof TimeoutException) { // 超时异常
                localizedMessage = "System Exception, Timeout";
            } else {
                localizedMessage = "System Exception, " + localizedMessage;
            }
            // 异常抛出
            throw new SpringGearException(localizedMessage, status, timestamp);
        }

        ((SpringGearException) e).setTimestamp(timestamp);

        if (e instanceof SpringGearContinueException) {// 捕获到 continue 异常，返回继续向下执行代码。
            log.warn(logMessage);
            return;
        } else if (e instanceof SpringGearInterruptException) {
            switch (((SpringGearInterruptException) e).getCode()) { // 以下几种类型不记录堆栈
                /** @see HttpResponseStatus **/
                case HttpStatus.SC_OK:
                case HttpStatus.SC_UNAUTHORIZED:
                case HttpStatus.SC_NOT_FOUND:
                    log.error(logMessage);
                    break;
                default:
                    log.error(logMessage, e);
                    break;
            }
            if (response != null && ((SpringGearInterruptException) e).getResponse() == null) { // 如果是 中断异常，且 response 没有内容，尝试将 response 放入。
                ((SpringGearInterruptException) e).setResponse(response);
            }
        } else {
            log.error(logMessage, e);
        }

        throw (SpringGearException) e;
    }

}
