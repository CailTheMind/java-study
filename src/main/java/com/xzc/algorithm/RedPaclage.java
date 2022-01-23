package com.xzc.algorithm;

import java.math.BigDecimal;
import java.util.*;

/**
 * 线段分割算法实现的分红包， 比如把100元红包，分给十个人，就相当于把（0-100）这个线段随机分成十段，也就是再去中找出9个随机点。
 * 找随机点的时候要考虑碰撞问题，如果碰撞了就重新随机（当前我用的是这个方法）。这个方法也更方便抑制红包金额MAX情况，如果金额index-start>MAX,就直接把红包设为最大值MAX，
 *
 * 然后随机点重置为start+MAX，保证所有红包金额相加等于总金额。
 */
public class RedPaclage {
    public static List<Integer>  divideRedPackage(int allMoney, int peopleCount,int MAX) {
        //人数比钱数多则直接返回错误
        if(peopleCount<1||allMoney<peopleCount){
            System.out.println("钱数人数设置错误！");
            return null;
        }
        List<Integer> indexList = new ArrayList<>();
        List<Integer> amountList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < peopleCount - 1; i++) {
            int index;
            do{
                index = random.nextInt(allMoney - 2) + 1;
            }while (indexList.contains(index));//解决碰撞
            indexList.add(index);
        }
        Collections.sort(indexList);
        int start = 0;
        for (Integer index:indexList) {
            //解决最大红包值
            if(index-start>MAX){
                amountList.add(MAX);
                start=start+MAX;
            }else{
                amountList.add(index-start);
                start = index;
            }
        }
        amountList.add(allMoney-start);
        return amountList;
    }
    public static void main(String args[]){
        Scanner in=new Scanner(System.in);
        int n=Integer.parseInt(in.nextLine());
        int pnum=Integer.parseInt(in.nextLine());
        int money=n*100;int max=n*90;
        List<Integer> amountList = divideRedPackage(money, pnum,max);
        if(amountList!=null){
            for (Integer amount : amountList) {
                System.out.println("抢到金额：" + new BigDecimal(amount).divide(new BigDecimal(100)));
            }
        }

    }
}
