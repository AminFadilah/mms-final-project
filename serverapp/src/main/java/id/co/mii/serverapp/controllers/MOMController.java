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

import id.co.mii.serverapp.models.MOM;
import id.co.mii.serverapp.models.dto.request.MomDTO;
import id.co.mii.serverapp.services.MOMService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/mom")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class MOMController {
    private MOMService momService;

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    @GetMapping
    public List<MOM> getAll() {
        return momService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    @GetMapping("/{id}")
    public MOM getById(@PathVariable Integer id) {
        return momService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    @PostMapping
    public MOM create(@RequestBody MomDTO momDTO) {
        return momService.create(momDTO);
    }

    @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN', 'UPDATE_USER')")
    @PutMapping("/{id}")
    public MOM update(@PathVariable Integer id, @RequestBody MOM mom) {
        return momService.update(id, mom);
    }

    @PreAuthorize("hasAnyAuthority('DELETE_ADMIN', 'DELETE_USER')")
    @DeleteMapping("/{id}")
    public MOM delete(@PathVariable Integer id) {
        return momService.delete(id);
        
    }
}
