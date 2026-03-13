package org.vaadin.addons.autoselectcombobox;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.DispatchEventOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.server.LocalServerPort;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BasePlayWrightIT implements HasTestView {

    @LocalServerPort
    private int port;

    @Override
    public String getUrl() {
        return String.format("http://localhost:%d/", port);
    }

    String WAIT_FOR_VAADIN_SCRIPT =
    // @formatter:off
            "() => {"
            + "if (window.Vaadin && window.Vaadin.Flow && window.Vaadin.Flow.clients) {"
            + "  var clients = window.Vaadin.Flow.clients;"
            + "  for (var client in clients) {"
            + "    if (clients[client].isActive()) {"
            + "      return false;"
            + "    }"
            + "  }"
            + "  return true;"
            + "} else if (window.Vaadin && window.Vaadin.Flow && window.Vaadin.Flow.devServerIsNotLoaded) {"
            + "  return false;"
            + "} else {"
            + "  return true;"
            + "}"
            + "}";
    // @formatter:on

    protected Page page;
    protected static Playwright playwright;
    protected static Browser browser;

    @BeforeEach
    public void setupTest() throws Exception {
        page = browser.newPage();
        page.navigate(getUrl() + getView());
        page.waitForFunction(WAIT_FOR_VAADIN_SCRIPT);
        page.setDefaultNavigationTimeout(4000);
        page.setDefaultTimeout(5000);
    }

    @AfterEach
    public void cleanupTest() {
        page.close();
    }

    @AfterAll
    public static void cleanup() {
        browser.close();
        playwright.close();
    }

    @BeforeAll
    public static void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new LaunchOptions()
                .setHeadless(false));
    }

}