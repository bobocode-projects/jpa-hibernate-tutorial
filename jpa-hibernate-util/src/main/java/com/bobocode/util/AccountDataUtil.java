package com.bobocode.util;


import com.bobocode.model.Account;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class AccountDataUtil {

    public static List<Account> generateFakeAccountList(int n) {
        return range(1, n)
                .mapToObj(i -> generateFakeAccount())
                .collect(toList());

    }

    public static Account generateFakeAccount() {
        Fairy fairy = Fairy.create();
        Person person = fairy.person();
        Random random = new Random();


        Account fakeAccount = new Account();
        fakeAccount.setFirstName(person.getFirstName());
        fakeAccount.setLastName(person.getLastName());
        fakeAccount.setEmail(person.getEmail());
        fakeAccount.setPassword(person.getPassword());
        fakeAccount.setBirthday(LocalDate.of(
                person.getDateOfBirth().getYear(),
                person.getDateOfBirth().getMonthOfYear(),
                person.getDateOfBirth().getDayOfMonth()));
        fakeAccount.setBalance(BigDecimal.valueOf(random.nextInt(200_000)));
        fakeAccount.setCreationDate(LocalDate.now().minusWeeks(random.nextInt(365)));

        return fakeAccount;
    }


}