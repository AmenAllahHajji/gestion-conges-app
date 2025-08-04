package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.graphics.Color;
import android.view.Gravity;



import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;
import java.util.List;
public class EspaceRH extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_activity);

        TextView accueil = findViewById(R.id.accueil);
        TextView ajout = findViewById(R.id.ajout);
        TextView demande = findViewById(R.id.mesDemandes);
        TextView decon = findViewById(R.id.deconnexion);
        TextView supp=findViewById(R.id.btn_supprimer);

        demande.setOnClickListener(v ->
                startActivity(new Intent(EspaceRH.this, Validation_conge.class))
        );
        accueil.setOnClickListener(v ->
                startActivity(new Intent(this, EspaceRH.class))
        );
        ajout.setOnClickListener(v ->
                startActivity(new Intent(this, InscriptionActivity.class))
        );
        supp.setOnClickListener(v->
                startActivity(new Intent(this, SupprimerAgent.class)));
        decon.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class))
        );

        LinearLayout containerAgents = findViewById(R.id.containerAgents);

        BaseDonnee base = new BaseDonnee(this);
        List<Agent> agents = base.afficherAgents();
        base.close();

        for (Agent agent : agents) {
            LinearLayout ligne = new LinearLayout(this);
            ligne.setOrientation(LinearLayout.HORIZONTAL);
            ligne.setPadding(8, 8, 8, 8);
            ligne.setBackgroundResource(R.drawable.row_border); // Ajoute bordure

            for (String value : new String[]{
                    agent.getMatricule(),
                    agent.getNom(),
                    agent.getPrenom(),
                    agent.getPoste(),
                    agent.getDatePrise().toString() }) {

                TextView tv = new TextView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                tv.setLayoutParams(params);
                tv.setText(value);
                tv.setTextSize(14);
                tv.setTextColor(Color.BLACK);
                tv.setGravity(Gravity.CENTER);
                ligne.addView(tv);
            }


            containerAgents.addView(ligne);
        }
    }


}
