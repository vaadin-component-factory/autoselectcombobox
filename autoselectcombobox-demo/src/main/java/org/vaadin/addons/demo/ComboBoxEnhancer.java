package org.vaadin.addons.demo;

import java.util.Objects;
import java.util.stream.Stream;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.function.SerializableBiFunction;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.shared.Registration;

/**
 * Contains utility methods to enhance {@link ComboBox}.
 * 
 * @author flang
 *
 * @param <T>
 */
public class ComboBoxEnhancer<T> {

    private final ComboBox<T> comboBox;

    private Registration autoSelectRegistration;

    public ComboBoxEnhancer(ComboBox<T> comboBox) {
        this.comboBox = comboBox;
    }

    /**
     * Enables autoselect support for {@link ComboBox}. Automatically selects {@link ComboBox} value when a single item is provided.
     * 
     * @param emptyValue the empty value to set when no items were provided, not null.
     * @param displayValueGenerator value to display when empty value is set, not null.
     * @param itemsProvider return a stream of items, not null.
     */
    public void enableAutoSelect(T emptyValue, SerializableBiFunction<String, T, T> displayValueGenerator,
            SerializableFunction<String, Stream<T>> itemsProvider) {
        Objects.requireNonNull(emptyValue, "The empty value can not be null");
        Objects.requireNonNull(displayValueGenerator, "The display value generator can not be null");
        Objects.requireNonNull(itemsProvider, "The items provider can not be null");
        autoSelectRegistration = comboBox.addCustomValueSetListener(event -> {
            T item = itemsProvider.apply(event.getDetail()).reduce((u, v) -> emptyValue).orElse(emptyValue);
            if (!emptyValue.equals(item)) {
                comboBox.setValue(item);
            } else {
                comboBox.setValue(displayValueGenerator.apply(event.getDetail(), emptyValue));
            }
        });
    }

    /**
     * Disables autoselect support for {@link ComboBox}.
     */
    public void disabledAutoSelect() {
        if (autoSelectRegistration != null) {
            autoSelectRegistration.remove();
            autoSelectRegistration = null;
        }
    }

}
