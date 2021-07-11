package com.spring.springreboot.domain;

import com.spring.springreboot.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    // 다대다 관계를 표현할때 중간 테이블이 필요하기 때문에(CATEGORY_ITEM같은), JoinTable을 정의해야 한다.
    // 실무에서는 다대다를 쓰지 않으려고 노력한다.
    // Join류의 애노테이션은 실제 데이터베이스의 JOIN 관계를 의미하는 것이다.
    @ManyToMany
    @JoinTable(name = "category_item",
        joinColumns = @JoinColumn(name = "category_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    // 같은 타입간의 관계도 다른 타입할 때와 똑같이 하면 된다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
    // 컬렉션은 왜 필드에서 초기화하는걸까?
    // 일단 null reference exception이 발생할 위험을 줄이는 1차적인 효과도 있지만,
    // 하이버네이트가 엔티티를 영속할 때, 컬렉션을 감싸서 내장 컬렉션으로 변경하기 때문이다.
    // 말이 어려운데, 간단히 말해 타입이 ArrayList에서 PersistentBag같은 하이버네이트의 컬렉션으로 바뀌는 것을 유도하기 때문이다.
    // 따라서 엔티티 내의 컬렉션을 변경하지도 말자. 위 코드라면 child = new OtherList 이렇게 하지 말자는 이야기이다.

    // 연관관계 메서드
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}