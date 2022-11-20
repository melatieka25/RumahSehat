package TA_C_SHA_90.RumahSehatAPI.repository;

import TA_C_SHA_90.RumahSehatAPI.model.JumlahModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JumlahDb extends JpaRepository<JumlahModel, Long> {
}
