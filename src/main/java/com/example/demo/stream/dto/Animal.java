package com.example.demo.stream.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class Animal {

    private final String kind;
    private final String from;
    private final String nickname;
    private final int age;

}
