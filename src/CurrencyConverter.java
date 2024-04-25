import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import org.json.JSONObject;
public class CurrencyConverter {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Seja bem-vindo/a ao Conversor de Moedas\n");
            System.out.println("1) Dólar => Peso argentino");
            System.out.println("2) Peso argentino => Dólar");
            System.out.println("3) Dólar => Real brasileiro");
            System.out.println("4) Real brasileiro => Dólar");
            System.out.println("5) Dólar => Peso colombiano");
            System.out.println("6) Peso colombiano => Dólar");
            System.out.println("7) Sair\n");
            System.out.print("Escolha uma opção válida: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.println("Você escolheu: Dólar => Peso argentino");
                    // Add logic for Dollar to Argentine Peso conversion here
                    break;
                case "2":
                    System.out.println("Você escolheu: Peso argentino => Dólar");
                    // Add logic for Argentine Peso to Dollar conversion here
                    break;
                case "3":
                    System.out.println("Você escolheu: Dólar => Real brasileiro");
                    // Add logic for Dollar to Brazilian Real conversion here
                    break;
                case "4":
                    System.out.println("Você escolheu: Real brasileiro => Dólar");
                    // Add logic for Brazilian Real to Dollar conversion here
                    break;
                case "5":
                    System.out.println("Você escolheu: Dólar => Peso colombiano");
                    // Add logic for Dollar to Colombian Peso conversion here
                    break;
                case "6":
                    System.out.println("Você escolheu: Peso colombiano => Dólar");
                    // Add logic for Colombian Peso to Dollar conversion here
                    break;
                case "7":
                    System.out.println("Até mais!");
                    exit = true;
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                    break;
            }
        }

        scanner.close();
    }

    private static Map<String, Double> getExchangeRates(String baseCurrency) {
        try {
            String apiKey = APIKeyReader.getAPIKey();
            // Criar URL da solicitação
            URL url = new URL("https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + baseCurrency);

            // Abrir conexão HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Ler a resposta da API
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Converter a resposta em JSON
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Verificar se a solicitação foi bem-sucedida
            if (!jsonResponse.getString("result").equals("success")) {
                System.out.println("Erro na solicitação da API: " + jsonResponse.getString("error"));
                return null;
            }

            // Obter as taxas de câmbio do JSON
            JSONObject rates = jsonResponse.getJSONObject("conversion_rates");

            // Converter as taxas de câmbio para um mapa
            Map<String, Double> exchangeRates = new HashMap<>();
            for (String currency : rates.keySet()) {
                exchangeRates.put(currency, rates.getDouble(currency));
            }

            // Adicionar a taxa de câmbio do USD para USD (que é 1.0)
            exchangeRates.put("USD", 1.0);

            return exchangeRates;
        } catch (IOException e) {
            System.out.println("Erro ao fazer solicitação HTTP: " + e.getMessage());
            return null;
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
