package TA_C_SHA_90.RumahSehatAPI.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Jumlah")
@Entity
public class JumlahModel {
    @EmbeddedId
    JumlahKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("obat")
    @JoinColumn(name = "obat")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value = "jumlahObat")
    private ObatModel obat;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("resep")
    @JoinColumn(name = "resep")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value = "jumlahResep")
    private ResepModel resep;

    @NotNull
    @Column(name = "kuantitas", nullable = false)
    private Integer kuantitas;

}
