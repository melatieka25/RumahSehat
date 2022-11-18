package TA_C_SHA_90.RumahSehatWeb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Apoteker")
public class ApotekerModel extends UserModel {
    @OneToMany(mappedBy = "apoteker" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ResepModel> listResep;
}
