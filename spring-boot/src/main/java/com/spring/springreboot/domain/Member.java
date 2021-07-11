package com.spring.springreboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    // 다른곳에서 foreign key로 쓸때 필드명인 id가 자동으로 들어가면 구분이 안되므로 명시적으로 정해준다.
    // Database에서는 카멜 케이스가 아닌 스네이크 케이스를 기본적으로 사용하므로 memberId가 아닌 member_id로 한다.
    // 하이버네이트가 테이블을 생성할 때도 카멜케이스인 클래스 필드가 스네이크 케이스로 변해서 생성되는게 기본 정책이다.
    @Column(name="member_id")
    private Long Id;

    private String name;

    @Embedded
    private Address address;

    // primary가 아닐때 이렇게 설정한다. 멤버가 변경되어도 primary에게 영향이 없는 읽기전용이 된다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
