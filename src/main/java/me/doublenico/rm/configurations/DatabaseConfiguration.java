package me.doublenico.rm.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import me.doublenico.rm.utils.FileManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DatabaseConfiguration {

    public DatabaseModel loadConfiguration(String file) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        DatabaseModel order;

        File installationFolder = new FileManager().getInstallationFolder();
        File yamlFile = new File(installationFolder, file);
        if (!yamlFile.exists()) {
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file)) {
                if (inputStream == null) {
                    throw new NullPointerException("The " + file + " should not be null!");
                }
                Files.copy(inputStream, yamlFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        try {
            order = mapper.readValue(yamlFile, DatabaseModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return order;
    }
}