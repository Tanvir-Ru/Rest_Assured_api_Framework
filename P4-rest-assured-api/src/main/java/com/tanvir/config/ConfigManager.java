package com.tanvir.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * Loads config.properties and overrides with System env vars.
 * Priority: System.getenv > System.getProperty > config.properties
 */
public class ConfigManager {

    private static final Properties props = new Properties();
    private static ConfigManager instance;

    private ConfigManager() {
        try (InputStream is = ConfigManager.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (is != null) props.load(is);
        } catch (Exception e) {
            System.err.println("⚠ config.properties not found — using env vars only");
        }
    }

    public static ConfigManager getInstance() {
        if (instance == null) instance = new ConfigManager();
        return instance;
    }

    public String get(String key) {
        String env = System.getenv(key.toUpperCase().replace(".", "_"));
        if (env != null && !env.isBlank()) return env;
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) return sys;
        return props.getProperty(key, "");
    }

    public String get(String key, String defaultValue) {
        String val = get(key);
        return (val == null || val.isBlank()) ? defaultValue : val;
    }

    public String getBaseUrl()  { return get("base.url", "https://reqres.in"); }
    public String getApiKey()   { return get("api.key", "reqres-free-v1"); }
    public String getEnv()      { return get("env", "dev"); }
}
