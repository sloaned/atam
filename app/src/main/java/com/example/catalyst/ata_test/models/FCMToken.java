package com.example.catalyst.ata_test.models;

import java.util.List;

/**
 * Created by dsloane on 6/14/2016.
 */
public class FCMToken {

    private String userId;
    private List<String> tokens;

    public FCMToken(String userId, List<String> tokens) {
        this.userId = userId;
        this.tokens = tokens;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public List<String> getTokens() { return tokens; }
    public void setTokens(List<String> tokens) { this.tokens = tokens; }
}
