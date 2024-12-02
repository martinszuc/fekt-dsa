package org.example;

import java.util.ArrayList;
import java.util.List;

class Center {
    private final List<String> memory;

    public Center() {
        memory = new ArrayList<>();
    }

    public void add(String value) {
        memory.add(value);
    }

    public List<String> getAll() {
        return new ArrayList<>(memory);
    }

    public void printMemory() {
        System.out.println("Center memory: " + memory);
    }
}