package com.example.demo.aop.service;

@FunctionalInterface
public interface LogStrategy {

    void process(int depth, Object data, String... names);

}
