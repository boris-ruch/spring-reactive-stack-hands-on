package com.boo.domain;

import lombok.*;

@Value
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class User {

    public static final User SKYLER = User.builder().username("swhite").firstname("Skyler").lastname("White").build();
    public static final User JESSE = User.builder().username("jpinkman").firstname("Jesse").lastname("Pinkman").build();
    public static final User WALTER = User.builder().username("wwhite").firstname("Walter").lastname("White").build();
    public static final User SAUL = User.builder().username("sgoodman").firstname("Saul").lastname("Goodman").build();

    String username;
    String firstname;
    String lastname;
}
