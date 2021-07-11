package com.spring.springreboot.domain;

import com.spring.springreboot.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter @Getter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long Id;

    // X-ToOne과 같은 관계는 default fetch 전략이 EAGER이다. 따라서 반드시 LAZY로 수정을 해야한다.
    // 이유는 하나의 엔티티를 불러올 때, 연관된 모든 엔티티로 쿼리가 날라가는 N+1 problem이 발생하기 때문이다.
    // X-ToMany 관계는 default가 LAZY라서 상관이 없다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

    // 비지니스 로직 //
    public void cancel() {
        // 재고수량 원상 복귀
        getItem().addStock(count);
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    // 생성 로직 //
    // 생성시 로직이 복잡하기 때문에 만들어두면 편하다
    // 생성자로 하지 않는 이유는?
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }
}
