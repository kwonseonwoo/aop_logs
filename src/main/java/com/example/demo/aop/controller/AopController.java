package com.example.demo.aop.controller;


import com.example.demo.aop.annotation.ReflectionLogs;
import com.example.demo.aop.dto.GetResponseDto;
import com.example.demo.aop.dto.PostResponseDto;
import com.example.demo.aop.service.AopService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/aop")
public class AopController {

    private final AopService aopService;

    @GetMapping("/get")
    @ReflectionLogs(depth = 2, names = {"results", "userId"}, type = ReflectionLogs.LogType.PART_FIELD)
    public ResponseEntity<GetResponseDto> getApiMethod() {
        return ResponseEntity.ok(aopService.get());
    }

    @PostMapping("/post")
    @ReflectionLogs()
    public ResponseEntity<PostResponseDto> postApiMethod() {
        return ResponseEntity.ok(aopService.post());
    }
}
