package com.cybersix.markme.adapter;

import io.searchbox.annotations.JestId;

public class TransferDataAdapter {
    private String shortcode;
    private String username;

    public TransferDataAdapter(String shortcode, String username) {
        this.shortcode = shortcode;
        this.username = username;
    }

    public String getShortcode() {
        return this.shortcode;
    }

    public String getUsername() {
        return this.username;
    }
}
