package com.bobocode.service.impl;

import com.bobocode.dao.UserDao;
import com.bobocode.model.User;
import com.bobocode.service.GreetingsService;
import com.bobocode.service.MarkupService;
import com.bobocode.service.NotificationService;
import com.bobocode.service.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private NotificationService notificationService;
    private MarkupService markupService;
    private GreetingsService greetingsService;

    @Override
    public void happyBirthday() {
        List<User> users = userDao.findByBirthday(LocalDate.now());
        users.forEach(this::happyBirthday);
    }

    private void happyBirthday(User user) {
        String greetingMessage = greetingsService.createHappyBirthdayGreeting(user.getFirstName());
        String greeting = markupService.applyBirthdayTemplate(greetingMessage);
        notificationService.sentEmail(user.getCredentials().getEmail(), greeting);
    }
}
