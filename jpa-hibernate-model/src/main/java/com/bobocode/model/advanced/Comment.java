package com.bobocode.model.advanced;


import com.bobocode.model.basic.User;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Comment {
    private User author;
    private LocalDateTime createdOn;
    private String message;
}
