package com.example.experimentify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParticipantsActivity extends AppCompatActivity implements UserOptionsFragment.OnFragmentInteractionListener {

    private UserListAdapter userAdapter;
    private ArrayList<User> userList;

    FirebaseFirestore db;
    public static final String PREFS_NAME = "PrefsFile";
    private SharedPreferences settings;
    private User localUser;
    private Experiment exp;
    private ListView userLV;
    private DatabaseSingleton dbSingleton;
    private LocalUserSingleton LUS;

    /**
     * This method shows the fragment that gives users options for the experiment they long clicked on.
     * @param newUser experiment whose options will be edited
     */
    private void showUserOptionsUI(User newUser, User currentUser) {
        String localUID = settings.getString("uid","0");
        UserOptionsFragment fragment = UserOptionsFragment.newInstance(newUser, localUID, currentUser, exp);
        fragment.show(getSupportFragmentManager(), "EXP_OPTIONS");
    }

    private void updateList(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

        userList.clear();
        String localUID = settings.getString("uid","0");
        for (QueryDocumentSnapshot doc : value){
            String email = (String) doc.getData().get("email");
            String name = (String) doc.getData().get("name");
            ArrayList<String> ownedExp = (ArrayList<String>) doc.getData().get("ownedExperiments");
            ArrayList<String> participatingExp = (ArrayList<String>) doc.getData().get("participatingExperiments");
            String username = (String) doc.getData().get("username");
            String userID = (String) doc.getData().get("uid");

            // Experiments are only displayed in ListView if they are viewable or current user is the owner.


            User tempUser = new User();
            tempUser.setEmail(email);
            tempUser.setName(name);
            tempUser.setUid(userID);
            tempUser.setParticipatingExperiments(participatingExp);
            tempUser.setOwnedExperiments(ownedExp);
            tempUser.setUsername(username);
            userList.add(tempUser);
        }
        /*
            //TODO check if user is participant
            newExperiment.userIsSubscribed(localUID, new Experiment.GetDataListener() {
                @Override
                public void onSuccess(boolean result) {
                    if (result) {
                        experimentList.add(newExperiment);
                        experimentController.getAdapter().notifyDataSetChanged();
                    }
                }
            });

         */
        userAdapter.notifyDataSetChanged();
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        if (extras != null) {
            exp = extras.getParcelable("experiment");
        }


        db = dbSingleton.getDB();
        localUser = LUS.getLocalUser();


        CollectionReference collectionReference = db.collection("Users");
        settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
        userList = new ArrayList<User>();



        userLV = findViewById(R.id.userListView);


        userAdapter = new UserListAdapter(this, userList);
        userLV.setAdapter(userAdapter);

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                updateList(value, error);
            }
        });

        //TODO find way to pass in currentUser from main activity to this point

        userLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long id) {
                User userToModify = userList.get(pos);
                showUserOptionsUI(userToModify, localUser);
                return true;
            }
        });




    }


    @Override
    public void onConfirmEdits(Experiment newExp) {

        /*
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
        */

    }

    @Override
    public void onDeletePressed(Experiment current) {

    }
}