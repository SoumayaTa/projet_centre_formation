package univ.iwa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import univ.iwa.model.Individus;
@Repository
public interface IndividusRepository extends JpaRepository<Individus, Long> {
}
