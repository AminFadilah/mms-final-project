package id.co.mii.serverapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import id.co.mii.serverapp.models.Room;
import id.co.mii.serverapp.repositories.RoomRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoomService {
    private RoomRepository roomRepository;

    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    public Room getById(Integer id) {
        return roomRepository.findById(id).orElse(null);
    }

    public Room create(Room room) {
        return roomRepository.save(room);
    }

    public Room update(Integer id, Room room) {
        getById(id);
        room.setId(id);
        return roomRepository.save(room);
    }

    public Room delete(Integer id) {
        Room room = getById(id);
        roomRepository.delete(room);
        return room;
    }

    public void updateRoomAvailability(Room room, boolean isAvailable) {
        room.setIsAvailable(isAvailable);
        roomRepository.save(room);
    }
}
