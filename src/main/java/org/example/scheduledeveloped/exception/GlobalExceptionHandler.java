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

/**
 * 전역 예외 처리 클래스입니다.
 * 컨트롤러에서 발생할 수 있는 다양한 예외들을 처리하여, 일관된 에러 응답을 제공합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @RequestBody 요청에서 유효성 검사에 실패한 경우 처리합니다.
     * 유효하지 않은 필드와 메시지를 함께 반환합니다.
     *
     * @param ex MethodArgumentNotValidException 예외
     * @return 400 BAD_REQUEST 상태 코드와 에러 메시지 및 필드 정보
     */
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
     * @RequestParam 또는 @PathVariable에서 유효성 검사 실패 시 처리합니다.
     *
     * @param ex ConstraintViolationException 예외
     * @return 400 BAD_REQUEST 상태 코드와 에러 메시지 및 필드 정보
     */
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
     * 로그인 시 비밀번호가 일치하지 않을 경우 처리합니다.
     *
     * @param ex PasswordMismatchException 예외
     * @return 403 FORBIDDEN 상태 코드와 에러 메시지
     */
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
     * 존재하지 않는 사용자 정보 요청 시 처리합니다.
     *
     * @param ex UserNotFoundException 예외
     * @return 404 NOT_FOUND 상태 코드와 에러 메시지
     */
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
     * 인증되지 않은 접근이나 권한 부족 시 처리합니다.
     *
     * @param ex AccessDeniedException 예외
     * @return 403 FORBIDDEN 상태 코드와 에러 메시지
     */
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
     * ResponseStatusException 예외를 처리합니다.
     * 직접 상태 코드와 메시지를 설정한 경우 이를 그대로 클라이언트에 전달합니다.
     *
     * @param ex ResponseStatusException 예외
     * @return 설정된 상태 코드와 메시지를 포함한 응답
     */
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
     * 위에서 정의되지 않은 기타 모든 예외를 처리합니다.
     * 서버 내부 에러로 간주하며 500 오류를 반환합니다.
     *
     * @param ex 처리되지 않은 일반 예외
     * @return 500 INTERNAL_SERVER_ERROR 상태 코드와 에러 메시지
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric(Exception ex) {
        ex.printStackTrace(); // 서버 콘솔 로그 기록

        ErrorResponseDto responseDto = new ErrorResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "서버 내부 오류가 발생했습니다.",
                null
        );
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
