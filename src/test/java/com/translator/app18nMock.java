package com.translator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.translator.app18n.factory", "com.translator.app18n.services" })
public class app18nMock {
  public static String getSampleJson(String name) throws IOException {
    ClassLoader cl = app18nMock.class.getClassLoader();
    File file = new File(cl.getResource("fixtures/" + name + ".json").getPath());
    return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
  }
}