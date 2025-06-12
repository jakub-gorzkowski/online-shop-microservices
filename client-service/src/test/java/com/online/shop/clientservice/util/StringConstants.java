package com.online.shop.clientservice.util;

public class StringConstants {

    /**
     * API URLs
     */
    public final static String API_URL_V1 = "/api/v1/clients/";
    public final static String API_URL_V1_NO_SLASH = "/api/v1/clients";

    /**
     *  Test Ids
     */
    public final static String NON_EXISTENT_ID = "00000000-0000-0000-0000-000000000000";
    public final static String INVALID_TYPE_ID = "Q";

    /**
     * JSON Payloads
     */
    public static final String POST_JSON =
            """
                    {
                        "name": "John",
                        "surname": "Smith",
                        "email": "john.smith@email.com",
                        "address": "789 Oak Avenue Suite 101 Metropolis, NY 10001 USA"
                    }
            """;
    public static final String POST_JSON_WITH_TAKEN_EMAIL =
            """
                    {
                        "name": "John",
                        "surname": "Doe",
                        "email": "john.doe@email.com",
                        "address": "1234 Elm Street Apt. 567 Springfield, IL 62701 USA"
                    }
            """;
    public static final String INVALID_POST_JSON =
            """
                    {
                        "surname": "Doe",
                        "email": "john.doe@email.com",
                        "address": "1234 Elm Street Apt. 567 Springfield, IL 62701 USA"
                    }
            """;

    public static final String FULL_UPDATE_JSON =
            """
                    {
                        "name": "Dan",
                        "surname": "Carter",
                        "email": "dan.carter@email.com",
                        "address": "555 Pine Lane Unit 3B Pleasant-ville, CA 90210 USA"
                    }
            """;
    public static final String INVALID_FULL_UPDATE_JSON =
            """
                    {
                        "surname": "Carter",
                        "email": "dan.carter@email.com",
                        "address": "555 Pine Lane Unit 3B Pleasant-ville, CA 90210 USA"
                    }
            """;
    public static final String FULL_UPDATE_JSON_WITH_TAKEN_EMAIL =
            """
                    {
                        "name": "Dan",
                        "surname": "Carter",
                        "email": "john.doe@email.com",
                        "address": "555 Pine Lane Unit 3B Pleasant-ville, CA 90210 USA"
                    }
            """;

    public static final String PARTIAL_UPDATE_JSON =
            """
                    {
                        "name": "Andrew",
                        "email": "andrew.doe@email.com"
                    }
            """;
    public static final String PARTIAL_UPDATE_JSON_WITH_TAKEN_EMAIL =
            """
                    {
                        "email": "john.doe@email.com"
                    }
            """;
}
