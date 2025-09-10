package org.vaadin.addons.autoselectcombobox;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("ComboBox validation")
@Route("")
public class ComboBoxValidation extends AbstractDemo {

    private org.vaadin.addons.autoselectcombobox.PersonService personService;

    @Override
    protected void initView() {
        personService = new PersonService(1);
        addComboValidation();
    }

    private void addComboValidation() {
        DataProvider<Person, String> dataProvider = DataProvider.fromFilteringCallbacks(
                query -> personService.fetch(query.getOffset(), query.getLimit(), query.getFilter().orElse(null)).stream(),
                query -> personService.count(query.getFilter().orElse(null)));

        AutoSelectComboBox<String> asComboBoxReadOnly = comboBoxReadOnly();

        Button button = new Button("Check value", e -> {
            Notification.show(asComboBoxReadOnly.getValue());
        });

        // end-source-example
        addCard("ComboBox with autoselect", regularComboBox(dataProvider),
                comboBoxMultipleItems(dataProvider), comboBoxTwoItems(), asComboBoxReadOnly,
                comboBoxWithAutoFocus(), comboBoxWithBinderValidation(),
                new Anchor("#", "Focus target for testing"), button);
    }

    private ComboBox<Person> regularComboBox(DataProvider<Person, String> dataProvider) {
        ComboBox<Person> comboBoxDefault = new ComboBox<>("People");
        comboBoxDefault.setHelperText("Default behaviour");
        comboBoxDefault.setItems(dataProvider);
        comboBoxDefault.setItemLabelGenerator(Person::toString);
        return comboBoxDefault;
    }

    private AutoSelectComboBox<Person> comboBoxMultipleItems(DataProvider<Person, String> dataProvider) {
        AutoSelectComboBox<Person> asComboBoxMultiItems = new AutoSelectComboBox<>("Autoselect with 1 item");
        asComboBoxMultiItems.setHelperText("Custom Web Component. Auto select if 1 option. Allow custom values + run validation against options.");
        asComboBoxMultiItems.setItems(dataProvider);
        asComboBoxMultiItems.setItemLabelGenerator(Person::toString);
        asComboBoxMultiItems.setClearButtonVisible(true);
        asComboBoxMultiItems.addValueChangeListener(e -> {
            System.out.println("asComboBox value change to " + e.getValue());
        });

        return asComboBoxMultiItems;
    }

    private AutoSelectComboBox<Person> comboBoxTwoItems() {
        AutoSelectComboBox<Person> asComboBoxTwoItems = new AutoSelectComboBox<>("AutoSelect with 2 items");
        asComboBoxTwoItems.setHelperText("Custom Web Component. Auto select if 1 option. Allow custom values + run validation against options.");
        asComboBoxTwoItems.setItems(new Person(1, "Aaron", "Allen", 22,
                null, "123"), new Person(2, "Benjamin", "Brick  ", 32,
                null, "1223"));
        asComboBoxTwoItems.setClearButtonVisible(true);
        asComboBoxTwoItems.setItemLabelGenerator(Person::toString);
        asComboBoxTwoItems.setAllowCustomValue(true);
        return asComboBoxTwoItems;
    }

    private AutoSelectComboBox<String> comboBoxReadOnly() {
        AutoSelectComboBox<String> asComboBoxReadOnly = new AutoSelectComboBox<>("Read-only");
        asComboBoxReadOnly.setItems("Foo", "Bar", "Baz2");
        asComboBoxReadOnly.setValue("Bar");
        asComboBoxReadOnly.setClearButtonVisible(true);
        asComboBoxReadOnly.setReadOnly(true);
        asComboBoxReadOnly.addValueChangeListener(e -> Notification.show("New value: " + e.getValue()));
        return asComboBoxReadOnly;
    }

    private AutoSelectComboBox<String> comboBoxWithAutoFocus() {
        AutoSelectComboBox<String> comboBox = new AutoSelectComboBox<>("Autoselect with 1 item, autofocus");
        comboBox.setAutofocus(true);
        comboBox.setItems("Foo");
        comboBox.setValue("Foo");
        return comboBox;
    }

    private AutoSelectComboBox<String> comboBoxWithBinderValidation() {
        AutoSelectComboBox<String> bindingCombo = new AutoSelectComboBox<>("Binding with validation");
        bindingCombo.setItems("Bar", "Foo");
        bindingCombo.setValue("Bar");
        Binder<Holder> binder = new Binder<>();
        binder.forField(bindingCombo).asRequired().withConverter(new Converter<String, Choice>() {
            @Override
            public Result<Choice> convertToModel(String s, ValueContext valueContext) {
                Choice choice1 = new Choice(s);
                return Result.ok(choice1);
            }

            @Override
            public String convertToPresentation(Choice choice, ValueContext valueContext) {
                return choice.getChosenValue();
            }
        }).withValidator(new Validator<Choice>() {
            @Override
            public ValidationResult apply(Choice choice, ValueContext valueContext) {
                if ("Foo".equals(choice.getChosenValue())) {
                    return ValidationResult.error("No foo allowed");
                }
                return ValidationResult.ok();
            }
        }).bind(Holder::getChoice, Holder::setChoice);

        return bindingCombo;
    }

    private class Choice {

        public Choice(String s) {
            chosenValue = s;
        }
        public String getChosenValue() {
            return chosenValue;
        }

        public void setChosenValue(String chosenValue) {
            this.chosenValue = chosenValue;
        }

        private String chosenValue = "Bar";

    }

    private class Holder {
        public Choice getChoice() {
            return choice;
        }

        public void setChoice(Choice choice) {
            this.choice = choice;
        }

        Choice choice;
    }

    private Person buildEmptyPerson() {
        Person person = new Person();
        person.setId(-1);
        person.setFirstName("");
        person.setLastName("");
        return person;
    }

}