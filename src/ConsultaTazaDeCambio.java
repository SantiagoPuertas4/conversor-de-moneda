import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaTazaDeCambio {
    private static final HttpClient client = HttpClient.newHttpClient();

    public static boolean validarApiKey(String apiKey) {
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/codes";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body().contains("\"result\":\"success\"");
        } catch (Exception e) {
            System.err.println("Error al validar API key: " + e.getMessage());
            return false;
        }
    }

    public static CodigosMonedaResponse obtenerCodigosMoneda(String apiKey) {
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/codes";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            return gson.fromJson(response.body(), CodigosMonedaResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener códigos de moneda: " + e.getMessage());
        }
    }

    public static String obtenerTasasDeCambio(String apiKey, String monedaBase) {
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + monedaBase;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener tasas de cambio: " + e.getMessage());
        }
    }

    public static double obtenerConversionRate(String apiKey, String monedaBase, String monedaObjetivo) {
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + monedaBase + "/" + monedaObjetivo;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            ConversionResponse data = gson.fromJson(response.body(), ConversionResponse.class);

            if (!"success".equals(data.getResult())) {
                throw new RuntimeException("La conversión falló: " + data.getResult());
            }

            return data.getConversion_rate();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener tasa de conversión: " + e.getMessage());
        }
    }

}
