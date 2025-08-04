package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;

public class SupprimerAgent extends AppCompatActivity {
    BaseDonnee baseDonnee;
    TextView soldeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supprimeragent);
        Button supp=findViewById(R.id.buttonSupprimer);
        Button retour=findViewById(R.id.buttonRetour);
        retour.setOnClickListener(v->startActivity(new Intent(this, EspaceRH.class)));
        EditText mat=findViewById(R.id.Mat);
        supp.setOnClickListener(v->
                {
                    baseDonnee = new BaseDonnee(this);
                    String matricule = mat.getText().toString().trim();
                    baseDonnee.supprimerAgent(matricule);
                    Toast.makeText(this, "Agent supprimé avec succès", Toast.LENGTH_LONG).show();
                    mat.setText("");
                }
                );
    }
}
