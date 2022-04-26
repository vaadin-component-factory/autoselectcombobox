package org.vaadin.addons.demo.data;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class PersonService {

    private final PersonData personData;

    public PersonService(int numberOfPerson) {
        personData = new PersonData(numberOfPerson, Comparator.comparing(Person::getFirstName).thenComparing(Comparator.comparing(Person::getLastName)));
    }

    public List<Person> fetch(int offset, int limit) {
        int end = offset + limit;
        int size = personData.getPersons().size();
        if (size <= end) {
            end = size;
        }
        return personData.getPersons().subList(offset, end);
    }

    public int count() {
        return personData.getPersons().size();
    }

    public List<Person> fetch(int offset, int limit, String filter) {
        if (StringUtils.isBlank(filter)) {
            return fetch(offset, limit);
        }
        String search = filter.toLowerCase();
        int end = offset + limit;
        List<Person> persons = personData.getPersons().stream()
                .filter(this.filter(search))
                .collect(Collectors.toList());
        int size = persons.size();
        if (size <= end) {
            end = size;
        }
        return persons.subList(offset, end);
    }

    public int count(String filter) {
        if (StringUtils.isBlank(filter)) {
            return count();
        }
        String search = filter.toLowerCase();
        return (int) personData.getPersons().stream()
                .filter(this.filter(search))
                .count();
    }

    private Predicate<? super Person> filter(String filter) {
        return p -> p.getFirstName().toLowerCase().contains(filter)
                || p.getLastName().toLowerCase().contains(filter)
                || p.toString().toLowerCase().contains(filter);
    }

    public List<Person> fetchAll() {
        return personData.getPersons();
    }

    public void addPerson() {
        personData.addPerson(personData.generatePerson());
    }

    public void removePerson() {
        if (!personData.getPersons().isEmpty()) {
            personData.removePerson(personData.getPersons().get(personData.getPersons().size() - 1));
        }
    }

    public boolean exists(Person person) {
        return person != null && personData.exists(person);
    }

}
