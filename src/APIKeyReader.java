import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class APIKeyReader {
    public static String getAPIKey() {
        Properties properties = new Properties();
        try {
            FileInputStream input = new FileInputStream("config.properties");
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("api.key");
    }
    public static String getBaseUrl() {
        Properties properties = new Properties();
        try {
            FileInputStream input = new FileInputStream("config.properties");
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("base.url");
    }
}
