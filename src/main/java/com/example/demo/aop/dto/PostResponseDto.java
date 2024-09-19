package com.example.demo.aop.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class PostResponseDto extends CommonResponse {

    private String name;
    private String userId;
    private String address;
    private int age;
    private String etc;

}
