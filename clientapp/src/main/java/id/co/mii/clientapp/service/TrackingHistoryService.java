package id.co.mii.clientapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import id.co.mii.clientapp.model.TrackingHistory;

import java.util.List;

@Service
public class TrackingHistoryService {
    private final RestTemplate restTemplate;

    @Value("${dns.baseUrl}/history")
    private String url;

    @Autowired
    public TrackingHistoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<TrackingHistory> getAll() {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TrackingHistory>>() {
                }).getBody();
    }

    public TrackingHistory getById(Integer id) {
        return restTemplate.exchange(
                url + "/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TrackingHistory>() {
                }).getBody();
    }

    public TrackingHistory create(TrackingHistory trackingHistory) {
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(trackingHistory),
                new ParameterizedTypeReference<TrackingHistory>() {
                }).getBody();
    }

    public TrackingHistory update(Integer id, TrackingHistory trackingHistory) {
        return restTemplate.exchange(
                url + "/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(trackingHistory),
                new ParameterizedTypeReference<TrackingHistory>() {
                }).getBody();
    }

    public TrackingHistory delete(Integer id) {
        return restTemplate.exchange(
                url + "/" + id,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<TrackingHistory>() {
                }).getBody();
    }

    public List<TrackingHistory> getByMeetingId(Integer meetingId) {
        return restTemplate.exchange(
                url + "/meeting/" + meetingId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TrackingHistory>>() {
                }).getBody();
    }
}
