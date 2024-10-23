package projet.pfe.smartop.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.pfe.smartop.entites.Operation;

public interface OperationDAO extends JpaRepository<Operation,Integer> {

}
