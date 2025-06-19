package id.co.mii.clientapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.co.mii.clientapp.model.Meeting;
import id.co.mii.clientapp.model.dto.MeetingDTO;

@Service
public class MeetingService {
    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/meeting")
    private String url;

    @Autowired
    public MeetingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Meeting> getAll() {
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Meeting>>() {
                }).getBody();
    }
    
    public List<Meeting> getAllUpcoming() {
        return restTemplate.exchange(url + "/upcoming",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Meeting>>() {
                }).getBody();
    }
    public List<Meeting> getAllPast() {
        return restTemplate.exchange(url + "/past",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Meeting>>() {
                }).getBody();
    }
    public List<Meeting> getAllDelete() {
        return restTemplate.exchange(url + "/cancelled",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Meeting>>() {
                }).getBody();
    }
    public Meeting getById(Integer id) {
        return restTemplate.exchange(
                url + "/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Meeting>() {
                }).getBody();
    }

    public Meeting create(MeetingDTO meetingDTO) {
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity(meetingDTO),
                new ParameterizedTypeReference<Meeting>() {
                }).getBody();
    }

    public Meeting update(Integer id, MeetingDTO meetingDTO) {
        return restTemplate.exchange(
                url + "/" + id,
                HttpMethod.PUT,
                new HttpEntity(meetingDTO),
                new ParameterizedTypeReference<Meeting>() {
                }).getBody();
    }

    public Meeting delete(Integer id) {
        return restTemplate.exchange(
                url + "/" + id,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Meeting>() {
                }).getBody();
    }
    public Meeting restore(Integer id) {
        return restTemplate.exchange(
                url + "/restore/" + id,
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<Meeting>() {
                }).getBody();
    }
}
