package id.co.mii.clientapp.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.Charset;

public class BasicHeader {

    public static String createBasicToken(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")));
        return new String(encodedAuth);
    }

    public static HttpHeaders getBasicHeader() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new HttpHeaders() {
            {
                set("Authorization", "basic " + createBasicToken(
                        auth.getPrincipal().toString(),
                        auth.getCredentials().toString()));
            }
        };
    }

}
