package TA_C_SHA_90.RumahSehatAPI.repository;

import TA_C_SHA_90.RumahSehatAPI.model.AppointmentModel;
import TA_C_SHA_90.RumahSehatAPI.model.PasienModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentDb extends JpaRepository<AppointmentModel, Long> {
    Optional<AppointmentModel> findByKode(String kode);

    List<AppointmentModel> findByPasien(PasienModel pasienModel);
    @Query("Select c FROM AppointmentModel c WHERE c.dokter.uuid = :dokter AND c.waktuAwal between :dateTimeStart and :dateTimeFinish")
    List<AppointmentModel> getAllAppointmentWithinInterval(@Param("dateTimeStart") LocalDateTime dateTimeStart, @Param("dateTimeFinish") LocalDateTime dateTimeFinish, @Param("dokter") String dokter);
}
