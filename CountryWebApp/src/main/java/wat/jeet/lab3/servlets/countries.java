package wat.jeet.lab3.servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

@WebServlet(name = "countries", urlPatterns = {"/countries"})
public class countries extends HttpServlet {

    private Client createJerseyClient() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                @Override public void checkClientTrusted(X509Certificate[] chain, String authType) { }
                @Override public void checkServerTrusted(X509Certificate[] chain, String authType) { }
                @Override public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
            }}, new java.security.SecureRandom());

            return ClientBuilder.newBuilder()
                    .sslContext(sslContext)
                    .hostnameVerifier((hostname, session) -> true)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error creating Jersey client with SSL bypass", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String countryName = request.getParameter("name");
        if (countryName == null || countryName.isBlank()) {
            countryName = "Poland";
        }

        String apiEndpointUrl = "https://restcountries.com/v3.1";
        String apiResource = "name/" + countryName;

        try {
            Client client = createJerseyClient();
            WebTarget webserviceRequest = client.target(apiEndpointUrl).path(apiResource);

            String jsonResponse = webserviceRequest
                    .request(MediaType.APPLICATION_JSON)
                    .get(String.class);

            // Parse JSON array -> List of Maps
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> countries = objectMapper.readValue(
                    jsonResponse, new TypeReference<List<Map<String, Object>>>() {}
            );

            // Send data to JSP
            request.setAttribute("countryData", countries);
            request.getRequestDispatcher("/index.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Country not found or API error: " + e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
