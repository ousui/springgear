package org.springgear.example.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springgear.core.annotation.SpringGearEngine;
import org.springgear.core.annotation.SpringGearProxy;

@SpringGearProxy
public interface MyServiceInterface {

    @SpringGearEngine(
            handlers = @Qualifier("single")
    )
    String single(String input);

}
