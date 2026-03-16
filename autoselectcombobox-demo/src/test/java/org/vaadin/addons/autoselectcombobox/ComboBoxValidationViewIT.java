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
    /**
     * Reproduces issue #5: Validation error not cleared from empty field.
     * Steps:
     * 1. Type invalid value "ABC"
     * 2. Blur -> field becomes invalid (correct)
     * 3. Clear the input
     * 4. Blur -> field should become valid (bug: stays invalid)
     *
     * @see <a href="https://github.com/vaadin-component-factory/autoselectcombobox/issues/5">Issue #5</a>
     */
    @Test
    public void testInvalidValueClearedBecomesValid() {
        VaadinElement comboBox = VaadinElement.getAutoSelectComboBoxByLabel(page, "Autoselect with 2 items");
        VaadinElement otherField = VaadinElement.getAutoSelectComboBoxByLabel(page, "AutoSelect with 1 item");
        assertThat(comboBox.getLocator()).isVisible();
        comboBox.assertValid();

        // Step 1-2: Type invalid value and blur -> should become invalid
        comboBox.type("ABC");
        otherField.focus(); // blur the combo box
        comboBox.assertInvalid();

        // Step 3-4: Clear the input and blur -> should become valid
        comboBox.clearInput();
        otherField.focus(); // blur again
        comboBox.assertValid();
        comboBox.assertValue("");
    }

}