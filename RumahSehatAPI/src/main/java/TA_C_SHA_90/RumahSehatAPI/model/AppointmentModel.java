package TA_C_SHA_90.RumahSehatAPI.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Appointment")
@Entity
public class AppointmentModel implements Serializable {
    @Id
    @GeneratedValue(generator = "appointment-generator")
    @GenericGenerator(name = "appointment-generator", strategy = "TA_C_SHA_90.RumahSehatAPI.Generator.AppointmentGenerator")
    private String kode;

    @NotNull
    @Column(name = "waktuAwal", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd' 'HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm")
    private LocalDateTime waktuAwal;

    @NotNull
    @Column(name = "isDone", nullable = false)
    private Boolean isDone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "resep_appointment",
        joinColumns = { @JoinColumn(name = "kode_appointment", referencedColumnName = "kode")
        },
        inverseJoinColumns = { @JoinColumn(name = "id_resep", referencedColumnName = "id")})
    @JsonManagedReference(value = "resepAppointment")
    private ResepModel resep;

    @OneToOne(mappedBy = "appointment" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference(value = "tagihanAppointment")
    private TagihanModel tagihan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pasien", referencedColumnName = "uuid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value = "pasienAppointment")
    private PasienModel pasien;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dokter", referencedColumnName = "uuid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value = "dokterAppointment")
    private DokterModel dokter;
}
