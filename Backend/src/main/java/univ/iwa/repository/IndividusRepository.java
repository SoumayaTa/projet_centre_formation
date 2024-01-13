package univ.iwa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import univ.iwa.model.Individus;

public interface IndividusRepository extends JpaRepository<Individus, Long> {
}
