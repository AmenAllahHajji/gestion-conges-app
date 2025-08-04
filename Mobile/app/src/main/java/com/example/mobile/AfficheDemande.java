package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AfficheDemande extends AppCompatActivity {

    TableLayout table;
    BaseDonnee baseDonnee;
    String matricule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contenu_mes_demandes);

        table = findViewById(R.id.table);
        baseDonnee = new BaseDonnee(this);
        matricule = getIntent().getStringExtra("matricule");
        if (matricule == null) {
            Toast.makeText(this, "Matricule manquant", Toast.LENGTH_SHORT).show();
            finish();
        }

        afficherDemandes();
        Button retour=findViewById(R.id.buttonRetour);
        retour.setOnClickListener(v -> {
            Intent intent = new Intent(this, Accueil.class);
            intent.putExtra("matricule", matricule);
            startActivity(intent);
            finish();
        });

    }

    private void afficherDemandes() {
        table.removeViews(1, table.getChildCount() - 1);

        List<Demande> demandes = baseDonnee.afficher_demandes(matricule);
        for (Demande d : demandes) {
            TableRow ligne = (TableRow) getLayoutInflater().inflate(R.layout.demande_ligne, table, false);

            ((TextView) ligne.findViewById(R.id.deb)).setText(d.getDateDebut());
            ((TextView) ligne.findViewById(R.id.fin)).setText(d.getDateFin());
            ((TextView) ligne.findViewById(R.id.statut)).setText(d.getStatut());

            Button btnAnnuler = ligne.findViewById(R.id.btn_annuler);

            if ("En attente".equalsIgnoreCase(d.getStatut())) {
                btnAnnuler.setVisibility(View.VISIBLE);
                btnAnnuler.setOnClickListener(v -> {
                    baseDonnee.annulerDemande(d);
                    afficherDemandes();
                });
            } else {
                btnAnnuler.setVisibility(View.GONE);
            }

            table.addView(ligne);
        }
    }
}
