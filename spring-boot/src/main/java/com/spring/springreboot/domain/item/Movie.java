package com.spring.springreboot.domain.item;

import javax.persistence.Entity;

@Entity
public class Movie extends Item {
    private String Director;
    private String Actor;
}
