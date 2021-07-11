package com.spring.springreboot.service;

import com.spring.springreboot.domain.Address;
import com.spring.springreboot.domain.Member;
import com.spring.springreboot.domain.Order;
import com.spring.springreboot.domain.OrderStatus;
import com.spring.springreboot.domain.item.Book;
import com.spring.springreboot.domain.item.Item;
import com.spring.springreboot.exception.NotEnoughStockException;
import com.spring.springreboot.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager entityManager;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    @Transactional
    public void 상품주문() throws Exception {
        // given
        Member member = new Member();
        Item item = new Book();
        member.setAddress(new Address("서울", "강가", "123-13"));
        entityManager.persist(member);

        Book book = new Book();
        book.setName("시골");
        book.setPrice(10000);
        book.setStockQuantity(10);
        entityManager.persist(book);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getOrderStatus());
    }

    /*
    // expected가 안되서 주석처리
    // 해당 예외가 터지기를 기대한다고 명시해서 예외 발생시 테스트케이스가 성공한다.
    // 탈출하지 않고 fail 문까지 가게되면 테스트케이스가 실패하게 된다.
    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        // given

        // when

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }
    */
}