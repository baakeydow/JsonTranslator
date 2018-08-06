package com.translator.app18n.services;

import com.translator.app18n.factory.IjsonFactory;
import com.translator.app18n.services.IJsonService;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Json parser.
 */
@Service
@Component
public class JsonService implements IjsonFactory {

  private JsonNode jsonSource;
  private JsonGenerator generator;

  public IJsonService getJsonService(String json) throws IOException {
    return new i18n(json);
  }

  private class i18n implements IJsonService {

    public i18n(String json) throws IOException {
      DataOutput output = new DataOutputStream(outputStream);
      generator = new JsonFactory().createGenerator(output, JsonEncoding.UTF8).setCodec(mapper);
      jsonSource = mapper.readTree(json);
    };

    public String translate(String dictionary) throws IOException {
      try {
        JsonNode dictionaryRootNode = mapper.readTree(dictionary);
        processJson(jsonSource, dictionaryRootNode);
        String done = outputStream.toString();
        System.out.println("\nHas been translated => " + !IJsonService.jsonAreEqual(jsonSource.toString(), done));
        return done;
      } catch (JsonProcessingException e) {
        e.printStackTrace();
        throw new IOException("json parsing failed !! you did not provide a valid JSON schema, rtfm...");
      } finally {
        outputStream.reset();
      }
    };

    private void processJson(JsonNode parentNode, JsonNode lexiconNode) throws IOException {
      JsonParser jsonParser = new JsonFactory().createParser(parentNode.toString());
      while (!jsonParser.isClosed()) {
        JsonToken jsonToken = jsonParser.nextToken();
        if (jsonToken != null && !jsonToken.isScalarValue() && !JsonToken.FIELD_NAME.equals(jsonToken)) {
          handleContainerNode(jsonToken);
        } else if (jsonToken != null) {
          writeKeyValue(jsonToken, jsonParser, lexiconNode);
        }
      }
      generator.close();
    };

    private void handleContainerNode(JsonToken token) throws IOException {
      if (token.isStructStart())
        if (JsonToken.START_OBJECT.equals(token))
          generator.writeStartObject();
        else
          generator.writeStartArray();
      else if (JsonToken.END_OBJECT.equals(token))
        generator.writeEndObject();
      else
        generator.writeEndArray();
    };

    private void writeKeyValue(JsonToken token, JsonParser parser, JsonNode lexiconNode) throws IOException {
      switch (token) {
      case FIELD_NAME:
        String txt = parser.getValueAsString();
        if (!lexiconNode.findPath(txt).isMissingNode()) {
          generator.writeFieldName(lexiconNode.findPath(txt).toString().replace("\"", ""));
        } else {
          generator.writeFieldName(txt);
        }
        break;
      case VALUE_STRING:
        generator.writeString(parser.getText());
        break;
      case VALUE_NULL:
        generator.writeNull();
        break;
      case VALUE_FALSE:
      case VALUE_TRUE:
        generator.writeBoolean(parser.getValueAsBoolean());
        break;
      case VALUE_NUMBER_INT:
        generator.writeNumber(parser.getValueAsInt());
        break;
      case VALUE_NUMBER_FLOAT:
        generator.writeNumber(parser.getValueAsDouble());
        break;
      default:
        break;
      }
    };
  };
}
