package com.dcxuexi.util.md5;

public class Md5test {

    public static void main(String[] args) {

        System.out.println(Md5SaltTool.encode("123"));
        String password = Md5SaltTool.generate("123");
        System.out.println(password);
        System.out.println(Md5SaltTool.verify("123", "51d13835dd6e005b04145e9f48126765928450bb0994eb6e"));

    }


}
