package com.cloudlabs.library.service.impl;

import com.cloudlabs.library.dto.request.MemberRequestDto;
import com.cloudlabs.library.dto.response.MemberResponseDto;
import com.cloudlabs.library.mapper.MemberMapper;
import com.cloudlabs.library.model.Member;
import com.cloudlabs.library.repository.MemberRepository;
import com.cloudlabs.library.service.MemberService;
import com.cloudlabs.library.util.Constants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl extends GenericServiceImpl<Member, Long> implements MemberService {

    private final MemberMapper memberMapper;

    @Autowired
    public MemberServiceImpl(@Qualifier("memberRepository") MemberRepository memberRepository, MemberMapper memberMapper) {
        super(memberRepository);
        this.memberMapper = memberMapper;
    }

    @Override
    public List<MemberResponseDto> readAll() {
        return memberMapper.toResponseList(this.findAll());
    }

    @Override
    public MemberResponseDto create(MemberRequestDto memberRequestDto) {
        return memberMapper.toResponse(
                this.save(
                        memberMapper.toEntity(memberRequestDto)
                )
        );
    }

    @Override
    public MemberResponseDto readById(Long id) {
        return this.findById(id)
                .map(memberMapper::toResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException(Constants.NOT_FOUND_SECTION)
                );
    }

    @Override
    public MemberResponseDto modify(Long id, MemberRequestDto memberRequestDto) {
        return this.findById(id)
                .map(member -> {
                    Member updatedMember = memberMapper.toEntity(memberRequestDto);
                    updatedMember.setId(member.getId());
                    return updatedMember;
                })
                .map(this::save)
                .map(memberMapper::toResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException(Constants.NOT_FOUND_MEMBER)
                );
    }

    @Override
    public void remove(Long id) {
        this.delete(
                findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException(Constants.NOT_FOUND_MEMBER.concat(id.toString()))
                        )
                        .getId()
        );
    }
}
