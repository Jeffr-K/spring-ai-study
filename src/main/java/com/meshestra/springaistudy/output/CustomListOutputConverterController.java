package com.meshestra.springaistudy.output;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/output/custom-list")
@Slf4j
public class CustomListOutputConverterController {
  private CustomListOutputConverter service;

  @PostMapping(
    value = "/list-output-converter",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public List<String> listOutputConverter(@RequestParam("city") String city) {
    List<String> hotelList = service.listOutputConverterHighLevel(city);
    return hotelList;
  }
}
