package com.wjcoding.server.model;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description ceshi
 * @date 2024/3/6 10:52:52
 */
public class Person<T> {
    private T name;
    int age;

    public Person(T name) {
        this.name = name;
    }

    public Person(T name, int age) {
        this.name = name;
        this.age = age;
    }

    public void test(){

    }
}
