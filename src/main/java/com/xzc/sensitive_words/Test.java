package com.xzc.sensitive_words;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        long start1 = System.currentTimeMillis();

//        System.out.println(getFileMD5(new File("C:\\Users\\Administrator\\Desktop\\学习文件\\《〈Java开发手册（泰山版）〉灵魂13问》.pdf")));
//        File file = new File("C:\\Users\\Administrator\\Desktop\\b72e1b1f-151d-4183-a804-45ecdd91581f\\11");
//        file.delete();
////        try {
//////            file.createNewFile();
////            file.delete();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }

//        System.out.println((System.currentTimeMillis() - start1) + "ms");



        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aaa", "111");
        jsonObject.put("bbb", "111");
        jsonObject.put("ccc", "111");
        jsonObject.put("ddd", "111");
        String s = jsonObject.toJSONString();

        System.out.println((System.currentTimeMillis() - start1) + "ms");


        long start2 = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>(5);
        map.put("aaa", "111");
        map.put("bbb", "111");
        map.put("ccc", "111");
        map.put("ddd", "111");
        String s1 = JSONObject.toJSONString(map);

        System.out.println((System.currentTimeMillis() - start2) + "ms");

        long start3 = System.currentTimeMillis();
        String s3 = new Gson().toJson(map);

        System.out.println((System.currentTimeMillis() - start3) + "ms");

        long start4 = System.currentTimeMillis();
        String s4 = com.alibaba.fastjson2.JSONObject.toJSONString(map);

        System.out.println((System.currentTimeMillis() - start4) + "ms");

        long start5 = System.currentTimeMillis();
        String s5 = JSON.toJSONString(map);

        System.out.println((System.currentTimeMillis() - start5) + "ms");


    }


    public static String getFileMD5(File file) {
        BigInteger bigInt = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            bigInt = new BigInteger(1, md.digest());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bigInt.toString(16);
    }
}
