package com.spring.springreboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/*
@RunWith(SpringRunner.class)
@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test */

//  모든 데이터 변경은 트랜잭션 안에서 이루어져야 한다
//  @Transactional

//  테스트에서 트랜잭션이 일어나면 당연히도 테스트가 끝나고 롤백을 해서 남지 않는다.
//  아래 애노테이션으로 롤백하지 않도록 설정할 수 있다.
//  @Rollback(false)

/*  public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("aaa");

        // when
        long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    }

    // TDD live template
    // IntelliJ의 preference/LiveTemplate 메뉴에서 tdd라는 템플릿을 생성했다.
    // tdd까지 쓰고 탭을 누르면 위 테스트 포맷이 자동으로 완성된다.
    // $END$ 자리로 커서가 이동한다.
}
*/