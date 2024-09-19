package com.example.demo.aop.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class GetResponseDto extends CommonResponse {

    private List<Response> results;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Response {
        private String name;
        private String userId;
        private String address;
        private int age;
        private String etc;
    }

}
