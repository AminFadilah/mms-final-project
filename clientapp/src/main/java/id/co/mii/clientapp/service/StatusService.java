package id.co.mii.clientapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import id.co.mii.clientapp.model.Status;

@Service
public class StatusService {
    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/status")
    private String url;

    @Autowired
    public StatusService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Status> getAll() {
        return restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Status>>() {}
        ).getBody();
    }

    public Status getById(String id) {
        return restTemplate.exchange(
            url + "/" + id,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Status>() {}
        ).getBody();
    }

    public Status create(Status status) {
        return restTemplate.exchange(
            url,
            HttpMethod.POST,
            new HttpEntity(status),
            new ParameterizedTypeReference<Status>() {}
        ).getBody();
    }

    public Status update(String id, Status status) {
        return restTemplate.exchange(
            url + "/" + id,
            HttpMethod.PUT,
            new HttpEntity(status),
            new ParameterizedTypeReference<Status>() {}
        ).getBody();
    }

    public void delete(String id) {
        restTemplate.exchange(
            url + "/" + id,
            HttpMethod.DELETE,
            null,
            new ParameterizedTypeReference<Status>() {}
        );
    }
}

