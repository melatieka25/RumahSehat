package TA_C_SHA_90.RumahSehatAPI.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference(value = "apotekerResep")
    private List<ResepModel> listResep;
}
