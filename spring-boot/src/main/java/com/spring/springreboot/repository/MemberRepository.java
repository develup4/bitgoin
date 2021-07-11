package com.spring.springreboot.repository;

import com.spring.springreboot.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/* 아래와 같이 롬복과 생성자 주입을 이용해서 구현할 수도 있다. 스프링 Data JPA에서 제공하는 기능이다.
@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager entityManager;
}
*/

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Member member) {
        entityManager.persist(member);
    }

    public Member findOne(Long id) {
        return entityManager.find(Member.class, id);
    }

    public List<Member> findAll() {
        // 아래 문자열은 JPQL 문법이다. SQL과 비슷하게 생겼지만 테이블이 아닌 엔티티에서 연산.
        return entityManager.createQuery("select m from Member m", Member.class)
                .getResultList();   // chaining시에는 한 줄 내리는게 정석인가보다
    }

    public List<Member> findByName(String name) {
        return entityManager.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
