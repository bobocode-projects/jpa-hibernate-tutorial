package com.bobocode.service.impl;

import com.bobocode.service.MarkupService;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MarkupServiceImpl implements MarkupService {
    @Override
    public String applyBirthdayTemplate(String greetings) {
        StringBuilder messageBuilder = new StringBuilder("***** ");
        messageBuilder.append(greetings);
        messageBuilder.append(" *****");

        return messageBuilder.toString();
    }

}
