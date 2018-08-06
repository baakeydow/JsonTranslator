package com.translator.app18n.factory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.io.IOException;

import com.translator.app18n.services.IJsonService;

public interface IjsonFactory {
  public IJsonService getJsonService(String json) throws IOException;

  public static String getDictionary(Locale locale) throws IOException {
    ClassLoader cl = IjsonFactory.class.getClassLoader();
    File dictionary = new File(cl.getResource("static/words.json").getPath());
    return new String(Files.readAllBytes(Paths.get(dictionary.getAbsolutePath())));
  }

  public static String getSampleJson(String name) throws IOException {
    ClassLoader cl = IjsonFactory.class.getClassLoader();
    File dictionary = new File(cl.getResource(name + ".json").getPath());
    return new String(Files.readAllBytes(Paths.get(dictionary.getAbsolutePath())));
  }
}