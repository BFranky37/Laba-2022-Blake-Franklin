package com.DeliverySystem.other;

import com.DeliverySystem.people.Person;

import java.util.LinkedHashSet;
import java.util.LinkedList;

public final class SavedData {
    private static LinkedList<Person> profiles = new LinkedList<Person>();

    private static LinkedHashSet<String> cities = new LinkedHashSet<String>();

    public static void addProfile(Person individual) {
        if (!profiles.contains(individual))
            profiles.add(individual);
    }

    public static LinkedList<Person> getProfiles() {
        return profiles;
    }

    public static void addCity(String city) {
        cities.add(city);
    }

    public static LinkedHashSet<String> getCities() {
        return cities;
    }
}
