package univ.iwa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univ.iwa.model.Groupe;

import java.util.List;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long> {

}
