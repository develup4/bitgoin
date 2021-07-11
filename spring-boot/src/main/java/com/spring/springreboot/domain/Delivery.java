package com.spring.springreboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long Id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    // EnumType.ORDINAL은 숫자로 들어간다. 가독성도 안좋고, 중간에 하나 끼어들어가면 값이 다 밀려서 망하게 되므로 절대 쓰지말 것!
    @Enumerated(EnumType.STRING)
    private DeliveryStatus Status;
}
