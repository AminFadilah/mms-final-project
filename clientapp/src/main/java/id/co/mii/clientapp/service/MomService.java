package id.co.mii.clientapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import id.co.mii.clientapp.model.MOM;

@Service
public class MomService {
    private final RestTemplate restTemplate;

    @Value("${dns.baseUrl}/mom")
    private String url;

    @Autowired
    public MomService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<MOM> getAll() {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MOM>>() {
                }).getBody();
    }

    public MOM getById(Integer id) {
        return restTemplate.exchange(
                url + "/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MOM>() {
                }).getBody();
    }

    public MOM create(MOM mom) {
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(mom),
                new ParameterizedTypeReference<MOM>() {
                }).getBody();
    }

    public MOM update(Integer id, MOM mom) {
        return restTemplate.exchange(
                url + "/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(mom),
                new ParameterizedTypeReference<MOM>() {
                }).getBody();
    }

    public MOM delete(Integer id) {
        return restTemplate.exchange(
                url + "/" + id,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<MOM>() {
                }).getBody();
    }
}