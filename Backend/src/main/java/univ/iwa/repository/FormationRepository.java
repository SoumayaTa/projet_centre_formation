package univ.iwa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import univ.iwa.model.Formation;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
    List<Formation> findByCategorie(String categorie);
    List<Formation> findByVille(String ville);
    List<Formation> findByDate(LocalDate date);
    @Query("SELECT DISTINCT f.ville FROM Formation f")
    List<String> getDistinctVilles();

    @Query("SELECT DISTINCT f.categorie FROM Formation f")
    List<String> getDistinctCategories ();
    Page<Formation> findByNomContainingIgnoreCaseOrVilleContainingIgnoreCase(
            String nom, String ville, Pageable pageable
    );

}
