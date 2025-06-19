package id.co.mii.serverapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import id.co.mii.serverapp.models.Status;
import id.co.mii.serverapp.repositories.StatusRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StatusService {
    private StatusRepository statusRepository;

    public List<Status> getAll() {
        return statusRepository.findAll();
    }

    public Status getById(Integer id) {
        return statusRepository.findById(id).orElse(null);
    }

    public Status create(Status status) {
        return statusRepository.save(status);
    }

    public Status update(Integer id, Status status) {
        getById(id);
        status.setId(id);
        return statusRepository.save(status);
    }

    public Status delete(Integer id) {
        Status status = getById(id);
        statusRepository.delete(status);
        return status;
    }
}
