package com.example.demo.aop.service;

import com.example.demo.aop.dto.GetResponseDto;
import com.example.demo.aop.dto.PostResponseDto;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AopService {

    public GetResponseDto get() {
        GetResponseDto.Response response = GetResponseDto.Response.builder()
                .name("My name is GetMapping")
                .userId("get-kylen")
                .age(30)
                .etc("get Mapping Test")
                .address("getApiMethod")
                .build();

        GetResponseDto dto = GetResponseDto.builder()
                .totalCnt(1)
                .code("200")
                .message("SUCCESS")
                .results(Lists.newArrayList(response))
                .build();

        return dto;
    }

    public PostResponseDto post() {
        PostResponseDto dto = PostResponseDto.builder()
                .totalCnt(1)
                .code("200")
                .message("SUCCESS")
                .name("My name is PostMapping")
                .userId("post-kylen")
                .age(40)
                .address("postApiMethod")
                .etc("post Mapping Test")
                .build();
        return dto;
    }
}
