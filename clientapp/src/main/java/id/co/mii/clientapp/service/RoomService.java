package id.co.mii.clientapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import id.co.mii.clientapp.model.Room;

import java.util.List;

@Service
public class RoomService {
    private final RestTemplate restTemplate;

    @Value("${dns.baseUrl}/room") 
    private String url;
    
    @Autowired
    public RoomService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Room> getAll() {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Room>>() {
                }
        ).getBody();
    }

    public Room getById(Integer id) {
        return restTemplate.exchange(
                url + "/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Room>() {
                }
        ).getBody();
    }

    public Room create(Room room) {
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(room),
                new ParameterizedTypeReference<Room>() {
                }
        ).getBody();
    }

    public Room update(Integer id, Room room) {
        return restTemplate.exchange(
                url + "/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(room),
                new ParameterizedTypeReference<Room>() {
                }
        ).getBody();
    }

    public Room delete(Integer id) {
        return restTemplate.exchange(
                url + "/" + id,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Room>() {
                }
        ).getBody();
    }
}