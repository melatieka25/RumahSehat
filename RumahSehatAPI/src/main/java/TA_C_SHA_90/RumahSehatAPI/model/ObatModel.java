package TA_C_SHA_90.RumahSehatAPI.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Obat")
@Entity
public class ObatModel implements Serializable {
    @Id
    @NotNull
    @Column(name = "id_obat", nullable = false)
    private String id;

    @NotNull
    @Column(name = "nama_obat", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "stok", nullable = false)
    @ColumnDefault("0")
    private Integer stok;

    @NotNull
    @Column(name = "harga", nullable = false)
    private Integer harga;

    @OneToMany(mappedBy = "obat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<JumlahModel> listJumlah;
}
