package com.meshestra.springaistudy.output;

import com.meshestra.springaistudy.dto.Hotel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/output/custom-list")
@Slf4j
public class CustomBeanOutputConverterController {
  @PostMapping(
    value = "/list-output-converter",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Hotel listOutputConverter(String city) {
    CustomBeanOutputConverter service = new CustomBeanOutputConverter();
    Hotel hotel = service.beanOutputConverterHighLevel(city);
    return hotel;
  }
}
