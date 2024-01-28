package univ.iwa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univ.iwa.model.Calendrier;
@Repository
public interface CalendrierRepository extends JpaRepository<Calendrier, Long> {
}
