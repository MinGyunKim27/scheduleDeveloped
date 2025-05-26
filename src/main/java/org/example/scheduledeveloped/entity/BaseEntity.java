package org.example.scheduledeveloped.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 모든 엔티티에 공통적으로 적용되는 생성일자(createdAt)와 수정일자(updatedAt)를 포함하는 추상 클래스입니다.
 * JPA의 Auditing 기능을 활용하여 엔티티가 생성되거나 수정될 때 자동으로 시간 정보를 기록합니다.
 */
@Getter
@MappedSuperclass // 이 클래스를 상속받는 엔티티 클래스에 매핑 정보가 전달됨
@EntityListeners(AuditingEntityListener.class) // 생성/수정 시점 자동 감지를 위한 리스너 등록
public abstract class BaseEntity {

    /**
     * 엔티티가 생성된 시각.
     * 최초 생성 시에만 값이 설정되며, 이후에는 수정되지 않음.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * 엔티티가 마지막으로 수정된 시각.
     * 엔티티가 갱신될 때마다 자동으로 업데이트됨.
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
