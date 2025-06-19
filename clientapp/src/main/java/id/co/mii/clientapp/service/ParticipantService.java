package id.co.mii.clientapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import id.co.mii.clientapp.model.Participant;

@Service
public class ParticipantService {
    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/participants")
    private String url;

    @Autowired
    public ParticipantService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Participant> getAll() {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Participant>>() {
                }).getBody();
    }

    public Participant getById(Integer id) {
        return restTemplate.exchange(
                url + "/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Participant>() {
                }).getBody();
    }

    public Participant create(Participant participant) {
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity(participant),
                new ParameterizedTypeReference<Participant>() {
                }).getBody();
    }

    public Participant update(Integer id, Participant participant) {
        return restTemplate.exchange(
                url + "/" + id,
                HttpMethod.PUT,
                new HttpEntity(participant),
                new ParameterizedTypeReference<Participant>() {
                }).getBody();
    }

    public Participant delete(Integer id) {
        return restTemplate.exchange(
                url + "/" + id,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Participant>() {
                }).getBody();
    }
}
