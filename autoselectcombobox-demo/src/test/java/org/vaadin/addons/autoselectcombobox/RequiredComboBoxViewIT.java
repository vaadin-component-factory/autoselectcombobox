package org.vaadin.addons.autoselectcombobox;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RequiredComboBoxViewIT extends BasePlayWrightIT {


    @Override
    public String getView() {
        return "Required";
    }

    @BeforeEach
    public void setupTest() throws Exception {
        super.setupTest();
    }


    @Test
    public void testInitialStatesValid() {
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Required Combo Box"))).isVisible();
        VaadinElement autoSelectComboBoxByLabel = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect Required");
        autoSelectComboBoxByLabel.assertValid();
        autoSelectComboBoxByLabel.assertValue("");

        VaadinElement autoSelect2ComboBoxByLabel = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect2 Required");
        autoSelect2ComboBoxByLabel.assertValid();
        autoSelect2ComboBoxByLabel.assertValue("Foo");

        VaadinElement comboBoxByLabel = VaadinElement.getComboBoxByLabel(page, "Regular required");
        comboBoxByLabel.assertValid();
        comboBoxByLabel.assertValue("");

        VaadinElement comboBox2ByLabel = VaadinElement.getComboBoxByLabel(page, "Regular2 required");
        comboBox2ByLabel.assertValid();
        comboBox2ByLabel.assertValue("Foo");
    }

    @Test
    public void testFocusAndNoChangesIsValidAutoSelect() {
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Required Combo Box"))).isVisible();
        VaadinElement autoSelectComboBoxByLabel = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect Required");
        autoSelectComboBoxByLabel.assertValid();
        autoSelectComboBoxByLabel.focus();

        VaadinElement autoSelect2ComboBoxByLabel = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect2 Required");
        autoSelect2ComboBoxByLabel.focus();
        autoSelectComboBoxByLabel.assertValid();
    }

    @Test
    public void testFocusAndNoChangesIsValidCombobox() {
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Required Combo Box"))).isVisible();
        VaadinElement comboBoxByLabel = VaadinElement.getComboBoxByLabel(page, "Regular required");
        comboBoxByLabel.assertValid();
        comboBoxByLabel.focus();

        VaadinElement autoSelect2ComboBoxByLabel = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect2 Required");
        autoSelect2ComboBoxByLabel.focus();
        comboBoxByLabel.assertValid();
    }

    @Test
    public void testFocusAndClearIsInvalidAutoSelect() {
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Required Combo Box"))).isVisible();
        VaadinElement autoSelectComboBoxByLabel = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect2 Required");
        autoSelectComboBoxByLabel.assertValid();
        autoSelectComboBoxByLabel.setValue("");

        VaadinElement autoSelect2ComboBoxByLabel = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect Required");
        autoSelect2ComboBoxByLabel.focus();
        autoSelectComboBoxByLabel.assertInvalid();
    }

    @Test
    public void testFocusAndClearIsInvalidCombobox() {
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Required Combo Box"))).isVisible();
        VaadinElement comboBoxByLabel = VaadinElement.getComboBoxByLabel(page, "Regular2 required");
        comboBoxByLabel.assertValid();
        comboBoxByLabel.setValue("");

        VaadinElement autoSelect2ComboBoxByLabel = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect2 Required");
        autoSelect2ComboBoxByLabel.focus();
        comboBoxByLabel.assertInvalid();
    }
}