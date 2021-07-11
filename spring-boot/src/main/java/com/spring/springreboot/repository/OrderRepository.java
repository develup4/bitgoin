package com.spring.springreboot.repository;

import com.spring.springreboot.domain.Order;
import com.spring.springreboot.domain.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager entityManager;

    public void save(Order order) {
        entityManager.persist(order);
    }

    public Order findOne(Long orderId) {
        return entityManager.find(Order.class, orderId);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        // 검색조건이 모두 있다면 아래처럼 그냥 돌리면 된다.
        /*
        return entityManager.createQuery("select o from Order o join o.member m" +
                " where o.orderStatus = :status" +
                " and m.name like :name", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setFirstResult(10)
                .setMaxResults(1000)
                .getResultList();
         */

        // 그런데 검색조건이란게 모든 정보가 다 안들어있을 수도 있다. status로만 검색을 한다던지...
        // 동적쿼리의 필요성이 생겼다.

        // 1. jpql 문자열을 노가다로 동적 생성해서 해결한다. 굉장한 노가다이다. 쿼리문도 그렇고 파라메터 넣는것까지 많은 if 문과 노가다가 필요하다.
        // 2. JPA creteria를 활용하는 방법. 그나마 낫지만 역시나 비추천이다. 필요하다면 검색해보자.
        // 3. Query DSL을 활용하는 방법
        /*
        QOrder order = QOrder.order;
        QMember member = QMember.member;

        return query
                .select(order)
                .from(order)
                .join(order.member, member)
                .where(statusEq(orderSearch.getOrderStatus()),
                        namesLike(orderSearch.getMemberName()))
                .limit(1000)
                .fetch();
         */
        return null;
    }
}
