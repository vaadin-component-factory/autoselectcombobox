package org.vaadin.addons.autoselectcombobox;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class VaadinElement {
    private final Locator locator;

    public VaadinElement(Locator locator) {
        this.locator = locator;
    }

    public static VaadinElement getAutoSelectComboBoxByLabel(Page page, String label) {
        return getByLabel(page, "vcf-auto-select-combo-box", label);
    }

    public static VaadinElement getByLabel(Page page, String tagName, String label) {
        return new VaadinElement(page.locator(tagName)
                .filter(new Locator.FilterOptions()
                        .setHas(page.getByRole(AriaRole.COMBOBOX,
                                new Page.GetByRoleOptions().setName(label)))
                ).first());
    }

    public Locator getLocator() {
        return locator;
    }


    /** Locator for the native input element inside the component. */
    public Locator getInputLocator() {
        return getLocator().locator("*[slot=\"input\"]").first(); // slot="helper"
    }

    /**
     * Set the field value by filling the input and dispatching a change event.
     */
    public void setValue(String value) {
        getInputLocator().fill(value);
        getLocator().dispatchEvent("change");
    }

    /** Assert that the input value matches the expected string. */
    public void assertValue(String value) {
        assertThat(getInputLocator()).hasValue(value);
    }

    /** Assert that the component is valid (not {@code invalid}). */
    public void assertValid() {
        assertThat(getLocator()).not().hasAttribute("invalid", "");
    }

    /** Assert that the component is invalid. */
    public void assertInvalid() {
        assertThat(getLocator()).hasAttribute("invalid", "");
    }
    /**
     * Focus the component.
     */
    public void focus() {
        getInputLocator().focus();
    }

}
