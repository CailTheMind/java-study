package com.xzc.jdk.java8;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class StringTest {

    private String value;


    public StringTest() {
        value = new String();
    }

    public StringTest(byte[] bytes) {
        value = new String(bytes);
    }

    public StringTest(byte[] bytes, Charset charset) {
        value = new String(bytes, charset);
    }

    public StringTest(byte[] bytes, int offset, int length) {
        value = new String(bytes, offset, length);
    }

    public StringTest(byte[] bytes, int offset, int length, Charset charset) {
        value = new String(bytes, offset, length, charset);
    }

    public StringTest(byte[] bytes, int offset, int length, String charsetName) {
        try {
            value = new String(bytes, offset, length, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public StringTest(byte[] bytes, String charsetName) {
        try {
            value = new String(bytes, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public StringTest(char[] chars) {
        value = new String(chars);
    }

    public StringTest(char[] chars, int offset, int count) {
        value = new String(chars, offset, count);
    }

    public StringTest(int[] codePoints, int offset, int count) {
        value = new String(codePoints, offset, count);
    }

    public StringTest(String option) {
        value = new String(option);
    }

    public StringTest(StringBuffer buffer) {
        value = new String(buffer);
    }

    public StringTest(StringBuilder builder) {
        value = new String(builder);
    }

    public char charAt(int index) {
        return value.charAt(index);
    }

    public int codePointAt(int index) {
        return value.codePointAt(index);
    }

    public int codePointBefore(int index) {
        return value.codePointBefore(index);
    }


    public static void main(String[] args) {
//        StringTest s = new StringTest();

//        byte[] bytes = {'B', '1', '0', 'a'};
//        StringTest s = new StringTest(bytes);
//        Charset charset = Charset.forName("UTF-8");
//        StringTest s = new StringTest(bytes, charset);

//        StringTest s = new StringTest(bytes, 1, 3, charset);
//        StringTest s = new StringTest(bytes, "UTF-8");


//        char[] chars = {'B', '中', '过', '0', 'a'};
//        int[] ints = {1, 'A', '3', 4};
//        StringTest s = new StringTest(ints, 1, 2);

//        StringTest s = new StringTest("UTF-8");
//        StringBuffer buffer = new StringBuffer("12321");
//        StringTest s = new StringTest(buffer);

//        StringBuilder builder = new StringBuilder("22222");
//        StringTest s = new StringTest(builder);

        String a = null;
        if (true || a.equals(1)) {
            System.out.println(11111);
        }
    }
}
