package org.example.scheduledeveloped.repository;

import org.example.scheduledeveloped.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User 엔티티에 대한 데이터베이스 접근을 처리하는 JPA Repository 인터페이스입니다.
 * Spring Data JPA를 통해 자동으로 구현되며, 사용자 관련 CRUD 및 검색 기능을 제공합니다.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 사용자 이름으로 사용자 정보를 조회합니다.
     *
     * @param userName 조회할 사용자 이름
     * @return 해당 이름을 가진 사용자가 존재하면 Optional에 담겨 반환되고, 없으면 빈 Optional 반환
     */
    Optional<User> findUserByUserName(String userName);

    /**
     * 이메일과 비밀번호로 사용자 정보를 조회합니다.
     * (현재 사용하지 않으며, 보안상의 이유로 일반적으로는 사용하지 않는 방식입니다.)
     *
     * @param email    사용자 이메일
     * @param password 사용자 비밀번호
     * @return 해당 이메일과 비밀번호를 가진 사용자가 존재하면 Optional에 담겨 반환되고, 없으면 빈 Optional 반환
     */
    Optional<User> findUserByEmailAndPassword(String email, String password);

    /**
     * 사용자 ID로 사용자 정보를 조회합니다.
     *
     * @param id 사용자 ID
     * @return 해당 ID를 가진 사용자가 존재하면 Optional에 담겨 반환되고, 없으면 빈 Optional 반환
     */
    Optional<User> findUserById(Long id);

    /**
     * 특정 사용자 이름을 가진 사용자의 수를 조회합니다.
     * (중복 여부 확인용으로 주로 사용됩니다.)
     *
     * @param name 사용자 이름
     * @return 해당 이름을 가진 사용자 수
     */
    Long countByUserName(String name);

    /**
     * 특정 이메일을 가진 사용자의 수를 조회합니다.
     * (중복 여부 확인용으로 주로 사용됩니다.)
     *
     * @param email 사용자 이메일
     * @return 해당 이메일을 가진 사용자 수
     */
    Long countByEmail(String email);

    /**
     * 이메일로 사용자 정보를 조회합니다.
     *
     * @param email 사용자 이메일
     * @return 해당 이메일을 가진 사용자가 존재하면 Optional에 담겨 반환되고, 없으면 빈 Optional 반환
     */
    Optional<User> findUserByEmail(String email);
}
