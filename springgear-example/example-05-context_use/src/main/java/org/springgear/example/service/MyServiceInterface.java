package org.springgear.example.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springgear.core.annotation.SpringGearEngine;
import org.springgear.core.annotation.SpringGearRouter;
import org.springgear.example.ctx.MyCtxVal;

import java.util.Map;

@SpringGearRouter
public interface MyServiceInterface {

    @SpringGearEngine(
            handlers = @Qualifier("single"), contextValueClass = MyCtxVal.class
    )
    Map<String, String> single(String input);

    @SpringGearEngine(
            handlers = @Qualifier("multi")
    )
    String multi(String input);

    @SpringGearEngine(
            handlers = @Qualifier("order")
    )
    String order(String input);

    @SpringGearEngine(
            handlers = {@Qualifier("order"), @Qualifier("multi"), @Qualifier("single")}
    )
    String mixed(String input);
}
