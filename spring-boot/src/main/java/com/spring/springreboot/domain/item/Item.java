package com.spring.springreboot.domain.item;

import com.spring.springreboot.domain.Category;
import com.spring.springreboot.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
// 상속관계 entity들이 있을때 전략을 세울 수 있는데,
// 싱글테이블은 하나의 테이블에 아이템을 상속받는 Album, Book, Movie를 db에 하나의 테이블로 합쳐서 넣는것을 말하고 성능과 관계가 있다.
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// 아래 애노테이션이 싱글테이블시 구분자의 필드를 정의하는 것이다. 자식에서 DiscriminatorValue로 선언한 값이 들어간다.
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id @GeneratedValue
    @Column(name="item_id")
    private Long Id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // 비지니스 로직
    // 엔티티 내에 둔다는 것이 객체지향적으로 훌륭하다
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
