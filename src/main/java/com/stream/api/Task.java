package com.stream.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Task {
    private String id;
    private int duration;
    boolean needHelp;
    private int delay;

}
