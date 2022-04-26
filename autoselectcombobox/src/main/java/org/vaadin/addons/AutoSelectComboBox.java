package org.vaadin.addons;

import java.util.Collection;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

@Tag("autoselect-combo-box")
@NpmPackage(value = "@vaadin-component-factory/vcf-auto-select-combobox", version="14.8.7")
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