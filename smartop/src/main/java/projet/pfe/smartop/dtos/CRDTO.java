package projet.pfe.smartop.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import projet.pfe.smartop.entites.Operation;
import projet.pfe.smartop.enums.EnumstatutCR;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CRDTO implements Serializable {
    private Integer id;
    private Date dateFin;
    private EnumstatutCR enumstatutCR;
    private String resume;
    private String equipementImp;
    private OperationDTO operation;
    private String codeOP;
}
