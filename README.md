# 


## Development instructions

Build the project and install the add-on locally:
```
mvn clean install -DskipTests
```

```
mvn spring-boot:run
```

This deploys demo at http://localhost:8080

## Description 

The AutoSelect Combobox component extends from Combobox

If there is only one match while typing a custom value in combobox, it selects the matched value in blur when either the user hit ENTER or leave focus from the field (clicking any other area outside of combobox).

The default behaviour of combobox is that when leaving the focus, it clears the value however with AutoSelectComboBox the value stay as it is and it can show a custom error massage if there is no exactly one match with the input.

## How to use it

Create a new component AutoSelectComboBox and use it like a Combobox.


## Missing features or bugs

You can report any issue or missing feature on github: https://github.com/vaadin-component-factory/autoselect-combobox


## Testing 

```
mvn -Pit verify
```

If the tests are running in development mode, you might have a timeout because the bundle is building.
You can run the application before (to build the dev bundle) or use the profile it to run it in production mode.


The tests are written in Playwright and they are covering mostly 