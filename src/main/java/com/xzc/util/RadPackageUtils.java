package com.xzc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 红包拆分工具类
 *
 * @author xzc
 */
public class RadPackageUtils {

    private static final Random random = new Random();

    public static List<Integer> getPackage(Integer num, Integer sumMoney) {
        List<Integer> pak = new ArrayList<>();

        while (num > 1) {
            Integer m = getMoney(num, sumMoney);
            num--;
            sumMoney -= m;
            pak.add(m);
        }
        pak.add(sumMoney);
        return pak;
    }

    public static Integer getMoney(Integer num, Integer restMoney) {
        int floor = (int) Math.floor(restMoney * 1.0d / num * 2);
        return getRandom(1, floor);
    }

    public static Integer getRandom(Integer min, Integer max) {
        int i = random.nextInt(max - min + 1) + min;
        return i;
    }

    public static void main(String[] args) {
        List<Integer> aPackage = getPackage(100, 100);
        System.out.println(aPackage.size());

        Integer j = 0;
        for (Integer i : aPackage) {
            j = j + i;
        }
        System.out.println(j);
        System.out.println(aPackage);
    }
}
