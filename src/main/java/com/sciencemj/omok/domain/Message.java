package com.sciencemj.omok.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private MessageType type;
    private String msg;
    private int sender;
    private Long id;
}
