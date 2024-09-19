package com.example.demo.aop.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class CommonResponse {

    private int totalCnt;
    private String code;
    private String message;

}
