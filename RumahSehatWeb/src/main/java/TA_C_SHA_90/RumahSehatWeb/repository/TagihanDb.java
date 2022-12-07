package TA_C_SHA_90.RumahSehatWeb.repository;

import TA_C_SHA_90.RumahSehatWeb.model.TagihanModel;
import TA_C_SHA_90.RumahSehatWeb.model.AppointmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface TagihanDb extends JpaRepository<TagihanModel, Long> {
	Optional<TagihanModel> findByAppointment(AppointmentModel appointment);
	
	@Query("SELECT t FROM TagihanModel t WHERE YEAR(t.tanggalTerbuat) = :year")
	List<TagihanModel> findByYear(@Param("year") Integer year);
	
	@Query("SELECT t FROM TagihanModel t WHERE YEAR(t.tanggalTerbuat) = :year AND MONTH(t.tanggalTerbuat) = :month")
	List<TagihanModel> findByYearMonth(@Param("year") Integer year, @Param("month") Integer month);
	
	@Query("SELECT t FROM TagihanModel t WHERE t.isPaid = true")
	List<TagihanModel> findAllPaid();
}
