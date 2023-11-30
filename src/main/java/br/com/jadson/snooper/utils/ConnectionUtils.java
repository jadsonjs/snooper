package br.com.jadson.snooper.utils;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class ConnectionUtils {

    /**
     * This approach disables SSL verification for all calls made by your application,
     * globally affecting the behavior of all SSL connections.
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public void disableSslVerification() throws NoSuchAlgorithmException, KeyManagementException {
        // Criar um SSLContext que aceita todos os certificados
        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManager[] trustAllCerts = new TrustManager[] {
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
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        // Aplicar o SSLContext ao HttpsURLConnection
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        // Desativar a verificação do hostname
        HostnameVerifier allHostsValid = (hostname, session) -> true;
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
}
