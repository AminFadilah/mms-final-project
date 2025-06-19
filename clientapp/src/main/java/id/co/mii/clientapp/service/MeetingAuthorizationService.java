package id.co.mii.clientapp.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import id.co.mii.clientapp.model.Meeting;
import id.co.mii.clientapp.model.Participant;

@Service
public class MeetingAuthorizationService {

    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/meeting")
    private String url;

    @Autowired
    public MeetingAuthorizationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean canUpdateMeeting(Principal principal, Integer id) {
        try {
            String username = principal.getName();
            System.out.println("Authenticated username: " + username);
            
    
            Meeting meeting = restTemplate.exchange(
                url + "/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Meeting>() {
                }).getBody();

            System.out.println(meeting);
                
            if (meeting != null) {
                return meeting.getInitiator().getUser().getUsername().equals(username);
            }
        } catch (Exception e) {
            // Cetak informasi kesalahan jika terjadi masalah
            e.printStackTrace();
        }
    
        return false;
    }
    
    public boolean canAddMom(Principal principal, Integer id) {
        try {
            String username = principal.getName();
            System.out.println("Authenticated username: " + username);
            
    
            Meeting meeting = restTemplate.exchange(
                url + "/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Meeting>() {
                }).getBody();

                
            if (meeting != null) {
                return meeting.getNoteTaker().getUser().getUsername().equals(username);
            }
        } catch (Exception e) {
            // Cetak informasi kesalahan jika terjadi masalah
            e.printStackTrace();
        }
    
        return false;
    }
    
}
