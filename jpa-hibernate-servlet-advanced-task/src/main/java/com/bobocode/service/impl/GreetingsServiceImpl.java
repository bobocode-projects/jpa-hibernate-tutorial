package com.bobocode.service.impl;

import com.bobocode.service.GreetingsService;

public class GreetingsServiceImpl implements GreetingsService {
    @Override
    public String createHappyBirthdayGreeting(String firstName) {
        return "Happy Birthday, " + firstName + "!";
    }
}
