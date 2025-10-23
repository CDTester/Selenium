package com.cdTester.tests.selenium.web;

import org.junit.jupiter.api.BeforeEach;

public class BaseFirefoxTest extends BaseTest {

  @BeforeEach
  public void setup() {
    startFirefoxDriver();
  }
}