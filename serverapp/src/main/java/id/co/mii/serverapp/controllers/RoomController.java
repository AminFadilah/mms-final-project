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

import id.co.mii.serverapp.models.Room;
import id.co.mii.serverapp.services.RoomService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/room")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class RoomController {
    private RoomService roomService;

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    @GetMapping
    public List<Room> getAll() {
        return roomService.getAll();
    }

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/{id}")
    public Room getById(@PathVariable Integer id) {
        return roomService.getById(id);
    }

    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public Room create(@RequestBody Room room) {
        return roomService.create(room);
    }

    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping("/{id}")
    public Room update(@PathVariable Integer id, @RequestBody Room room) {
        return roomService.update(id, room);
    }

    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public Room delete(@PathVariable Integer id) {
        return roomService.delete(id);

    }
}
