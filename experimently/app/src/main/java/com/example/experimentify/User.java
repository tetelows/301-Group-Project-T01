package com.example.experimentify;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private ExperimentController subbedExperiments;
    private String email;
    private String name;
    private ArrayList<String> ownedExperiments;
    private ArrayList<String> participatingExperiments;
    private String uid;
    private String username;

    public User() {
        this.uid = "";
        this.email = "";
        this.name = "";
        this.username = "";
        //this.subbedExperiments = new ExperimentController();
        this.ownedExperiments = new ArrayList<String>();
        this.participatingExperiments = new ArrayList<String>();
    }

    public ExperimentController getSubbedExperiments() {
        return subbedExperiments;
    }

    public void setSubbedExperiments(ExperimentController subbedExperiments) {
        this.subbedExperiments = subbedExperiments;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getOwnedExperiments() {
        return ownedExperiments;
    }

    public void setOwnedExperiments(ArrayList<String> ownedExperiments) {
        this.ownedExperiments = ownedExperiments;
    }

    public ArrayList<String> getParticipatingExperiments() {
        return participatingExperiments;
    }

    public void setParticipatingExperiments(ArrayList<String> participatingExperiments) {
        this.participatingExperiments = participatingExperiments;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void generateUserId() {
        //pass
    }
}
