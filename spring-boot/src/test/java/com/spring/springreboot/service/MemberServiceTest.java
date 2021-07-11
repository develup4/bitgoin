package com.spring.springreboot.service;

import com.spring.springreboot.domain.Member;
import com.spring.springreboot.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)    // 실제 스프링과 연동해서 테스트
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    // @Autowired EntityManager entityManager;

    @Test
    public void 회원가입() throws Exception {
        // given(이런이런게 주어지면)
        Member member = new Member();
        member.setName("kim");

        // when(이럴 때)
        Long savedId = memberService.join(member);

        //then(이렇게 동작해야 해)
        // entityManager.flush();  // persist()는 바로 INSERT 쿼리를 만들지 않는다. flush() 될때서야 만들기 때문에 확인을 위해서 잠깐 생성하였다.
        assertEquals(member, memberRepository.findOne(savedId));
        // 테스트이므로 반복 실행을 위해 롤백이 필요하다. @Transaction은 테스트인 경우 자동으로 롤백을 진행시킨다.
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
        }

        //then
        fail("예외가 발생해야 한다!");
    }
}