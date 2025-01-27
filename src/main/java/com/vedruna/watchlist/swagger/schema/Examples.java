package com.vedruna.watchlist.swagger.schema;

public class Examples {

    //DNI
    public static final String DNI_NUMBER_SAMPLE = "23456789D";
    public static final String PATH_SAMPLE = "https://picsum.photos/800/800";
    public static final String DNI_SAMPLE = "{ \"number\": \""+DNI_NUMBER_SAMPLE+"\", " +
                                            "\"frontImg\": \""+PATH_SAMPLE+"\", " + 
                                            "\"backImg\": \""+PATH_SAMPLE+"\", " +
                                            "\"dniOwnerId\": 2 }";

    //Film
    public static final String FILM_TITLE_SAMPLE = "The phantom menace";
    public static final String FILM_TITLE_SAMPLE_2 = "Attack of the clones";
    public static final String FILM_RELEASE_DATE_SAMPLE = "2000-01-01";
    public static final String FILM_RELEASE_DATE_SAMPLE_2 = "2025-01-01";
    public static final String FILM_SAMPLE = "{ \"title\": \""+FILM_TITLE_SAMPLE+"\", " +
                                                "\"releaseDate\": \"1999-05-19\" }";
    public static final String EDIT_TITLE_SAMPLE = "{ \"title\": \""+FILM_TITLE_SAMPLE_2+"\" }";
    public static final String FILM_PAGEABLE_SAMPLE = "{\r\n" + 
                                                        "  \"page\": 0,\r\n" + 
                                                        "  \"size\": 5,\r\n" + 
                                                        "  \"sort\": \"releaseDate\"\r\n" +
                                                      "}";
    

    //User
    public static final String USERNAME_SAMPLE_INC = "adm";
    public static final String USERNAME_SAMPLE = "joaquin";
    public static final String EMAIL_SAMPLE = "joaquin.borrego@vedruna.es";
    public static final String USER_ID_DTO_SAMPLE = "{ \"userId\": 1 }";
    public static final String USER_PAGEABLE_SAMPLE = "{\r\n" + 
                                                        "  \"page\": 0,\r\n" + 
                                                        "  \"size\": 3,\r\n" + 
                                                        "  \"sort\": \"username\"\r\n" +
                                                    "}";
}
