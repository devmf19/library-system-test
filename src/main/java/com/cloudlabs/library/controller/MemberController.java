package com.cloudlabs.library.controller;

import com.cloudlabs.library.dto.request.MemberRequestDto;
import com.cloudlabs.library.dto.response.MemberResponseDto;
import com.cloudlabs.library.dto.response.ResponseDto;
import com.cloudlabs.library.service.MemberService;
import com.cloudlabs.library.util.Constants;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<MemberResponseDto>>> readAll(){
        return new ResponseEntity<>(
                ResponseDto.<List<MemberResponseDto>>builder()
                        .data(memberService.readAll())
                        .message(Constants.SUCCESS)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto<MemberResponseDto>> create(@Validated @RequestBody MemberRequestDto memberRequestDto) {
        return new ResponseEntity<>(
                ResponseDto.<MemberResponseDto>builder()
                        .data(memberService.create(memberRequestDto))
                        .message(Constants.CREATED_MEMBER)
                        .status(HttpStatus.CREATED)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/r/{id}")
    public ResponseEntity<ResponseDto<MemberResponseDto>> readById(@Min(value = 1) @PathVariable("id") Long id) {
        return new ResponseEntity<>(
                ResponseDto.<MemberResponseDto>builder()
                        .data(memberService.readById(id))
                        .message(Constants.SUCCESS)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }

    @PutMapping("/u/{id}")
    @PreAuthorize(Constants.ADMIN_ROLE)
    public ResponseEntity<ResponseDto<MemberResponseDto>> modify(@Min(value = 1) @PathVariable("id") Long id,
                                                                 @Validated @RequestBody MemberRequestDto memberRequestDto) {
        return new ResponseEntity<>(
                ResponseDto.<MemberResponseDto>builder()
                        .data(memberService.modify(id, memberRequestDto))
                        .message(Constants.UPDATED_MEMBER)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/d/{id}")
    @PreAuthorize(Constants.ADMIN_ROLE)
    public ResponseEntity<ResponseDto<String>> remove(@Min(value = 1) @PathVariable("id") Long id) {
        memberService.remove(id);
        return new ResponseEntity<>(
                ResponseDto.<String>builder()
                        .data(Constants.DELETED_MEMBER)
                        .message(Constants.SUCCESS)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }
}
