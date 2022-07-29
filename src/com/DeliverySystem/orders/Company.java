package com.DeliverySystem.orders;

public final class Company {

    private static String companyName = "Speedy Delivery";
    private static int yearFounded = 2003;

    private Company() {}

    public static String getCompanyName() { return companyName; }
    public static int getYearFounded() { return yearFounded; }

    public static void companyDescription() {
        System.out.println(companyName + ", founded in " + yearFounded + " is commited to providing a quality and speedy deliver service" +
                            "accross the nation!");      
    }
}
