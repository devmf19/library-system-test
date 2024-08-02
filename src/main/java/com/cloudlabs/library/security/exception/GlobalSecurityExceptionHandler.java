package com.cloudlabs.library.security.exception;

import com.cloudlabs.library.dto.response.ResponseDto;
import com.cloudlabs.library.security.util.SecurityConstants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalSecurityExceptionHandler {

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ResponseDto<String>> handleSignatureException(SignatureException ex) {
        ResponseDto<String> response = ResponseDto.<String>builder()
                .data(SecurityConstants.TOKEN_SIGNATURE_ERROR)
                .status(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ResponseDto<String>> handleMalformedJwtException(MalformedJwtException ex) {
        ResponseDto<String> response = ResponseDto.<String>builder()
                .data(SecurityConstants.INVALID_TOKEN)
                .status(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseDto<String>> handleExpiredJwtException(ExpiredJwtException ex) {
        ResponseDto<String> response = ResponseDto.<String>builder()
                .data(SecurityConstants.EXPIRED_TOKEN)
                .status(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ResponseDto<String>> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        ResponseDto<String> response = ResponseDto.<String>builder()
                .data(SecurityConstants.UNSUPPORTED_TOKEN)
                .status(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ResponseDto<String> response = ResponseDto.<String>builder()
                .data(SecurityConstants.EMPTY_TOKEN)
                .status(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDto<String>> handleIllegalArgumentException(BadCredentialsException ex) {
        ResponseDto<String> response = ResponseDto.<String>builder()
                .data(SecurityConstants.BAD_CREDENTIALS)
                .status(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }




}
