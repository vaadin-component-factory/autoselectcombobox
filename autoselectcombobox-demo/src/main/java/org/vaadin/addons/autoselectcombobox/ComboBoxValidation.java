package org.vaadin.addons.autoselectcombobox;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Validator;
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

        ComboBox<Person> comboBoxDefault = new ComboBox<>("People");
        comboBoxDefault.setHelperText("Default behaviour");
        comboBoxDefault.setItems(dataProvider);
        comboBoxDefault.setItemLabelGenerator(Person::toString);

        // begin-source-example
        // source-example-heading: ComboBox with autoselect and validation
        ComboBox<Person> comboBoxWithEnhancer = new ComboBox<>("ComboBoxEnhancer");
        comboBoxWithEnhancer.setHelperText("Auto select if 1 option. Allow custom values + run validation against options.");
        comboBoxWithEnhancer.setItemLabelGenerator(Person::toString);
        comboBoxWithEnhancer.setItems(dataProvider);

        new ComboBoxEnhancer<>(comboBoxWithEnhancer).enableAutoSelect(buildEmptyPerson(), (displayValue, emptyValue) -> {
            emptyValue.setFirstName(displayValue);
            return emptyValue;
        }, filter -> personService.fetch(0, Integer.MAX_VALUE, filter).stream());

        AutoSelectComboBox<Person> asComboBoxMultiItems = new AutoSelectComboBox<>("Autoselect with 1 item");
        asComboBoxMultiItems.setHelperText("Custom Web Component. Auto select if 1 option. Allow custom values + run validation against options.");
        asComboBoxMultiItems.setItems(dataProvider);
        asComboBoxMultiItems.setItemLabelGenerator(Person::toString);
        asComboBoxMultiItems.setClearButtonVisible(true);
        asComboBoxMultiItems.addValueChangeListener(e -> {
            System.out.println("asComboBox value change to " + e.getValue());
        });
        Binder<TestBean> binder = new Binder<>();
        binder.forField(comboBoxWithEnhancer)
                .asRequired(Validator.from(personService::exists, "Please select one of the available values"))
                .bind(TestBean::getPerson, TestBean::setPerson);

        TestBean item = new TestBean();
        binder.setBean(item);


        AutoSelectComboBox<Person> asComboBoxTwoItems = new AutoSelectComboBox<>("AutoSelect with 2 items");
        asComboBoxTwoItems.setHelperText("Custom Web Component. Auto select if 1 option. Allow custom values + run validation against options.");
        asComboBoxTwoItems.setItems(new Person(1, "Aaron", "Allen", 22,
                null, "123"), new Person(2, "Benjamin", "Brick  ", 32,
                null, "1223"));
        asComboBoxTwoItems.setClearButtonVisible(true);
        asComboBoxTwoItems.setItemLabelGenerator(Person::toString);

        AutoSelectComboBox<String> asComboBoxReadOnly = new AutoSelectComboBox<>("Read-only");
        asComboBoxReadOnly.setItems("Foo", "Bar", "Baz");
        asComboBoxReadOnly.setValue("Bar");
        asComboBoxReadOnly.setClearButtonVisible(true);
        asComboBoxReadOnly.setReadOnly(true);
        asComboBoxReadOnly.addValueChangeListener(e -> Notification.show("New value: " + e.getValue()));

        Button button = new Button("Check value", e -> {
            Notification.show(asComboBoxReadOnly.getValue());
        });

        // end-source-example
        addCard("ComboBox with autoselect and validation", comboBoxDefault, comboBoxWithEnhancer, asComboBoxMultiItems, asComboBoxTwoItems, asComboBoxReadOnly, new Anchor("#", "Focus target for testing"), button);
    }

    private Person buildEmptyPerson() {
        Person person = new Person();
        person.setId(-1);
        person.setFirstName("");
        person.setLastName("");
        return person;
    }

}