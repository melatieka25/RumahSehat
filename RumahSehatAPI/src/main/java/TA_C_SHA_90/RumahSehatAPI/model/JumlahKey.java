package TA_C_SHA_90.RumahSehatAPI.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class JumlahKey implements Serializable {
    @Column(name = "obat")
    public String obat;

    @Column(name = "resep")
    public Long resep;
}
