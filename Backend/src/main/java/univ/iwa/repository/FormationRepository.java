package univ.iwa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univ.iwa.model.Formation;
@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
}
