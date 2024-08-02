package com.cloudlabs.library.repository;

import com.cloudlabs.library.model.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("memberRepository")
public interface MemberRepository extends JpaRepository<Member, Long> {
}
