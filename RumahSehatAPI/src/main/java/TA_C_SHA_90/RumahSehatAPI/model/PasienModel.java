package TA_C_SHA_90.RumahSehatAPI.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Pasien")
public class PasienModel extends UserModel {
    @NotNull
    @Column(name = "saldo", nullable = false)
    private Integer saldo;

    @NotNull
    @Column(name = "umur", nullable = false)
    private Integer umur;

    @OneToMany(mappedBy = "pasien" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AppointmentModel> listAppointment;
}
