package javaee.books_security.db;

import javaee.books_security.dto.PermissionEntity;
import javaee.books_security.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PermissionsRepo extends JpaRepository<PermissionEntity, Integer> {
    Optional<PermissionEntity> findPermissionEntityByPermission(PermissionEntity.Permission permission);

}
