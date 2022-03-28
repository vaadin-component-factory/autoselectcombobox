package org.vaadin.addons.autoselectcombobox;

import java.util.Collection;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;

@Tag("autoselect-combo-box")
@JsModule("./autoselect-combo-box.js")
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