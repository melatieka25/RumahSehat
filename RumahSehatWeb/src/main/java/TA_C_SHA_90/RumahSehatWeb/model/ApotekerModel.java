package TA_C_SHA_90.RumahSehatWeb.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Apoteker")
public class ApotekerModel extends UserModel {
    @OneToMany(mappedBy = "apoteker" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ResepModel> listResep;
}
