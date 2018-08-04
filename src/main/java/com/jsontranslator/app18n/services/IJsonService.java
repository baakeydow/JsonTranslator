package com.jsontranslator.app18n.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface IJsonService {

  static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  static ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(Include.ALWAYS);

  public String translate(String dictionary) throws IOException;

  public static boolean jsonAreEqual(String json1, String json2) throws IOException {
    JsonNode tree1 = mapper.readTree(json1);
    JsonNode tree2 = mapper.readTree(json2);
    return tree1.equals(tree2);
  }

}