package com.example.mobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Accueil extends AppCompatActivity {

    EditText etDateDebut, etDateFin;
    LinearLayout contenuPrincipal;
    TableLayout table;
    BaseDonnee baseDonnee;

    String matricule;
    String motif = "Congé";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);

        baseDonnee = new BaseDonnee(this);

        etDateDebut = findViewById(R.id.etDateDebut);
        etDateFin = findViewById(R.id.etDateFin);
        contenuPrincipal = findViewById(R.id.contenuPrincipal);
        TextView demande = findViewById(R.id.mesDemandes);
        TextView accueil = findViewById(R.id.accueil);
        TextView profil = findViewById(R.id.profil);
        TextView solde = findViewById(R.id.mesCongesRestants);
        TextView deconnexion = findViewById(R.id.deconnexion);
        Button Envoyer = findViewById(R.id.buttonEnvoyer);

        matricule = getIntent().getStringExtra("matricule");
        if (matricule == null) {
            Toast.makeText(this, "Erreur : matricule manquant", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        profil.setOnClickListener(v -> {
            Intent intent = new Intent(Accueil.this, Profil.class);
            intent.putExtra("matricule", matricule);
            startActivity(intent);
        });

        accueil.setOnClickListener(v -> startActivity(new Intent(this, Accueil.class)));

        demande.setOnClickListener(v -> {
            Intent intent = new Intent(Accueil.this, AfficheDemande.class);
            intent.putExtra("matricule", matricule);
            startActivity(intent);
        });

        solde.setOnClickListener(v -> {
            Intent intent = new Intent(Accueil.this, Solde.class);
            intent.putExtra("matricule", matricule);
            startActivity(intent);
        });

        deconnexion.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));

        etDateDebut.setOnClickListener(v -> showDate(etDateDebut));
        etDateFin.setOnClickListener(v -> showDate(etDateFin));

        Envoyer.setOnClickListener(v -> envoyerDemande());
    }

    private void envoyerDemande() {
        String deb = etDateDebut.getText().toString().trim();
        String fin = etDateFin.getText().toString().trim();

        if (deb.isEmpty() || fin.isEmpty()) {
            Toast.makeText(this, "Veuillez sélectionner les deux dates", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar dateDebut = parseDate(deb);
        Calendar dateFin = parseDate(fin);
        Calendar dateSysteme = Calendar.getInstance();

        if (dateDebut == null || dateFin == null) {
            Toast.makeText(this, "Dates invalides", Toast.LENGTH_SHORT).show();
            etDateDebut.setText("");
            etDateFin.setText("");
            return;
        }

        if (dateFin.before(dateDebut)) {
            Toast.makeText(this, "La date de fin ne peut pas être avant la date de début", Toast.LENGTH_LONG).show();
            etDateDebut.setText("");
            etDateFin.setText("");
            return;
        }

        if (dateDebut.before(dateSysteme)) {
            Toast.makeText(this, "La date début ne peut pas être avant la date de système", Toast.LENGTH_LONG).show();
            etDateDebut.setText("");
            etDateFin.setText("");
            return;
        }

        long diffInMillis = dateFin.getTimeInMillis() - dateDebut.getTimeInMillis();
        int duree = (int) TimeUnit.MILLISECONDS.toDays(diffInMillis) + 1;

        float solde = baseDonnee.getSolde(matricule);
        if (solde >= duree) {
            baseDonnee.addDemande(matricule, deb, fin, motif, "En attente");
            Toast.makeText(this, "Demande enregistrée avec succès", Toast.LENGTH_LONG).show();
            etDateDebut.setText("");
            etDateFin.setText("");
        } else {
            Toast.makeText(this, "Solde insuffisant !", Toast.LENGTH_LONG).show();
        }
    }

    private Calendar parseDate(String dateStr) {
        try {
            String[] parts = dateStr.split("/");
            int jour = Integer.parseInt(parts[0]);
            int mois = Integer.parseInt(parts[1]) - 1;
            int annee = Integer.parseInt(parts[2]);

            Calendar calendar = Calendar.getInstance();
            calendar.set(annee, mois, jour, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar;
        } catch (Exception e) {
            return null;
        }
    }

    private void showDate(EditText targetEditText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Accueil.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    targetEditText.setText(selectedDate);
                }, year, month, day);

        datePickerDialog.show();
    }

    private int getLastDay(int mois) {
        switch (mois) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;
            case 1:
                return 28;
            case 3:
            case 5:
            case 8:
            case 10:
                return 30;
            default:
                return 30;
        }


    }
}
