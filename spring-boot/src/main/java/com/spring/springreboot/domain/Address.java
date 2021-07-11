package com.spring.springreboot.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable // JPA 내장 타입을 의미한다.
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
