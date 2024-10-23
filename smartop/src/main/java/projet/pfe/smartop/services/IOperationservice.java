package projet.pfe.smartop.services;

import projet.pfe.smartop.dtos.OperationDTO;
import projet.pfe.smartop.dtos.UtilisateurDTO;

import java.util.List;

public interface IOperationservice {
    OperationDTO addOP(OperationDTO operationDTO);
    OperationDTO updateOP(Integer id,OperationDTO operationDTO);
    String deleteOP(Integer id);
    OperationDTO findOPbyIDOP(Integer id);
    OperationDTO findOPbydemandeur(String demandeur);
    OperationDTO findOPbycategorieop(String categorie);
    List<OperationDTO> getOP();
    OperationDTO exportOP();
    OperationDTO chercherOPparID(Integer id);
    OperationDTO chercherOPParCodeOP(String codeOP);
}
