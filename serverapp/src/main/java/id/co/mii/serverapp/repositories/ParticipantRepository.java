package id.co.mii.serverapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.co.mii.serverapp.models.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant,Integer> {
    Participant findByName(String name);
    Optional<Participant> findByUserUsername(String username);
}
