package org.vaadin.addons.autoselectcombobox;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Auto focus - ComboBox Validation")
@Menu(order = 1)
@Route("auto-focus")
@Uses(Icon.class)
public class AutoFocusComboBoxValidationView extends AbstractDemo {

    @Override
    protected void initView() {
        addAutoselectComboValidation();
    }

    private void addAutoselectComboValidation() {
        AutoSelectComboBox<String> comboBox = new AutoSelectComboBox<>("AutoSelectComboBox");
        comboBox.setItems("Test");
        comboBox.setValue("Test");
        comboBox.setAutofocus(true);

        // end-source-example
        Anchor focusTargetForTesting = new Anchor("#", "Focus target for testing");
        addCard("Autoselect with autofocus", comboBox,
                focusTargetForTesting);
    }

}