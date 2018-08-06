package com.translator.app18n.controllers;

import com.translator.app18n.services.IJsonService;
import com.translator.app18n.factory.IjsonFactory;

import java.util.Locale;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class TranslateController {

  @Autowired
  private IjsonFactory iJson;
  private String json;

  @RequestMapping(value = "/translate", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> translate() throws Exception {
    try {
      RestTemplate rq = new RestTemplate();
      json = rq.getForObject("https://21times2.com/db/listall", String.class);
      // json = IjsonFactory.getSampleJson("simpleKeys");
      IJsonService jsonService = iJson.getJsonService(json);
      String translated = jsonService.translate(IjsonFactory.getDictionary(new Locale("fr")));
      return new ResponseEntity<Object>(translated, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Failed try again...");
    } finally {
      IJsonService.outputStream.flush();
    }
    return new ResponseEntity<Object>("Failed !", HttpStatus.I_AM_A_TEAPOT);
  }
}