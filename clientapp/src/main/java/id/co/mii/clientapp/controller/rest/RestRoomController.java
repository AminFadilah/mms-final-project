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

import id.co.mii.clientapp.model.Room;
import id.co.mii.clientapp.service.RoomService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/room")
@AllArgsConstructor
// @PreAuthorize("hasRole('ADMIN')")
public class RestRoomController {

    private RoomService roomService;

    @GetMapping
    public List<Room> getAll() {
        return roomService.getAll();
    }

    @GetMapping("/{id}")
    public Room getById(@PathVariable Integer id) {
        return roomService.getById(id);
    }

    @PostMapping
    public Room create(@RequestBody Room room) {
        return roomService.create(room);
    }

    @PutMapping("/{id}")
    public Room update(@PathVariable Integer id, @RequestBody Room room) {

        return roomService.update(id, room);
    }

    @DeleteMapping("/{id}")
    public Room delete(@PathVariable Integer id) {
        return roomService.delete(id);
    }

}
