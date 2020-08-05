package tn.elan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.elan.model.Medias;

@Repository
public interface MediasRepository extends JpaRepository<Medias, Long> {


}
