package projet.pfe.smartop.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import projet.pfe.smartop.entites.Operation;
import projet.pfe.smartop.enums.EnumType_utilisateur;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UtilisateurDTO implements Serializable {
    private Integer id;
    private String nom;
    private String prenom;
    private String password;
    private String adresseMail;
    private String matricule;
    private EnumType_utilisateur enumType_utilisateur;
    @JsonIgnore
    List<Operation> listOperation = Collections.emptyList();
}
