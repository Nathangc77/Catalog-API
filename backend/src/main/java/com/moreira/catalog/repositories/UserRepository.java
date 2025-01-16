package com.moreira.catalog.repositories;

import com.moreira.catalog.entities.User;
import com.moreira.catalog.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true, value = """
            SELECT u.email AS username, u.password, role.id AS roleId, role.authority
            FROM tb_user AS u
            INNER JOIN tb_user_role AS userRole ON u.id = userRole.user_id
            INNER JOIN tb_role AS role ON userRole.role_id = role.id
            WHERE u.email = :email
            """)
    List<UserDetailsProjection> searchUserRolesByUsername(String email);
}
