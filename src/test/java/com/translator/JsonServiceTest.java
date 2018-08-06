package com.translator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.translator.app18n.factory.IjsonFactory;

/**
 * Unit test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = app18nMock.class)
public class JsonServiceTest {
  @Autowired
  private IjsonFactory iJson;
  private String jsonToTranslate, jsonModel, dictionary;

  @Before
  public void init() throws IOException {
    jsonToTranslate = app18nMock.getSampleJson("simpleKeys");
    jsonModel = app18nMock.getSampleJson("simpleKeysTranslated");
    dictionary = "{\"sellers\": \"Vendeurs\",\"buyers\": \"Acheteurs\",\"nbitemsbought\": \"Numbers of Items bought\"}";
  }

  @Test
  public void translateJson() {
    try {
      String translated = iJson.getJsonService(jsonToTranslate).translate(dictionary);
      assertEquals(translated, jsonModel);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(e.toString());
    }
  }
}