package id.co.mii.clientapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import id.co.mii.clientapp.model.dto.LoginRequest;
import id.co.mii.clientapp.model.dto.LoginResponse;

@Service
public class AuthService {

    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/login")
    private String url;

    @Autowired
    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean login(LoginRequest loginRequest) {
        try {
            ResponseEntity<LoginResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity(loginRequest),
                    new ParameterizedTypeReference<LoginResponse>() {
                    });
            if (response.getStatusCode() == HttpStatus.OK) {
                setPrinciple(response.getBody(), loginRequest.getPassword());
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public void setPrinciple(LoginResponse res, String pass) {

        List<SimpleGrantedAuthority> authorities = res.getAuthorities()
                .stream().map(authorize -> new SimpleGrantedAuthority(authorize))
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                res.getUsername(),
                pass,
                authorities);

        // Set Principle
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
