package org.example.scheduledeveloped.exception;

import jakarta.validation.ConstraintViolationException;
import org.example.scheduledeveloped.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     *
     * @param ex
     * @return
     */
    // @RequestBody 유효성 검사 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResponseDto responseDto = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                "입력값이 올바르지 않습니다.",
                fieldErrors
        );
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    /**
     *
     * @param ex
     * @return
     */
    // @RequestParam, @PathVariable 유효성 검사 실패
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            fieldErrors.put(field.substring(field.lastIndexOf('.') + 1), violation.getMessage());
        });

        ErrorResponseDto responseDto = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                "입력값이 올바르지 않습니다.",
                fieldErrors
        );
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    /**
     *
     * @param ex
     * @return
     */
    // 비밀번호 불일치 예외
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handlePasswordMismatch(PasswordMismatchException ex) {
        ErrorResponseDto response = new ErrorResponseDto(
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    /**
     *
     * @param ex
     * @return
     */
    // 사용자 없음 예외 → 404 처리로 수정
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponseDto response = new ErrorResponseDto(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     *
     * @param ex
     * @return
     */
    //권한 없음
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDenied(AccessDeniedException ex) {
        ErrorResponseDto response = new ErrorResponseDto(
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    /**
     *
     * @param ex
     * @return
     */
    // ResponseStatusException 처리 (권한, 논리 등)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDto> handleResponseStatus(ResponseStatusException ex) {
        ErrorResponseDto responseDto = new ErrorResponseDto(
                ex.getStatusCode().value(),
                ex.getReason(),
                null
        );
        return new ResponseEntity<>(responseDto, ex.getStatusCode());
    }

    /**
     *
     * @param ex
     * @return
     */
    // 기타 모든 예외 (서버 내부 오류)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric(Exception ex) {
        ex.printStackTrace(); // 서버 로그

        ErrorResponseDto responseDto = new ErrorResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "서버 내부 오류가 발생했습니다.",
                null
        );
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
