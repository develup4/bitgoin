package com.spring.springreboot.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Album")    // Entity 상속관계에서 싱글테이블시의 구분자이다. 생략하면 클래스명이 그냥 들어간다.
public class Album extends Item {
    private String artist;
    private String etc;
}
