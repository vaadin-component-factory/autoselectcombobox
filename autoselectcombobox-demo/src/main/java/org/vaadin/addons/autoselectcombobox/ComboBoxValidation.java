package org.vaadin.addons.autoselectcombobox;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
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
        personService = new PersonService(1);
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
        asComboBox.setClearButtonVisible(true);
        asComboBox.addValueChangeListener(e -> {
            System.out.println("asComboBox value change to " + e.getValue());
        });
        Grid<String> grid = new Grid<>();
        grid.getDataCommunicator();
        Binder<TestBean> binder = new Binder<>();
        binder.forField(comboBox)
                .asRequired(Validator.from(personService::exists, "Please select one of the available values"))
                .bind(TestBean::getPerson, TestBean::setPerson);

        TestBean item = new TestBean();
        binder.setBean(item);
        // end-source-example

        AutoSelectComboBox<Person> asComboBox2 = new AutoSelectComboBox<>("2 People");
        asComboBox2.setHelperText("Custom Web Component. Auto select if 1 option. Allow custom values + run validation against options.");
        asComboBox2.setItems(new Person(1, "first", "last", 22,
        null, "123"), new Person(2, "firs2t", "l2ast", 32,
                null, "1223"));
        asComboBox2.setClearButtonVisible(true);
        asComboBox2.setItemLabelGenerator(Person::toString);
        addCard("ComboBox with autoselect and validation", comboBoxDefault, comboBox, asComboBox, asComboBox2, new Anchor("#", "Focus target for testing"));
    }

    private Person buildEmptyPerson() {
        Person person = new Person();
        person.setId(-1);
        person.setFirstName("");
        person.setLastName("");
        return person;
    }

}