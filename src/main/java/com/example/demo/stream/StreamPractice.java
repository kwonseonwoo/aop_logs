package com.example.demo.stream;

import com.example.demo.stream.dto.Animal;
import com.example.demo.stream.dto.AnimalGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class StreamPractice {

    public static void main(String[] args) {
        // Stream 기본 동작 과정
        List<String> myList = new ArrayList<>();
        myList.add("hello");
        myList.add("world");
        myList.add("!!!");

        myList.stream()
                .filter(x -> {
                    System.out.println("filter log... " + x);
                    return true;
                })
                .forEach(x -> System.out.println("forEach log... " + x));

        System.out.println("===============================");
        System.out.println("===============================");
        System.out.println("===============================");

        // GroupingBy Sample Case
        List<Animal> animals = new ArrayList<>();
        add(animals, "dog", 5, "Korea", "bokshiri");
        add(animals, "dog", 10, "Korea", "bokdori");
        add(animals, "cat", 3, "Japan", "boknyang");
        add(animals, "cat", 1, "Korea", "bokshilnayng");
        add(animals, "tiger", 2, "America", "bokshilTiger");

        //System.out.println(animals);

        Map<String, List<Animal>> groupByFrom = animals.stream()
                .collect(
                        Collectors.groupingBy(
                                Animal::getFrom
                        )
                );

        // 결과 확인
        for(String key : groupByFrom.keySet()) {
            System.out.println(key);
            System.out.println(groupByFrom.get(key));
        }

        System.out.println("===============================");
        System.out.println("===============================");
        System.out.println("===============================");

        Map<AnimalGroup, List<Animal>> groupByKindAndFrom = animals.stream()
                .collect(
                        Collectors.groupingBy(
                                animal -> new AnimalGroup(animal.getKind(), animal.getFrom())
                        )
                );

        for(AnimalGroup key : groupByKindAndFrom.keySet()) {
            System.out.println(key);
            System.out.println(groupByKindAndFrom.get(key));
            System.out.println("\n");
        }

        System.out.println("===============================");
        System.out.println("===============================");
        System.out.println("===============================");

        List<String> flatMapList = animals.stream()
                .map(animal -> animal.getNickname().split(""))
                .flatMap(Arrays::stream)
                .toList();

        System.out.println(flatMapList);

        for (String data : flatMapList) {
            System.out.print(data);
        }

        log.info("test");
        log.info("test입니다...ㅎㅎㅎ");
        log.info("test입니다...ㅎㅎㅎ");
        log.info("test입니다...ㅎㅎㅎ");
    }

    private static void add(List<Animal> list, String kind, int age, String from, String nickname) {
        list.add(
                Animal.builder()
                        .kind(kind)
                        .age(age)
                        .from(from)
                        .nickname(nickname)
                        .build()
        );
    }

}
