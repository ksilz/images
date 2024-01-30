## What's This?

This project demonstrates how to block image downloads with Selenium in Java. That's both for a locally launched Chrome instance and a remote browser instance.

## How Does It Work?

First, you need Java 17 or later.

[application.properties](https://github.com//ksilz/images/blob/main/src/main/resources/application.properties) controls the app:

- `app.browser.blockPictures.enabled`: `true` means images will be blocked, `false` means they won't. Here's the [code that configures this](https://github.com/ksilz/images/blob/fed143db8d33248b4ea2d37266f9ef8bbd732073/src/main/java/com/betterprojectsfaster/bugs/brightdata/scrapingbrowser/images/Browser.java#L43-L46) is here.
- `app.browser.remote.enabled`: `false` means launching a local Chrome instance, `true` means launching a remote browser. When `true`, you also need to se the next parameter.
- `app.browser.remote.url`: That's the URL of the remote browser.

You start the application from a terminal with `./gradlew` (Linux, macOS) or `gradlew.bat` (Windows). The application will launch the browser and go to the CNN home page. It then waits five seconds and quits.
