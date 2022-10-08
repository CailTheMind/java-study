package com.xzc.jdk.java8;

import cn.hutool.core.date.StopWatch;
import cn.hutool.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static cn.hutool.log.StaticLog.info;
import static java.lang.Thread.sleep;

public class CompletableFutureTest {

    public static void main(String[] args) {
        test3();
    }

    public static void test3() {
        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        Supplier<List<String>> task1 = () -> {
            info(Thread.currentThread().getName());
            info("task1 begin");
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new ArrayList<String>() {{
                add("1");
            }};
        };
        Supplier<List<String>> task2 = () -> {
            info(Thread.currentThread().getName());
            info("task2 begin");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new ArrayList<String>() {{
                add("2");
            }};
        };
        Function<List<String>, List<String>> eitherFunc = (e -> {
            info("fasterTask");
            e.add("f");
            return e;
        });
        // applyToEither接收一个任务CompletableFuture和一个处理结果的Function，两个任务优先执行完的任务将结果传入Function处理
        // applyToEither和applyToEitherAsync区别是， applyToEither在较快task执行完成后继续执行eitherFunc， Async将eitherFunc放入新的线程执行
        CompletableFuture<List<String>> completableFuture = CompletableFuture.supplyAsync(task1, executorService).applyToEitherAsync(CompletableFuture.supplyAsync(task2, executorService), eitherFunc, executorService);
//        CompletableFuture<List<String>> completableFuture = CompletableFuture.supplyAsync(task1, executorService).applyToEither(CompletableFuture.runAsync(task2, executorService), eitherFunc, executorService);

        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.addAll(completableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        info(jsonArray.toString());
        info("主线程结束");
        executorService.shutdown();


    }

    public static void test2() {
        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        Supplier<List<String>> task1 = () -> {
            info(Thread.currentThread().getName());
            info("task1 begin");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new ArrayList<String>() {{
                add("1");
            }};
        };

        Function<List<String>, List<String>> applyFunc = (e -> {
            info(Thread.currentThread().getName());
            info("task2 begin");
            try {
                sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            e.add("2");
            return e;
        });
        // thenApplyAsync和thenApply区别是，去掉async则把task2和task1合并到一个线程执行
        CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(task1, executorService).thenApplyAsync(applyFunc, executorService);
//        CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(task1, executorService).thenApply(applyFunc);

        future.join();

        info("主线程结束");
        executorService.shutdown();
    }

    public static void test1() {
        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        Supplier<List<String>> task1 = () -> {
            info("task1 begin ");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new ArrayList<String>() {{
                add("1");
            }};
        };

        Supplier<List<String>> task2 = () -> {
            info("task2 begin");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new ArrayList<String>() {{
                add("2");
            }};
        };

        Supplier<List<String>> task3 = () -> {
            info("task3 begin");
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new ArrayList<String>() {{
                add("3");
            }};
        };

        BiFunction<List<String>, List<String>, List<String>> combineFunc = (task1Result, task2Result) -> {
            info("combine begin");
            task1Result.addAll(task2Result);
            return task1Result;
        };

        CompletableFuture<List<String>> completableFuture = CompletableFuture.supplyAsync(task1, executorService)
                .thenCombine(CompletableFuture.supplyAsync(task2, executorService), combineFunc)
                .thenCombine(CompletableFuture.supplyAsync(task3, executorService), combineFunc);
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.addAll(completableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        info(jsonArray.toString());
        info("主线程结束");
        executorService.shutdown();
    }
}
