package com.spring.springreboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders") // 정렬에 쓰는 order by 예약어 때문에 혼동이 있으므로 관례상 orders로 테이블 이름 변경한다.
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="orders_id")
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    // Order가 member의 foreign key를 가지고 있으므로 primary로 설계한다.
    // 매핑될 member의 key를 등록하는 과정이다.
    // primary가 아닌 member 쪽에는 mapped by가 정의되어야 한다.
    @JoinColumn(name="member_id")
    private Member member;

    // 위 member와 반대의 경우인데, order item에서의 order 필드명과 이 클래스가 매핑된다는 뜻이다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // 그냥 Date 클래스를 쓰면 어노테이션을 해줘야 JPA가 매핑을 했었는데,
    // JAVA8부터는 LocalDateTime을 쓰면 hibernate가 자동으로 인식한다.
    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

    // 아래와 같은 메서드를 연관관계 메서드라고 한다.
    // 데이터베이스를 떠나서 객체가 서로 참조를 주고 받으려면 필요한데 그런 용도이다.
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // 생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {   // 새 문법인가 보다. 사용법은 직관적이다.
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // 비지니스 로직 //
    // 주문 취소
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMPLETE) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.setOrderStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // 조회 로직 //
    // 전체 주문가격 조회
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
