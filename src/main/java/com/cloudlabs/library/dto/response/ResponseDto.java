package com.cloudlabs.library.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ResponseDto<T>{
    private T data;
    private String message;
    private HttpStatus status;
}
