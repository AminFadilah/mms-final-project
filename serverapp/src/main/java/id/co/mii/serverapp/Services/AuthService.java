package id.co.mii.serverapp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import id.co.mii.serverapp.models.User;
import id.co.mii.serverapp.models.dto.request.LoginRequest;
import id.co.mii.serverapp.models.dto.response.LoginResponse;
import id.co.mii.serverapp.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

        private AuthenticationManager authenticationManager;
        private UserRepository userRepository;
        private AppUserDetailService appUserDetailService;

        public LoginResponse login(LoginRequest loginRequest) {
                // set login request
                UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword());

                // set principle
                Authentication auth = authenticationManager.authenticate(authReq);
                SecurityContextHolder.getContext().setAuthentication(auth);

                // set login response
                User user = userRepository
                                .findByUsernameOrParticipant_Email(
                                                loginRequest.getUsername(),
                                                loginRequest.getUsername())
                                .get();

                UserDetails userDetails = appUserDetailService.loadUserByUsername(
                                loginRequest.getUsername());

                List<String> authorities = userDetails
                                .getAuthorities()
                                .stream()
                                .map(authority -> authority.getAuthority())
                                .collect(Collectors.toList());

                return new LoginResponse(
                                user.getUsername(),
                                user.getParticipant().getEmail(),
                                authorities);
        }
}
