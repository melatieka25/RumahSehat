package TA_C_SHA_90.RumahSehatWeb.repository;

import TA_C_SHA_90.RumahSehatWeb.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDb extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByUsername(String username);

    Optional<UserModel> findByEmail(String email);

}
