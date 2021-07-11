package com.spring.springreboot;

public class memberRepository {
    // 아래처럼 멤버 오브젝트가 아니라 아이디를 반환하는 이유는?
    // "커맨드와 쿼리를 분리하라"

    // 아래와 같은 mutation은 side effect를 만들 수 있기 때문에 리턴 값을 안만든다.
    // 대신 id 정도를 반환하면 나중에 필요할때 검색해서 쓰면 된다.

    /*
    public long save(Member member) {
        entityManager.persist(member);
        return member.getId();
    }
    */
}
