package projet.pfe.smartop.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import projet.pfe.smartop.enums.EnumstatutCR;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(exclude = "operation")
public class CR implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date dateFin;
    @Enumerated(EnumType.ORDINAL)
    @JdbcTypeCode(SqlTypes.ENUM)
    private EnumstatutCR enumstatutCR;
    private String resume;
    private String equipementImp;
    private String codeOP;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_id",referencedColumnName = "id")
    @JsonIgnore
    private Operation operation;
}
