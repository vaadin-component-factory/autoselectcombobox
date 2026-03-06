package org.vaadin.addons.autoselectcombobox;

import java.util.Map;

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

    public static VaadinElement getComboBoxByLabel(Page page, String label) {
        return getByLabel(page, "vaadin-combo-box", label);
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

    /** Locator for the clear button ({@code part~=clear-button}). */
    public Locator getClearButtonLocator() {
        return getLocator().locator("[part~=\"clear-button\"]");
    }

    /** Click the clear button. */
    public void clickClearButton() {
        getClearButtonLocator().click();
    }

    /**
     * Select an item by its visible label.
     * Opens the overlay, clicks the matching item.
     *
     * @param item label of the item to select
     */
    public void selectItem(String item) {
        open();
        getOverlayItem(item).click();
    }
    /**
     * Open the combo box overlay.
     */
    public void open() {
        setProperty("opened", true);
    }

    private void setProperty(String name, Object value) {
        locator.evaluate("(el, args) => el[args.name] = args.value", Map.of("name", name, "value", value));
    }

    private Locator getOverlayItem(String label) {
        return getLocator().page().locator("vaadin-combo-box-item:not([hidden])")
                .filter(new Locator.FilterOptions()
                        .setHasText(label)).first();
    }
}
