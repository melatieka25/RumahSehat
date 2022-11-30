package TA_C_SHA_90.RumahSehatAPI.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Tagihan")
@Entity
public class TagihanModel implements Serializable {
    @Id
    @GeneratedValue(generator = "tagihan-generator")
    @GenericGenerator(name = "tagihan-generator", strategy = "TA_C_SHA_90.RumahSehatAPI.Generator.TagihanGenerator")
    private String kode;

    @NotNull
    @Column(name = "tanggalTerbuat", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalTerbuat;

    @NotNull
    @Column(name = "tanggalBayar", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalBayar;

    @NotNull
    @Column(name = "isPaid", nullable = false)
    private Boolean isPaid;

    @NotNull
    @Column(name = "jumlahTagihan", nullable = false)
    private Integer jumlahTagihan;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kode_appointment", referencedColumnName = "kode", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference(value = "tagihanAppointment")
    private AppointmentModel appointment;
}
