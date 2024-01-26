package univ.iwa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univ.iwa.model.Inscription;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
}
