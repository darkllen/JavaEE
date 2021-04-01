package javaee.books_validation.db;

import javaee.books_validation.dto.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionsRepo extends JpaRepository<PermissionEntity, Integer> {
    Optional<PermissionEntity> findPermissionEntityByPermission(PermissionEntity.Permission permission);

}
