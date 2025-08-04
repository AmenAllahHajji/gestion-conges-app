package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class Validation_conge extends AppCompatActivity {
    BaseDonnee baseDonnee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demande_conge_validation);
        Button retour=findViewById(R.id.buttonRetour);
        retour.setOnClickListener(v->startActivity(new Intent(this, EspaceRH.class)));

        baseDonnee = new BaseDonnee(this);
        afficherDemandes();
    }
    private void afficherDemandes() {
        TableLayout table = findViewById(R.id.table);
        if (table == null) return;

        int lignes = table.getChildCount();
        if (lignes > 1) {
            table.removeViews(1, lignes - 1);
        }

        List<Demande> demandes = baseDonnee.afficher_toute_demande();

        for (Demande d : demandes) {
            TableRow ligne = (TableRow) getLayoutInflater().inflate(R.layout.demande_ligne_validation, table, false);

            ((TextView) ligne.findViewById(R.id.mat)).setText(d.getMatricule());
            ((TextView) ligne.findViewById(R.id.deb)).setText(d.getDateDebut());
            ((TextView) ligne.findViewById(R.id.fin)).setText(d.getDateFin());
            ((TextView) ligne.findViewById(R.id.statut)).setText(d.getStatut());

            ligne.findViewById(R.id.btn_valider).setOnClickListener(v -> {
                baseDonnee.mettreAJourStatut(d.getMatricule(), d.getDateDebut(), d.getDateFin(), "Validée");
                baseDonnee.mettreAJourSolde(d.getMatricule(), d.getDateDebut(), d.getDateFin());
                Toast.makeText(this, "Demande Validée", Toast.LENGTH_SHORT).show();
                table.removeView(ligne);


            });


            ligne.findViewById(R.id.btn_refuser).setOnClickListener(v -> {
                baseDonnee.mettreAJourStatut(d.getMatricule(), d.getDateDebut(), d.getDateFin(), "Refusée");
                Toast.makeText(this, "Demande refusée", Toast.LENGTH_SHORT).show();
                table.removeView(ligne);
            });

            table.addView(ligne);
        }

    }


}
