import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiExchange {

    private static final String API_KEY = System.getenv("EXCHANGE_API_KEY");

    private static final String URL =
            "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";

    // Validación al cargar la clase
    static {
        if (API_KEY == null || API_KEY.isBlank()) {
            throw new RuntimeException(
                    "API Key no configurada. Define la variable de entorno EXCHANGE_API_KEY"
            );
        }
    }

    public static JsonObject obtenerTasas() {

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(
                    request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser
                    .parseString(response.body())
                    .getAsJsonObject();

            if (!json.has("conversion_rates")) {
                System.out.println("Respuesta inválida de la API:");
                System.out.println(json);
                return null;
            }

            return json.getAsJsonObject("conversion_rates");

        } catch (Exception e) {
            System.out.println("Error al conectar con la API");
            e.printStackTrace();
            return null;
        }
    }
}