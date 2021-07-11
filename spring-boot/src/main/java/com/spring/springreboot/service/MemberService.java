package com.spring.springreboot.service;

import com.spring.springreboot.domain.Member;
import com.spring.springreboot.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional  // 데이터 변경(mutation)은 트랜잭션 안에서 이루어져야 한다.
@RequiredArgsConstructor    // Lombok 애노테이션이다. final 키워드가 있는 필드만으로 생성자를 생성한다.
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    // 회원 가입
    // Mutation이므로 트랜잭션이 반드시 필요하다. 여기서는 클래스에 정의했다.
    // 클래스 위에 @Transaction(read_only=True)를 하고 Mutation에만 따로 정의하는 것도 좋다.
    public Long join(Member member) {
        validateDuplicatedMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 아래와 같은 로직을 짜긴 했지만 멀티쓰레드 세이프티하다고 할 수 없다. Database에서도 유니크로 설정해서 한번 더 방어하자.
    private void validateDuplicatedMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (findMembers.isEmpty() == false) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회

    // 아래처럼 읽기 전용 트랜잭션을 설정할 경우 성능상의 이점이 있다. Dirty check를 안한다던지 이런저런...
    @Transactional(readOnly = true)
    public List<Member> findMember() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
