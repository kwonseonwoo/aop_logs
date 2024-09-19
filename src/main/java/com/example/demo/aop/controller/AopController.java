package com.example.demo.aop.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/aop")
public class AopController {

    //private final Service service;

    @GetMapping("/get")
    public ResponseEntity<Void> getApiMethod() {
        // aop 로그 처리만 확인할 것이기 때문에
        // service 로직은 주석처리...
        //service.method();
        return null;
    }
}
