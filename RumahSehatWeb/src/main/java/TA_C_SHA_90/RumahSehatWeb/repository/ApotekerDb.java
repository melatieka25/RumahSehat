package TA_C_SHA_90.RumahSehatWeb.repository;

import TA_C_SHA_90.RumahSehatWeb.model.ApotekerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApotekerDb extends JpaRepository<ApotekerModel, Long> {
}
