package cinema.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvironmentConfig {
    private static Dotenv dotenv = Dotenv.load();

    public static void loadEnvVariables() {
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

        System.setProperty("STATS_PASSWORD", dotenv.get("STATS_PASSWORD"));

        System.setProperty("REDIS_HOST", dotenv.get("REDIS_HOST"));
        System.setProperty("REDIS_PORT", dotenv.get("REDIS_PORT"));
    }
}
