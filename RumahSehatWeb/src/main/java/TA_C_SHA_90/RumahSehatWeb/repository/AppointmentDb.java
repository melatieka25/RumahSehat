package TA_C_SHA_90.RumahSehatWeb.repository;

import TA_C_SHA_90.RumahSehatWeb.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatWeb.model.DokterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
@Repository
public interface AppointmentDb extends JpaRepository<AppointmentModel, Long> {
    Optional<AppointmentModel> findAllByKode(String kode);
    List<AppointmentModel> findAllByDokter(DokterModel dokter);
}
