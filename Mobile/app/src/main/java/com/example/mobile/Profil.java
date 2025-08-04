package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

public class Profil extends AppCompatActivity {

    BaseDonnee baseDonnee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);

        baseDonnee = new BaseDonnee(this);
        String matricule = getIntent().getStringExtra("matricule");
        if (matricule == null) {
            Log.e("Profil", "Matricule est null !");
        } else {
            Log.d("Profil", "Matricule reçu : " + matricule);
        }
        Button retour=findViewById(R.id.buttonRetour);
        retour.setOnClickListener(v->
        {
            Intent intent = new Intent(this, Accueil.class);
            intent.putExtra("matricule", matricule);
            startActivity(intent);
            finish();
        });

        TextView nom = findViewById(R.id.tvNom);
        TextView prenom = findViewById(R.id.tvPrenom);
        TextView email = findViewById(R.id.tvEmail);
        TextView mat=findViewById(R.id.tvMatricule);
        TextView poste=findViewById(R.id.tvPoste);
        TextView tel=findViewById(R.id.tvTel);
        Button btnModifier = findViewById(R.id.btnModifierProfil);
        btnModifier.setOnClickListener(v->
        {
            Intent intent = new Intent(Profil.this, NouvProfil.class);
            intent.putExtra("matricule", matricule);
            startActivity(intent);
        });
        Agent agent = baseDonnee.getAgentParMatricule(matricule);
        if (agent == null) {
            Log.e("Profil", "Agent introuvable pour matricule: " + matricule);
        }
        else{
            mat.setText(matricule);
            nom.setText(agent.getNom());
            prenom.setText(agent.getPrenom());
            email.setText(agent.getEmail());
            poste.setText(agent.getPoste());
            tel.setText(agent.getTelephone());

        }

    }
}
