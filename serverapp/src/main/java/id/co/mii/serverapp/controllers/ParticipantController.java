package id.co.mii.serverapp.controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.mii.serverapp.models.Participant;
import id.co.mii.serverapp.services.ParticipantService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/participants")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@AllArgsConstructor
public class ParticipantController {

    private ParticipantService participantService;

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    @GetMapping
    public List<Participant> getAll() {
        return participantService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    @GetMapping("/{id}")
    public Participant getById(@PathVariable Integer id) {
        return participantService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ADMIN', 'CREATE_USER')")
    @PostMapping
    public Participant create(@RequestBody Participant participant) {
        return participantService.create(participant);
    }

    @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN', 'UPDATE_USER')")
    @PutMapping("/{id}")
    public Participant update(@PathVariable Integer id, @RequestBody Participant participant) {
        return participantService.update(id, participant);
    }

    @PreAuthorize("hasAnyAuthority('DELETE_ADMIN', 'DELETE_USER')")
    @DeleteMapping("/{id}")
    public Participant delete(@PathVariable Integer id) {
        return participantService.delete(id);

    }
}