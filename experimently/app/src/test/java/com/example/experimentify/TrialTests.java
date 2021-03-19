package com.example.experimentify;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.*;

import static org.junit.Assert.*;
//Experiment newExp, FirebaseFirestore db, String ownerID

/**
 * test for experiment controller methods (addtodb, edit in db, delete from db)
 * currently not working yet because cant initialize experimentControllerObject
 */
public class TrialTests {

    /**
     * tests CountTrial
     */
    @Test
    public void testCount(){
        // make mock variables
        Trial trial = new Trial("userID", "Edmonton", "ID");
        Trial.CountTrial countTrial = trial.new CountTrial(trial, 0);
        assertEquals(countTrial.getTotalCount(), 0);
        countTrial.incrementCount();
        assertEquals(countTrial.getTotalCount(), 1);

    }

    /**
     * tests BinomialTrial
     */
    @Test
    public void testBinomial(){
        // make mock variables
        Trial trial = new Trial("userID", "Edmonton", "ID");
        Trial.BinomialTrial binomialTrial = trial.new BinomialTrial(trial, 0, 0);
        assertEquals(binomialTrial.getBinomialTrials(), 0);
        binomialTrial.incrementBinomialFail();
        assertEquals(binomialTrial.getBinomialTrials(), 1);
        assertEquals(binomialTrial.getBinomialFails(), 1);
        binomialTrial.incrementBinomialPass();
        assertEquals(binomialTrial.getBinomialTrials(), 2);
        assertEquals(binomialTrial.getBinomialPasses(), 1);


    }
}