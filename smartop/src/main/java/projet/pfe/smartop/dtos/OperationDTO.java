package projet.pfe.smartop.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import projet.pfe.smartop.entites.CR;
import projet.pfe.smartop.entites.Utilisateur;
import projet.pfe.smartop.enums.EnumStatutOP;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationDTO implements Serializable {
    private Integer id;
    private String titre;
    private String dept;
    private String equipementImpacte;
    private String demandeur;
    private String categorie;
    private Date dateDemande;
    private Date datePrevisionelle;
    private String derniereModification;
    private String commentaire;
    private String codeOP;
    private String matricule;
    private EnumStatutOP enumStatutOP;
    private UtilisateurDTO utilisateur;
    @JsonIgnore
    List<CR> compterenduList = Collections.emptyList();
}
