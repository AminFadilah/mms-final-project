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

import id.co.mii.serverapp.models.Status;
import id.co.mii.serverapp.services.StatusService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/status")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class StatusController {
    private StatusService statusService;

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    @GetMapping
    public List<Status> getAll() {
        return statusService.getAll();
    }

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/{id}")
    public Status getById(@PathVariable Integer id) {
        return statusService.getById(id);
    }

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @PostMapping
    public Status create(@RequestBody Status status) {
        return statusService.create(status);
    }

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @PutMapping("/{id}")
    public Status update(@PathVariable Integer id, @RequestBody Status status) {
        return statusService.update(id, status);
    }

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @DeleteMapping("/{id}")
    public Status delete(@PathVariable Integer id) {
        return statusService.delete(id);
        
    }
}
