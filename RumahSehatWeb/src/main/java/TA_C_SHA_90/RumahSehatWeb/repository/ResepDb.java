package TA_C_SHA_90.RumahSehatWeb.repository;

import TA_C_SHA_90.RumahSehatWeb.model.ResepModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResepDb extends JpaRepository<ResepModel, Long> {
}
