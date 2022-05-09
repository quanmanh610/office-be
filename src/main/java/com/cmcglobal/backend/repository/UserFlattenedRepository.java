package com.cmcglobal.backend.repository;

import com.cmcglobal.backend.entity.UserFlattened;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserFlattenedRepository extends JpaRepository<UserFlattened, Integer> {
    UserFlattened findByUserName(String userName);

    boolean existsByParentDepartmentName(String department);

    List<UserFlattened> findAllByParentDepartmentName(String department);

    List<UserFlattened> findAllByDepartmentName(String department);

    @Query(value = "SELECT * FROM office.user_flattened WHERE username LIKE %:username%", nativeQuery = true)
    List<UserFlattened> findAllByUserNameLike(@Param("username") String username);
}
