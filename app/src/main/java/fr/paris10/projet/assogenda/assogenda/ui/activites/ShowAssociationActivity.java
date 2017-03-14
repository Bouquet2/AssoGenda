package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Association;
import fr.paris10.projet.assogenda.assogenda.model.User;

public class ShowAssociationActivity extends AppCompatActivity {
    private String associationID;
    private Association association;
    private User president;
    private TextView nameAsso;
    private TextView descAsso;
    private TextView namePrez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_association);
        nameAsso = (TextView) findViewById(R.id.activity_show_association_name_asso);
        descAsso = (TextView) findViewById(R.id.activity_show_association_description_asso);
        namePrez = (TextView) findViewById(R.id.activity_show_association_name_president);
        associationID = (String) getIntent().getExtras().get("associationID");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("association");
        reference.child(associationID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    association = dataSnapshot.getValue(Association.class);
                    nameAsso.setText(association.name);
                    descAsso.setText(association.description);
                    namePrez.setText(" ");
                    DatabaseReference references = FirebaseDatabase.getInstance().getReference("users");
                    references.child(association.president).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                president = dataSnapshot.getValue(User.class);
                                namePrez.setText(president.firstName+" "+president.lastName);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}