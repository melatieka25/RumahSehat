package TA_C_SHA_90.RumahSehatAPI.repository;

import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasienDb extends JpaRepository<PasienModel, Long> {
	Optional<PasienModel> findByUuid(String uuid);

    Optional<PasienModel> findByUsername(String username);
}
