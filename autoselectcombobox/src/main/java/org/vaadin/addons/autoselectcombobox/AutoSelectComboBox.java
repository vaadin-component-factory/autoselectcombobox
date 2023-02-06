package org.vaadin.addons.autoselectcombobox;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

import java.util.Collection;

@Tag("vcf-auto-select-combo-box")
@NpmPackage(value = "@vaadin-component-factory/vcf-auto-select-combobox", version="16.0.1")
@JsModule("@vaadin-component-factory/vcf-auto-select-combobox/src/vcf-auto-select-combobox.js")
public class AutoSelectComboBox<T> extends ComboBox<T> {

    public AutoSelectComboBox() {
        super();
    }

    public AutoSelectComboBox(int pageSize) {
        super(pageSize);
    }

    public AutoSelectComboBox(String label, Collection<T> items) {
        super(label, items);
    }

    public AutoSelectComboBox(String label, T... items) {
        super(label, items);
    }

    public AutoSelectComboBox(String label) {
        super(label);
    }

}