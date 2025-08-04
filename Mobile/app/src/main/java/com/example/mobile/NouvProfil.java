package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class NouvProfil extends AppCompatActivity {
    BaseDonnee baseDonnee;
    String matricule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifprofil);

        EditText nouvemail = findViewById(R.id.Email);
        EditText nouvtel = findViewById(R.id.Tel);
        EditText nouvposte = findViewById(R.id.Poste);
        matricule = getIntent().getStringExtra("matricule");
        if (matricule == null || matricule.isEmpty()) {
            Log.e("Profil", "Matricule manquant depuis l'intent !");
            Toast.makeText(this, "Erreur : matricule introuvable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Button enreg = findViewById(R.id.btnModifierProfil);
        Button retour=findViewById(R.id.buttonRetour);
        retour.setOnClickListener(v->
        {
            Intent intent = new Intent(this, Profil.class);
            intent.putExtra("matricule", matricule);
            startActivity(intent);
            finish();
        });

        baseDonnee = new BaseDonnee(this);

        enreg.setOnClickListener(v -> {
            String email = nouvemail.getText().toString().trim();
            String poste = nouvposte.getText().toString().trim();
            String tel = nouvtel.getText().toString().trim();

            Agent agent = baseDonnee.getAgentParMatricule(matricule);

            if (agent == null) {
                Log.e("Profil", "Agent introuvable pour matricule: " + matricule);
                return;
            }

            if (!email.isEmpty() && !agent.getEmail().equals(email) &&
                    android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                agent.setEmail(email);
                baseDonnee.modifemail(agent);
                Toast.makeText(this, "Email modifié avec succés", Toast.LENGTH_SHORT).show();
            }
            if (!poste.isEmpty() && !agent.getPoste().equals(poste)) {
                agent.setPoste(poste);
                baseDonnee.modifposte(agent);
                Toast.makeText(this, "Poste modifié avec succés", Toast.LENGTH_SHORT).show();
            }
            if (!tel.isEmpty() && !agent.getTelephone().equals(tel)) {
                agent.setTelephone(tel);
                baseDonnee.modiftel(agent);
                Toast.makeText(this, "Tel modifié avec succés", Toast.LENGTH_SHORT).show();
            }
            nouvemail.setText("");
            nouvposte.setText("");
            nouvtel.setText("");

        });
    }

}
