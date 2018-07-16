package com.mymall.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * BigDecimal练习
 */
public class BigDecimalTest {

    @Test
    public void test1() {
        System.out.println(0.05 + 0.01);
        System.out.println(1.0 - 0.23);
        System.out.println(4.014 * 100);
        System.out.println(123.3 / 100);
        //计算结果不精确
//        0.060000000000000005
//        0.77
//        401.40000000000003
//        1.2329999999999999

    }


    @Test
    public void test2() {
        BigDecimal b1 = new BigDecimal(0.05);
        BigDecimal b2 = new BigDecimal(0.01);
        System.out.println(b1.add(b2));
        //0.06000000000000000298372437868010820238851010799407958984375
    }

    @Test
    public void test3() {
        //使用String构造器
        BigDecimal b1 = new BigDecimal("0.05");
        BigDecimal b2 = new BigDecimal("0.01");
        System.out.println(b1.add(b2));
        //0.06
    }


}
