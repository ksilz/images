package com.betterprojectsfaster.bugs.brightdata.scrapingbrowser.images;

import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Browser {

  @Value("#{new Boolean('${app.browser.remote.enabled:true}')}")
  private boolean remoteBrowser;

  @Value("#{new Boolean('${app.browser.blockPictures.enabled:true}')}")
  private boolean blockPictures;

  @Value("${app.browser.remote.url:''}")
  private String remoteBrowserUrl;

  private RemoteWebDriver driver;

  @PostConstruct
  void init() throws IOException {
    log.info("Setting up Selenium...");

    var options = new ChromeOptions();

    if (blockPictures) {
      Map<String, Object> prefs = new HashMap<>();
      prefs.put("profile.managed_default_content_settings.images", 2);
      options.setExperimentalOption("prefs", prefs);
      log.info("Blocking pictures");
    } else {
      log.info("NOT blocking pictures");
    }

    if (remoteBrowser) {

      if (StringUtils.isNotBlank(remoteBrowserUrl)) {
        options.setCapability("browserName", "chrome");

        var browserUrl = new URL(remoteBrowserUrl);

        driver = new RemoteWebDriver(browserUrl, options);
        log.info("Remote browser initialized.");
      } else {
        throw new InvalidArgumentException("Remote browser URL not set!");
      }

    } else {
      System.setProperty("webdriver.http.factory", "jdk-http-client");
      WebDriverManager.chromedriver().setup();
      driver = new ChromeDriver(options);
      log.info("Local browser initialized.");
    }

    log.info("Done setting up Selenium.");
  }

  public void goToPage() {

    log.info("Opening page...");

    try {
      driver.get("https://www.cnn.com");
      log.info("Waiting for page to finish loading...");
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    } catch (RuntimeException e) {
      log.error("Error while loading page", e);
    } finally {
      try {
        log.info("Waiting for 5 seconds...");
        Thread.sleep(5 * 1_000L);
      } catch (InterruptedException e) {
        //
      }
    }
  }
}
