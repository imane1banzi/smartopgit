package projet.pfe.smartop.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import projet.pfe.smartop.enums.EnumStatutOP;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(exclude = "utilisateur")
public class Operation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String titre;
    @Column(nullable = false)
    private String dept;
    @Column(nullable = false)
    private String equipementImpacte;
    @Column(nullable = false)
    private String demandeur;
    @Column(nullable = false)
    private String categorie;
    @Column(nullable = false)
    private Date dateDemande;
    @Column(nullable = false)
    private Date datePrevisionelle;
    private String derniereModification;
    private String commentaire;
    @Column(unique = true)
    private String codeOP;
    private String matricule;
    @Enumerated(EnumType.ORDINAL)
    @JdbcTypeCode(SqlTypes.ENUM)
    private EnumStatutOP enumStatutOP;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id",referencedColumnName = "id" )
    @JsonIgnore
    private Utilisateur utilisateur;
    @OneToMany(mappedBy = "operation",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    List<CR> compterenduList = Collections.emptyList();

}
