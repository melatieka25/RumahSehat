package TA_C_SHA_90.RumahSehatWeb.model;

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
    @GenericGenerator(name = "appointment-generator", strategy = "TA_C_SHA_90.RumahSehatWeb.Generator.AppointmentGenerator")
    private String kode;

    @NotNull
    @Column(name = "waktuAwal", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd' 'HH:mm")
    private LocalDateTime waktuAwal;

    @NotNull
    @Column(name = "isDone", nullable = false)
    private Boolean isDone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "resep_appointment",
        joinColumns = { @JoinColumn(name = "kode_appointment", referencedColumnName = "kode")
        },
        inverseJoinColumns = { @JoinColumn(name = "id_resep", referencedColumnName = "id")})
    private ResepModel resep;

    @OneToOne(mappedBy = "appointment" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TagihanModel tagihan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pasien", referencedColumnName = "uuid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PasienModel pasien;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dokter", referencedColumnName = "uuid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DokterModel dokter;
}
