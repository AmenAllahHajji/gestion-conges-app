package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;

public class Solde extends AppCompatActivity {
    BaseDonnee baseDonnee;
    TextView soldeTextView;
    String matricule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solde);
        matricule = getIntent().getStringExtra("matricule");
        Toast.makeText(this, "Solde Activity ouverte", Toast.LENGTH_LONG).show();
        Button retour=findViewById(R.id.buttonRetour);
        retour.setOnClickListener(v->
                {
                    Intent intent = new Intent(this, Accueil.class);
                    intent.putExtra("matricule", matricule);
                    startActivity(intent);
                    finish();
                }

        );

        baseDonnee = new BaseDonnee(this);
        soldeTextView = findViewById(R.id.soldeTextView);
        String matricule = getIntent().getStringExtra("matricule");
        float soldeuser = baseDonnee.getSolde(matricule);
        soldeTextView.setText(soldeuser + " jours");
    }
}
