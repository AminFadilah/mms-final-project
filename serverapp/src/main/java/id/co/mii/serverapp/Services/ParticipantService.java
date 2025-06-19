package id.co.mii.serverapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.mii.serverapp.models.Participant;
import id.co.mii.serverapp.repositories.ParticipantRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParticipantService {

    private ParticipantRepository participantRepository;

    public List<Participant> getAll() {
        return participantRepository.findAll();
    }

    public Participant getById(Integer id) {
        return participantRepository.findById(id).orElse(null);
    }

    public Participant create(Participant participant) {
        return participantRepository.save(participant);
    }

    public Participant update(Integer id, Participant participant) {
        getById(id);
        participant.setId(id);
        return participantRepository.save(participant);
    }

    public Participant delete(Integer id) {
        Participant participant = getById(id);
        participantRepository.delete(participant);
        return participant;
    }

}
