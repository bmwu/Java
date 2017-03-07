package com.bmwu;

/**
 * Created by michael on 3/6/17.
 */
public class Modifier {

    private static volatile int anInt = 1;
    private final int anIntFinal = 1;

    private static int getInt() {
//        return getAnInt();
        return anInt;
    }

    private int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        anInt = 12;
//        final 对象的引用不能改变
//        anIntFinal = 10;
    }
}
