package com.betterprojectsfaster.bugs.brightdata.scrapingbrowser.images;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class ImagesApplication {

  @NotNull
  @NonNull
  private final Browser browser;

  public static void main(String[] args) {
    SpringApplication.run(ImagesApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void run() {
    log.info("run: START");
    browser.goToPage();
    log.info("run: STOP");
    System.exit(0);
  }
}
