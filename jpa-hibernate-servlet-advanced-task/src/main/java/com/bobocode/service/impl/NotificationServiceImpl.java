package com.bobocode.service.impl;


import com.bobocode.service.NotificationService;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sentEmail(String email, String message) {
        email.chars().forEach(i -> System.out.println("-"));
        System.out.println(email);
        email.chars().forEach(i -> System.out.println("-"));
        System.out.println(message);
    }
}
