package id.co.mii.clientapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import id.co.mii.clientapp.model.Attendance;
import id.co.mii.clientapp.model.Meeting;
import id.co.mii.clientapp.model.Participant;

@Service
public class AttendanceService {
        private final RestTemplate restTemplate;

    @Value("${dns.baseUrl}/attendance") 
    private String url;

     @Autowired
    public AttendanceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public List<Participant> getAttendeeByMeeting(Integer meetingId) {
        return restTemplate.exchange(
                url + "/attendee/" + meetingId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Participant>>() {
                }
        ).getBody();
    }

    public List<Attendance> getAll() {
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Attendance>>() {
                }).getBody();
    }

    public Attendance getById(Integer id) {
        return restTemplate.exchange(
                url + "/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Attendance>() {
                }).getBody();
    }
}
