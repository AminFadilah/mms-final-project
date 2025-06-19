package id.co.mii.clientapp.controller.rest;

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

import id.co.mii.clientapp.model.Participant;
import id.co.mii.clientapp.service.ParticipantService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/participants")
@AllArgsConstructor
public class RestParticipantController {

    private ParticipantService participantService;

    @GetMapping
    public List<Participant> getAll() {
        return participantService.getAll();
    }

    @GetMapping("/{id}")
    public Participant getById(@PathVariable Integer id) {
        return participantService.getById(id);
    }

    @PostMapping
    public Participant create(@RequestBody Participant participant) {
        return participantService.create(participant);
    }

    @PutMapping("/{id}")
    public Participant update(@PathVariable Integer id, @RequestBody Participant participant) {

        return participantService.update(id, participant);
    }

    @DeleteMapping("/{id}")
    public Participant delete(@PathVariable Integer id) {
        return participantService.delete(id);
    }

}