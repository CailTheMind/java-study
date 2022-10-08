package com.xzc.sensitive_words;

import java.util.function.Consumer;

public class IMyInterfaceTest {

    public static void main(String[] args) {
//        IMyInterface iMyInterface = () -> System.out.println("111111");
//        iMyInterface.study();

        Consumer<Person> consumer = (p) -> System.out.println(p.name);
//        consumer.accept(new Person("小民"));
    }

    static class Person{
        String name;

        public Person(String name) {
            this.name = name;
        }
    }
}
