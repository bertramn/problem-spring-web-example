package com.example.demoservlet;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.contract.wiremock.WireMockConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
public class WireMockServerPortTest {

  @Autowired
  private WireMockServer wireMockServer;

  @Value("${wiremock.server.port}")
  private int wireMockServerPortProperty;

  @Value("${app.wiremock.url}")
  private String applicationInjectedProperty;

  // FAILS
  @Test
  public void itShouldMatchEnvironmentProperty() {
    assertEquals(wireMockServer.port(), wireMockServerPortProperty);
  }

  @Test
  public void itShouldMatchApplicationConfigurationExpandedProperty() {
    assertEquals("http://localhost:" + wireMockServer.port(), applicationInjectedProperty);
  }

  /**
   * As soon as I
   */
  @Configuration
  static class TestConfiguration {

    @Bean
    WireMockConfigurationCustomizer optionsCustomizer() {
      return options -> {
        // do something with options
        options.bindAddress();
      };
    }

  }

}

