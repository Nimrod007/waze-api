package com.waze;

import io.dropwizard.configuration.ConfigurationSourceProvider;

import java.io.*;

/**
 * @author Nimrod_Lahav
 * @since 16-Dec-2015
 */
public class ConfigSource implements ConfigurationSourceProvider {

    @Override
    public InputStream open(String s) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("conf.yml").getFile());
        if (!file.exists()) {
            throw new FileNotFoundException("File " + file + " not found");
        }

        return new FileInputStream(file);
    }
}
