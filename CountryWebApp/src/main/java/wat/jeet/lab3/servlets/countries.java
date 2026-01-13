package wat.jeet.lab3.servlets;

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
import java.io.PrintWriter;
import java.security.cert.X509Certificate;

@WebServlet(name = "countries", urlPatterns = {"/countries"})
public class countries extends HttpServlet {

    private Client createJerseyClient() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) { }
                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) { }
                @Override
                public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
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

        response.setContentType("application/json; charset=UTF-8");

        try {
            Client client = createJerseyClient();
            WebTarget webserviceClient = client.target(apiEndpointUrl);
            WebTarget webserviceRequest = webserviceClient.path(apiResource);

            String jsonResponse = webserviceRequest
                    .request(MediaType.APPLICATION_JSON)
                    .get(String.class);

            PrintWriter out = response.getWriter();
            out.println(jsonResponse);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error fetching country data: " + e.getMessage());
        }
    }
}
