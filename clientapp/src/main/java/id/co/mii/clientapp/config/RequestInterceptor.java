package id.co.mii.clientapp.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import id.co.mii.clientapp.utils.BasicHeader;

import java.io.IOException;

public class RequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!request.getURI().getPath().equals("/v1/login")) {
            request.getHeaders().add(
                    "Authorization",
                    "Basic " + BasicHeader.createBasicToken(auth.getPrincipal().toString(),
                            auth.getCredentials().toString()));
        }

        ClientHttpResponse response = execution.execute(request, body);

        return response;
    }
}
