package org.vaadin.addons.autoselectcombobox;

public interface HasTestView {


    String getUrl();

    default String getView() {
        return "";
    }
}