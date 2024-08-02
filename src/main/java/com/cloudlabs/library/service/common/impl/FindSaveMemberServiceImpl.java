package com.cloudlabs.library.service.common.impl;

import com.cloudlabs.library.model.Member;
import com.cloudlabs.library.repository.MemberRepository;
import com.cloudlabs.library.service.common.FindSaveMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindSaveMemberServiceImpl implements FindSaveMemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public FindSaveMemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Optional<Member> get(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Member post(Member member) {
        return memberRepository.save(member);
    }
}
