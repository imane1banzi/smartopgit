package projet.pfe.smartop.entites;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import projet.pfe.smartop.enums.EnumType_utilisateur;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(exclude = "listOperation")
public class Utilisateur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    private String password;
    @Column(nullable = false,unique = true)
    private  String matricule;
    @Column(nullable = false,unique = true)
    private String adresseMail;
    @Enumerated(EnumType.ORDINAL)
    @JdbcTypeCode(SqlTypes.ENUM)
    private EnumType_utilisateur enumType_utilisateur;
    @OneToMany(mappedBy = "utilisateur",cascade = CascadeType.ALL,fetch = FetchType.EAGER,targetEntity = Operation.class)
    List<Operation> listOperation = Collections.emptyList();

}
