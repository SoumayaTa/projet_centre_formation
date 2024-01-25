package univ.iwa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univ.iwa.model.Formation;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
    List<Formation> findByCategorie(String categorie);
    List<Formation> findByVille(String ville);
    List<Formation> findByDate(LocalDate date);
}
