package com.example.experimentify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class models a list of experiments. It also holds an ArrayAdapter for displaying
 * experiments in a ListView.
 */
public class ExperimentController{
    private ArrayList<Experiment> experiments;
    private ArrayAdapter<Experiment> listAdapter;

    public ExperimentController(Context context) {
        experiments = new ArrayList<Experiment>();
        listAdapter = new ExperimentListAdapter(context, experiments);
    }

    public ArrayAdapter<Experiment> getAdapter() {
        return listAdapter;
    }

    public int getSize() {
        return experiments.size();
    }

    public ArrayList<Experiment> getExperiments() {
        return experiments;
    }

    /**
     * This method adds an experiment to the database.
     * @param newExp experiment to bed added
     * @param db the database the experiment will be saved to
     */
    public void addExperimentToDB(Experiment newExp, FirebaseFirestore db, String ownerID){
        Map<String, Object> enterData = new HashMap<>();

        List<String> searchable = new ArrayList<String>();
        //https://stackoverflow.com/a/36456911 add citation for below
        searchable.addAll(Arrays.asList(newExp.getDescription().toLowerCase().split("\\W+")));

        DocumentReference newRef = db.collection("Experiments").document();

        CollectionReference experiments = db.collection("Experiment");
        enterData.put("description", newExp.getDescription());
        enterData.put("isEnded", newExp.isEnded());
        enterData.put("minTrials", newExp.getMinTrials());
        enterData.put("ownerID", ownerID);
        enterData.put("region", newExp.getRegion());
        enterData.put("editable", true);
        enterData.put("searchable", searchable);
        enterData.put("locationRequired", newExp.isLocationRequired());
        enterData.put("viewable", newExp.isViewable());
        enterData.put("date", newExp.getDate());
        newRef.set(enterData);

        String experimentID = newRef.getId().toString();

        newExp.setUID(experimentID);  // every experiment should always have a uid which is the uid generated by firestore

        DocumentReference addID = db.collection("Experiments").document(experimentID);

        addID.update("uid", experimentID);
        addID.update("searchable", FieldValue.arrayUnion(experimentID));

    }
    /**
     * This method deletes an experiment from the database.
     * @param exp experiment to be deleted
     * @param db database the experiment will be deleted from
     */
    public void deleteExperimentFromDB(Experiment exp, FirebaseFirestore db){

        String uid = exp.getUID();

        db.collection("Experiments").document(uid)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("test tag", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("test tag:", "Error deleting document", e);
                    }
                });

    }

/**
 * This accepts an experiment with changed attributes but has a UID of an experiment that already exists in the DB
 *
 * */
    public void editExperimentToDB(Experiment newExp, FirebaseFirestore db){
        Map<String, Object> enterData = new HashMap<>();

        List<String> searchable = new ArrayList<String>();
        //https://stackoverflow.com/a/36456911 add citation for below
        searchable.addAll(Arrays.asList(newExp.getDescription().toLowerCase().split("\\W+")));

        DocumentReference newRef = db.collection("Experiments").document(newExp.getUID());

        CollectionReference experiments = db.collection("Experiment");
        enterData.put("description", newExp.getDescription());
        enterData.put("isEnded", newExp.isEnded());
        enterData.put("minTrials", newExp.getMinTrials());
        enterData.put("region", newExp.getRegion());
        enterData.put("editable", newExp.isEditable());
        enterData.put("searchable", searchable);
        enterData.put("locationRequired", newExp.isLocationRequired());
        enterData.put("viewable", newExp.isViewable());
        enterData.put("date", newExp.getDate());
        enterData.put("uid", newExp.getUID());  // an edited experiment should still have the SAME uid

        newRef.set(enterData, SetOptions.merge());
    }
    /**
     * sets the experiments variablee
     */
    public void setExperiments(ArrayList<Experiment>set_experiments){
        experiments = set_experiments;
    }

    /**
     * This method brings the user to the trial screen for the experiment they clicked on.
     * @param exp experiment to be viewed
     */
    public void viewExperiment(Activity activity, Experiment exp) {
        Intent intent = new Intent(activity, ExperimentActivity.class);
        intent.putExtra("clickedExp", exp);
        activity.startActivity(intent);
    }

    public void getQrScan(Activity activity) {
        //Intent intent = new Intent(activity, qrScanActivity.class);
        //activity.startActivityForResult(intent,1);
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.initiateScan();
    }

    /**
     * This method returns an experiment based on its position in the ListView.
     * @param pos position of experiment in ListView
     * @return experiment that was clicked on
     */
    public Experiment getClickedExperiment(int pos) {
        return experiments.get(pos);
    }
}


