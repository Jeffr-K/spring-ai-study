package com.meshestra.springaistudy.dto;

import lombok.Data;

@Data
public class ReviewClassification {
  public enum Sentiment {
    POSITIVE, NEGATIVE, NEUTRAL
  }

  private String review;
  private Sentiment classification;
}
