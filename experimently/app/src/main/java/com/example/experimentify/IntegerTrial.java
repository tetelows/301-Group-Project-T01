package com.example.experimentify;

public class IntegerTrial extends Trial {
    private int integerCount;
    private int intEntered;

    public IntegerTrial(String userId, String eID, String trialLocation) {
        super(userId, eID, trialLocation);
    }

    public void addIntCount() {
        integerCount = 1;
    }

    public int getIntEntered() {
        return intEntered;
    }

    public void setIntEntered(int intEntered) {
        this.intEntered = intEntered;
    }
}