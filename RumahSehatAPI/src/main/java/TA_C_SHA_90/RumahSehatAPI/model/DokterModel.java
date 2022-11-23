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
@Table(name = "Dokter")
public class DokterModel extends UserModel {

    @NotNull
    @Column(name = "tarif", nullable = false)
    private Integer tarif;

    @OneToMany(mappedBy = "dokter" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AppointmentModel> listAppointment;
}
