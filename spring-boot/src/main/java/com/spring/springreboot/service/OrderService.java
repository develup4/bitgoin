package com.spring.springreboot.service;

import com.spring.springreboot.domain.Delivery;
import com.spring.springreboot.domain.Member;
import com.spring.springreboot.domain.Order;
import com.spring.springreboot.domain.OrderItem;
import com.spring.springreboot.domain.item.Item;
import com.spring.springreboot.repository.ItemRepository;
import com.spring.springreboot.repository.MemberRepository;
import com.spring.springreboot.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());   // 현실이라면 따로 주소를 입력받겠지만 그냥 멤버의 주소 사용

        // 주문 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        // 여기서 OrderItem도 생성되었는데 orderRepository만 저장하는 이유는?
        // Order 클래스를 살펴보면 아래처럼 CascadeType.ALL로 설정되어 있어서 자동으로 연관된 데이터도 저장이 된다.
        // (라이프 사이클이 같기 때문에 아래와 같이 설정된 것이다)
        // @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
        // private List<OrderItem> orderItems = new ArrayList<>();
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel(); // 비지니스 로직이 내부에 있다 // 객체지향적
        // 이런 스타일을 "도메인 모델 패턴"이라고 한다. 비지니스 로직을 엔티티에 두고 서비스는 연결만 해주는 역할을 한다.
        // 반대되는 개념은 트랜잭션 스크립트 패턴이라고 부른다.
        // Dirty checking => JPA와 연관된 객체를 수정하면 알아서 감지해서 쿼리를 작성하지 않아도 업데이트해준다.
    }

    //public List<Order> findOrders(OrderSearch orderSearch) {
    //  orderRepository.find
    //}
}
