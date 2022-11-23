package TA_C_SHA_90.RumahSehatWeb.repository;

import TA_C_SHA_90.RumahSehatWeb.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDb extends JpaRepository<RoleModel, Long> {
	Optional<RoleModel> findById(Long id);
    Optional<RoleModel> findByRole(String role);
}
