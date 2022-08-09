package com.DeliverySystem.other;

import com.DeliverySystem.people.Person;

import java.util.LinkedHashSet;

public final class SavedData {
    private static LinkedHashSet<Person> profiles = new LinkedHashSet<Person>();

    private static CustomList<String> cities = new CustomList<String>();

    public static void addProfile(Person individual) {
        if (!profiles.contains(individual))
            profiles.add(individual);
    }

    public static LinkedHashSet<Person> getProfiles() {
        return profiles;
    }

    public static void addCity(String city) {
        cities.add(city);
    }

    public static CustomList<String> getCities() {
        return cities;
    }
}
