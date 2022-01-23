package com.xzc.algorithm;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

/**
 * 红包算法
 *
 * @author xzc
 */
public class RedEnvelope {

    public static void main(String[] args) {
        BigDecimal amount = new BigDecimal(100).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal min = new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal num = new BigDecimal(10).setScale(2, BigDecimal.ROUND_HALF_UP);
//        testPocket1(amount, min, num);
//        testPocket2(amount, min, num);
//        testPocket3(amount, min, num);
        testPocket4(amount, min, num);
    }

    /**
     * 剩余金额随机法. 会出现金额差距过大情况
     *
     * @param amount
     * @param min
     * @param num
     */
    public static void testPocket1(BigDecimal amount, BigDecimal min, BigDecimal num) {
        // 总金额-（最小金额*总数）
        BigDecimal remain = amount.subtract(min.multiply(num));
        final Random random = new Random();
        final BigDecimal hundred = new BigDecimal("100");
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal redpeck;
        for (int i = 0; i < num.intValue(); i++) {
            final int nextInt = random.nextInt(100);
            if (i == num.intValue() - 1) {
                // 最后一个人直接给他
                redpeck = remain;
            } else {
                // 随机数*最小金额/100
                redpeck = new BigDecimal(nextInt).multiply(remain).divide(hundred, 2, RoundingMode.HALF_DOWN);
            }
            if (remain.compareTo(redpeck) > 0) {
                remain = remain.subtract(redpeck);
            } else {
                remain = BigDecimal.ZERO;
            }
            sum = sum.add(min.add(redpeck));
            System.out.println("第" + (i + 1) + "个人抢到红包金额为：" + min.add(redpeck).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        System.out.println("红包总额：" + sum.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 红包总额 * 随机数/随机数总和
     *
     * @param amount
     * @param min
     * @param num
     */
    private static void testPocket2(BigDecimal amount, BigDecimal min, BigDecimal num) {
        final Random random = new Random();
        final int[] rand = new int[num.intValue()];
        BigDecimal sum1 = BigDecimal.ZERO;
        BigDecimal redpack;
        int sum = 0;
        for (int i = 0; i < num.intValue(); i++) {
            rand[i] = random.nextInt(100);
            sum += rand[i];
        }
        final BigDecimal bigDecimal = new BigDecimal(sum);
        BigDecimal remain = amount.subtract(min.multiply(num));
        for (int i = 0; i < rand.length; i++) {
            if (i == num.intValue() - 1) {
                redpack = remain;
            } else {
                redpack = remain.multiply(new BigDecimal(rand[i])).divide(bigDecimal, 2, RoundingMode.FLOOR);
            }
            if (remain.compareTo(redpack) > 0) {
                remain = remain.subtract(redpack);
            } else {
                remain = BigDecimal.ZERO;
            }
            sum1 = sum1.add(min.add(redpack)).setScale(2, BigDecimal.ROUND_HALF_UP);
            System.out.println("第" + (i + 1) + "个人抢到红包金额为：" + min.add(redpack).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        System.out.println("红包总额:" + sum1);
    }

    /**
     * 割线法
     *
     * @param amount
     * @param min
     * @param num
     */
    private static void testPocket3(BigDecimal amount, BigDecimal min, BigDecimal num) {
        final Random random = new Random();
        final int[] rand = new int[num.intValue()];
        BigDecimal sum1 = BigDecimal.ZERO;
        BigDecimal redpeck;
        int sum = 0;
        for (int i = 0; i < num.intValue(); i++) {
            rand[i] = random.nextInt(100);
            sum += rand[i];
        }
        final BigDecimal bigDecimal = new BigDecimal(sum);
        BigDecimal remain = amount.subtract(min.multiply(num));
        for (int i = 0; i < rand.length; i++) {
            if (i == num.intValue() - 1) {
                redpeck = remain;
            } else {
                redpeck = remain.subtract(new BigDecimal(rand[i])).divide(bigDecimal, 2, RoundingMode.FLOOR);
            }
            if (remain.compareTo(redpeck) > 0) {
                remain = remain.subtract(redpeck).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else {
                remain = BigDecimal.ZERO;
            }
            sum1 = sum1.add(min.add(redpeck)).setScale(2, BigDecimal.ROUND_HALF_UP);
            System.out.println("第" + (i + 1) + "个人抢到红包金额为：" + min.add(redpeck));
        }
        System.out.println("红包总额：" + sum1);
    }

    /**
     * 二倍均值计算公式：2 * 剩余金额/剩余红包数
     *
     * @param amount
     * @param min
     * @param num
     */
    private static void testPocket4(BigDecimal amount, BigDecimal min, BigDecimal num) {
        BigDecimal remain = amount.subtract(min.multiply(num));
        final Random random = new Random();
        final BigDecimal hundred = new BigDecimal("100");
        final BigDecimal two = new BigDecimal("2");
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal redpeck;
        for (int i = 0; i < num.intValue(); i++) {
            final int nextInt = random.nextInt(100);
            if (i == num.intValue() - 1) {
                redpeck = remain;
            } else {
                redpeck = new BigDecimal(nextInt).multiply(remain.multiply(two).divide(num.subtract(new BigDecimal(i)), 2, RoundingMode.CEILING)).divide(hundred, 2, RoundingMode.FLOOR);
            }
            if (remain.compareTo(redpeck) > 0) {
                remain = remain.subtract(redpeck).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else {
                remain = BigDecimal.ZERO;
            }
            sum = sum.add(min.add(redpeck)).setScale(2, BigDecimal.ROUND_HALF_UP);
            System.out.println("第" + (i + 1) + "个人抢到红包金额为：" + min.add(redpeck));
        }
        System.out.println("红包总额：" + sum);
    }
}
