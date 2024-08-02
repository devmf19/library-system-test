package com.cloudlabs.library.service;

import com.cloudlabs.library.model.Member;

import java.util.Optional;

public interface FindSaveMemberService {
    Optional<Member> get(Long id);
    Member post(Member member);
}
