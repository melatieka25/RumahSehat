package TA_C_SHA_90.RumahSehatAPI.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "Resep")
@Entity
public class ResepModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "isDone", nullable = false)
    private Boolean isDone;

    @NotNull
    @Column(name = "createdAt", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "resep")
    @JsonBackReference(value = "resepAppointment")
    private AppointmentModel appointment;

    @OneToMany(mappedBy = "resep", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "jumlahResep")
    List<JumlahModel> listJumlah;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "confirmer_uuid", referencedColumnName = "uuid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value = "apotekerResep")
    private ApotekerModel apoteker;

}
