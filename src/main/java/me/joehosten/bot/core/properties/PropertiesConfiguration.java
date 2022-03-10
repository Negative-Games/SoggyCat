package me.joehosten.bot.core.properties;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class PropertiesConfiguration {

    private final HashMap<String, String> fields = new HashMap<>();
    private final String filePath;
    private final File file;

    public PropertiesConfiguration(File file) {
        if (!file.exists())
            try {
                throw new FileNotFoundException("The file " + file.getName() + " does not exist.");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        if (!file.getName().endsWith(".properties"))
            throw new InvalidFileExtensionException("The file " + file.getName() + " must be a .properties file.");

        filePath = file.getAbsolutePath();
        this.file = file;

        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try (OutputStream output = new FileOutputStream(filePath)) {
            Properties properties = new Properties();

            fields.forEach(properties::setProperty);

            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() throws IOException {
        fields.clear();
        try (InputStream inputStream = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            properties.forEach((key, value) -> fields.put(String.valueOf(key), String.valueOf(value)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(String path, String value) {
        if (fields.containsKey(path))
            fields.replace(path, value);
        else
            fields.put(path, value);
    }

    public int getInt(String path) {
        String string = getString(path);
        int value = 0;
        try {
            value = Integer.parseInt(string);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public int getInt(String path, int defaultValue) {
        String string = getString(path);
        int value = defaultValue;
        try {
            value = Integer.parseInt(string);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public double getDouble(String path) {
        String string = getString(path);
        double value = 0;
        try {
            value = Double.parseDouble(string);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public double getDouble(String path, double defaultValue) {
        String string = getString(path);
        double value = defaultValue;
        try {
            value = Double.parseDouble(string);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public String getString(String path) {
        return getString(path, null);
    }

    public String getString(String path, String defaultValue) {
        try (InputStream inputStream = new FileInputStream(file)) {
            Properties properties = new Properties();

            properties.load(inputStream);

            if (defaultValue != null)
                return properties.getProperty(path, defaultValue);
            else
                return properties.getProperty(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean getBoolean(String path) {
        return getBoolean(path, false);
    }

    public boolean getBoolean(String path, boolean defaultValue) {
        String string = getString(path);
        boolean value = defaultValue;
        try {
            value = Boolean.parseBoolean(string);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }


}
