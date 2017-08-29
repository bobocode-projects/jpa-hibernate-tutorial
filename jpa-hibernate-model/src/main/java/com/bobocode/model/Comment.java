package com.bobocode.model;


import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Comment {
    private User author;
    private LocalDateTime createdOn;
    private String message;
}
