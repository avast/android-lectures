package com.avast.example.android.github.model;

import java.io.Serializable;

/**
 * @author Tomáš Kypta (kypta)
 */
public class Account implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
