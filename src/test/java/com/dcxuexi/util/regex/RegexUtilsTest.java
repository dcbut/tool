package com.dcxuexi.util.regex;

public class RegexUtilsTest {

    public static void main(String[] args) {

        System.out.println("验证手机号: " + RegexUtils.isMobileSimple("15201830000"));
        System.out.println("验证手机号: " + RegexUtils.isMobileSimple("15201830001"));
        System.out.println("验证手机号: " + RegexUtils.isMobileSimple("15201830002"));

    }


}
