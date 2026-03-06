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
    public void testFocusAndBlurIsValidAutoSelect() {
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("ComboBox Validation"))).isVisible();
        VaadinElement autoSelectComboBoxByLabel = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect with 1 item");
        assertThat(autoSelectComboBoxByLabel.getLocator()).isVisible();
        autoSelectComboBoxByLabel.assertValid();
        autoSelectComboBoxByLabel.focus();

        VaadinElement autoSelect2ComboBoxByLabel = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect with 2 items");
        autoSelect2ComboBoxByLabel.focus();
        autoSelectComboBoxByLabel.assertValid();
    }

    @Test
    public void testFocusAndBlurIsValidCombo() {
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("ComboBox Validation"))).isVisible();
        VaadinElement comboBoxByLabel = VaadinElement.getComboBoxByLabel(page, "People");
        assertThat(comboBoxByLabel.getLocator()).isVisible();
        comboBoxByLabel.assertValid();
        comboBoxByLabel.focus();

        VaadinElement autoSelect2ComboBoxByLabel = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect with 2 items");
        autoSelect2ComboBoxByLabel.focus();
        comboBoxByLabel.assertValid();
    }

    @Test
    public void testClearNonRequiredAutoSelectIsValid() {
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("ComboBox Validation"))).isVisible();
        VaadinElement autoSelectComboBoxByLabel = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect with 1 item");
        assertThat(autoSelectComboBoxByLabel.getLocator()).isVisible();
        autoSelectComboBoxByLabel.assertValid();
        autoSelectComboBoxByLabel.selectItem("Aaron Allen");

        VaadinElement autoSelect2ComboBoxByLabel = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect with 2 items");
        autoSelect2ComboBoxByLabel.focus();
        autoSelectComboBoxByLabel.assertValue("Aaron Allen");
        autoSelectComboBoxByLabel.assertValid();
        autoSelectComboBoxByLabel.clickClearButton();
        autoSelect2ComboBoxByLabel.focus();
        autoSelectComboBoxByLabel.assertValue("");
        autoSelectComboBoxByLabel.assertValid();
    }

    @Test
    public void testClearNonRequiredComboboxIsValid() {
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("ComboBox Validation"))).isVisible();
        VaadinElement comboBoxByLabel = VaadinElement.getComboBoxByLabel(page, "People");
        assertThat(comboBoxByLabel.getLocator()).isVisible();
        comboBoxByLabel.assertValid();
        comboBoxByLabel.selectItem("Aaron Allen");

        VaadinElement autoSelect2ComboBoxByLabel = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect with 2 items");
        autoSelect2ComboBoxByLabel.focus();
        comboBoxByLabel.assertValue("Aaron Allen");
        comboBoxByLabel.assertValid();
        comboBoxByLabel.clickClearButton();
        autoSelect2ComboBoxByLabel.focus();
        comboBoxByLabel.assertValue("");
        comboBoxByLabel.assertValid();
    }
}