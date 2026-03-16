package org.vaadin.addons.autoselectcombobox;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Reproduces issue #7: Single-value Autoselect with autofocus clears the value on blur.
 *
 * @see <a href="https://github.com/vaadin-component-factory/autoselectcombobox/issues/7">Issue #7</a>
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AutoFocusComboBoxValidationViewIT extends BasePlayWrightIT {

    @Override
    public String getView() {
        return "auto-focus";
    }

    @BeforeEach
    public void setupTest() throws Exception {
        super.setupTest();
    }

    /**
     * Issue #7: A prepopulated autoselect combobox with only one available item
     * and setAutofocus(true) loses the value on blur.
     *
     * Steps:
     * 1. Page loads with AutoSelectComboBox prepopulated with "Test" and autofocus enabled
     * 2. Click elsewhere to blur the combo box
     * 3. Value should remain "Test" and field should be valid
     */
    @Test
    public void testAutofocusSingleValueRetainsValueOnBlur() {
        VaadinElement comboBox = VaadinElement.getAutoSelectComboBoxByLabel(page, "AutoSelectComboBox");
        assertThat(comboBox.getLocator()).isVisible();

        // Verify initial value is "Test"
        comboBox.assertValue("Test");
        comboBox.assertValid();

        // Blur by clicking the focus target link
        page.getByText("Focus target for testing").focus();

        // After blur, value should still be "Test" and field should be valid
        comboBox.assertValue("Test");
        comboBox.assertValid();
    }
}
