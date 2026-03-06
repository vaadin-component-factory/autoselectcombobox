package org.vaadin.addons.autoselectcombobox;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBox.ComboBoxI18n;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Required Combo Box")
@Menu(order = 2)
@Route("Required")
public class RequiredComboView extends AbstractDemo {

    private PersonService personService;

    @Override
    protected void initView() {
        personService = new PersonService(1);
        addComboValidation();
    }

    private void addComboValidation() {
        DataProvider<Person, String> dataProvider = DataProvider.fromFilteringCallbacks(
                query -> personService.fetch(query.getOffset(), query.getLimit(), query.getFilter().orElse(null)).stream(),
                query -> personService.count(query.getFilter().orElse(null)));

        // end-source-example
        addCard("ComboBox, required value",
                requiredAutoSelectComboBox(),
                requiredWithInitialValueAutoSelectComboBox(),
                requiredNormalComboBox(),
                requiredWithInitialValueNormalComboBox(),
                new Anchor("#", "Focus target for testing"));
    }


    private Component requiredAutoSelectComboBox() {
        AutoSelectComboBox<String> requiredCombo = new AutoSelectComboBox<>("Autoselect Required");
        requiredCombo.setItems("Bar", "Foo", "Baz", "Quizzle", "Quux", "Flim", "Flam", "Raquette", "Boslix", "Suppum", "Amliaum");
        requiredCombo.setRequired(true);
        requiredCombo.setI18n(new ComboBoxI18n()
                .setRequiredErrorMessage("Field is required"));
        VerticalLayout vl = new VerticalLayout();
        Span span = new Span("Value: ");
        requiredCombo.addValueChangeListener( e -> span.setText("Value: " + e.getValue()));
        vl.add(requiredCombo, span);
        return vl;
    }

    private Component requiredWithInitialValueAutoSelectComboBox() {
        AutoSelectComboBox<String> requiredCombo = new AutoSelectComboBox<>("Autoselect Required");
        requiredCombo.setItems("Bar", "Foo", "Baz", "Quizzle", "Quux", "Flim", "Flam", "Raquette", "Boslix", "Suppum", "Amliaum");
        requiredCombo.setRequired(true);
        requiredCombo.setValue("Foo");
        requiredCombo.setI18n(new ComboBoxI18n()
                .setRequiredErrorMessage("Field is required"));
        VerticalLayout vl = new VerticalLayout();
        Span span = new Span("Value: ");
        requiredCombo.addValueChangeListener( e -> span.setText("Value: " + e.getValue()));
        vl.add(requiredCombo, span);
        return vl;
    }

    private ComboBox<String> requiredNormalComboBox() {
        ComboBox<String> combo = new ComboBox<>("Regular required");
        combo.setItems("Bar", "Foo", "Baz", "Quizzle", "Quux", "Flim", "Flam", "Raquette", "Boslix", "Suppum", "Amliaum");
        combo.setRequired(true);
        combo.setI18n(new ComboBoxI18n()
                .setRequiredErrorMessage("Field is required"));
        return combo;
    }

    private ComboBox<String> requiredWithInitialValueNormalComboBox() {
        ComboBox<String> combo = new ComboBox<>("Regular required");
        combo.setItems("Bar", "Foo", "Baz", "Quizzle", "Quux", "Flim", "Flam", "Raquette", "Boslix", "Suppum", "Amliaum");
        combo.setRequired(true);
        combo.setI18n(new ComboBoxI18n()
                .setRequiredErrorMessage("Field is required"));
        combo.setValue("Foo");
        return combo;
    }

}