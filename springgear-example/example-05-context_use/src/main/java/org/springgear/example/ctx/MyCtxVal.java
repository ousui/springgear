package org.springgear.example.ctx;

import org.springgear.core.engine.context.SpringGearContextValue;


public class MyCtxVal extends SpringGearContextValue {

    private String someString;

    private Long someLong;

    public String getSomeString() {
        return someString;
    }

    public void setSomeString(String someString) {
        this.someString = someString;
    }

    public Long getSomeLong() {
        return someLong;
    }

    public void setSomeLong(Long someLong) {
        this.someLong = someLong;
    }
}
