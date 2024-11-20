package org.api_resolver.Service;

import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.security.*;
import java.security.cert.*;

public class HttpPostExample {

    public static void main(String[] args) {
        String url = "https://sandbox-reporting.rpdpymnt.com/api/v3/merchant/user/login";
        String email = "demo@financialhouse.io";
        String password = "cjaiU8CV";

        // Sertifika güvenliği ayarları (isteğe bağlı)
        disableSSLVerification();

        // API'ye POST isteği gönderiyoruz
        sendPostRequest(url, email, password);
    }

    private static void disableSSLVerification() {
        try {
            // SSL sertifikalarını kontrol etmemek için güvenlik sağlayıcılarını kaldırıyoruz
            TrustManager[] trustAllCertificates = new TrustManager[]{
                    new X509TrustManager() {
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

    private static void sendPostRequest(String urlString, String email, String password) {
        try {
            // URL oluşturuluyor
            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            // JSON veri
            String jsonInputString = "{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }";

            // Bağlantıyı açıyoruz ve veri gönderiyoruz
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Yanıtı alıyoruz
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                // Yanıtı yazdırıyoruz
                System.out.println("Response: " + response.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
