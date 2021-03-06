package org.vaadin.addons.autoselectcombobox;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("ComboBox validation")
@Route("")
public class ComboBoxValidation extends AbstractDemo {

    private PersonService personService;

    @Override
    protected void initView() {
        personService = new PersonService(300);
        addComboValidation();
    }

    private void addComboValidation() {
        DataProvider<Person, String> dataProvider = DataProvider.fromFilteringCallbacks(
                query -> personService.fetch(query.getOffset(), query.getLimit(), query.getFilter().orElse(null)).stream(),
                query -> personService.count(query.getFilter().orElse(null)));

        ComboBox<Person> comboBoxDefault = new ComboBox<>("People");
        comboBoxDefault.setHelperText("Default behaviour");
        comboBoxDefault.setDataProvider(dataProvider);
        comboBoxDefault.setItemLabelGenerator(Person::toString);

        // begin-source-example
        // source-example-heading: ComboBox with autoselect and validation
        ComboBox<Person> comboBox = new ComboBox<>("People");
        comboBox.setHelperText("Auto select if 1 option. Allow custom values + run validation against options.");
        comboBox.setItemLabelGenerator(Person::toString);
        comboBox.setDataProvider(dataProvider);

        new ComboBoxEnhancer<>(comboBox).enableAutoSelect(buildEmptyPerson(), (displayValue, emptyValue) -> {
            emptyValue.setFirstName(displayValue);
            return emptyValue;
        }, filter -> personService.fetch(0, Integer.MAX_VALUE, filter).stream());

        AutoSelectComboBox<Person> asComboBox = new AutoSelectComboBox<>("Another People");
        asComboBox.setHelperText("Custom Web Component. Auto select if 1 option. Allow custom values + run validation against options.");
        asComboBox.setDataProvider(dataProvider);
        asComboBox.setItemLabelGenerator(Person::toString);

        Binder<TestBean> binder = new Binder<>();
        binder.forField(comboBox)
                .asRequired(Validator.from(personService::exists, "Please select one of the available values"))
                .bind(TestBean::getPerson, TestBean::setPerson);

        TestBean item = new TestBean();
        binder.setBean(item);
        // end-source-example

        addCard("ComboBox with autoselect and validation", comboBoxDefault, comboBox, asComboBox, new Anchor("#", "Focus target for testing"));
    }

    private Person buildEmptyPerson() {
        Person person = new Person();
        person.setId(-1);
        person.setFirstName("");
        person.setLastName("");
        return person;
    }

}