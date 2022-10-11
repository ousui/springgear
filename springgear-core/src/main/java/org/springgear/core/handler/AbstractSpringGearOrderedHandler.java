package org.springgear.core.handler;

import org.springframework.core.Ordered;

/**
 * the abstract handler with must set order
 * you also can use {@link org.springframework.core.annotation.Order} to your class
 * @param <REQ>
 * @param <RESP>
 */
public abstract class AbstractSpringGearOrderedHandler<REQ, RESP> extends AbstractSpringGearHandler<REQ, RESP> implements Ordered {

}
