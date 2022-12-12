package TA_C_SHA_90.RumahSehatAPI.repository;

import TA_C_SHA_90.RumahSehatAPI.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatAPI.model.TagihanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagihanDb extends JpaRepository<TagihanModel, Long> {
    Optional<TagihanModel> findByAppointment(AppointmentModel appointment);
    Optional<TagihanModel> findByKode(String kode);
}
