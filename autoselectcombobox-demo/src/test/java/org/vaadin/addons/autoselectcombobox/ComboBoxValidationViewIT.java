package org.vaadin.addons.autoselectcombobox;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ComboBoxValidationViewIT extends BasePlayWrightIT {


    @Override
    public String getView() {
        return "";
    }

    @BeforeEach
    public void setupTest() throws Exception {
        super.setupTest();
    }


    @Test
    public void testFocusAndBlurIsValid() {
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("ComboBox Validation"))).isVisible();
        VaadinElement autoSelectComboBoxByLabel = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect with 1 item");
        assertThat(autoSelectComboBoxByLabel.getLocator()).isVisible();
        autoSelectComboBoxByLabel.assertValid();
        autoSelectComboBoxByLabel.focus();

        VaadinElement autoSelect2ComboBoxByLabel = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect with 2 items");
        autoSelect2ComboBoxByLabel.focus();
        autoSelectComboBoxByLabel.assertValid();
    }
}