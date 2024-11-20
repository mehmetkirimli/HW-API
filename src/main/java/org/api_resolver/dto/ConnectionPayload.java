package org.api_resolver.dto;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Getter
public class ConnectionPayload
{
    @Value("${email}")
    private String email;

    @Value("${password}")
    private String password;

    @Value("${url1-test}")
    private String url1;

    @Value("${url2-test}")
    private String url2;

    @Value("${url3-test}")
    private String url3;

    @Value("${url4-test}")
    private String url4;

    @Value("${url5-test}")
    private String url5;

    //TODO apiKey burada olsa olur mu ?
    public void disableSSLVerification() {
        try {

            TrustManager[] trustAllCertificates = new TrustManager[]{
                    new X509TrustManager() {
                        // SSL sertifikalarını kontrol etmemek için,
                        // Güvenlik sağlayıcılarını kaldırıyoruz
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCertificates, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Hostname doğrulamasını devre dışı bırakıyoruz (isteğe bağlı)
            HostnameVerifier allHostsValid = (hostname, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
