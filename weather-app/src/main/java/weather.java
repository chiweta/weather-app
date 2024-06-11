import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class weather {

    private static final String API_KEY = "661e1f665a2d40af886162817240806";
    
    public static void main(String[] args) {
        System.out.println("What city would you like to see the weather for?");
        Scanner kb = new Scanner(System.in);
        String city = kb.nextLine();
        kb.close();

        String urlString = "http://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + city;

        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Create an input stream reader to read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                // Read the response line by line
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                // Close the input stream
                in.close();

                // Parse the response content
                JSONObject jsonResponse = new JSONObject(content.toString());
                String currentWeather = jsonResponse.getJSONObject("current").getJSONObject("condition").getString("text");
                String timezone = jsonResponse.getJSONObject("location").getString("tz_id");
                double temperature = jsonResponse.getJSONObject("current").getDouble("temp_f");

                // Print the parsed data
                System.out.println("Temperature: " + temperature+"F");
                System.out.println("Current Weather: " + currentWeather);
                System.out.println("Timezone: " + timezone);
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
