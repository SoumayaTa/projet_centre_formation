package univ.iwa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univ.iwa.model.Calendrier;
import univ.iwa.model.Entreprise;

import java.util.List;

@Repository
public interface CalendrierRepository extends JpaRepository<Calendrier, Long> {
	void deleteByEntreprise(Entreprise entreprise);

	List<Calendrier> findByFormateur_Id(int formateurId);
}
