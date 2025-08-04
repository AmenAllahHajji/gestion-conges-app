package com.example.mobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import java.text.SimpleDateFormat;
import java.util.Date;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class InscriptionActivity extends AppCompatActivity {

    public boolean verifLettres(String nom) {
        if (nom == null || nom.isEmpty()) return false;
        for (int i = 0; i < nom.length(); i++) {
            char c = Character.toUpperCase(nom.charAt(i));
            if ((c < 'A' || c > 'Z') && c != ' ') {
                return false;
            }
        }
        return true;
    }

    public boolean verifChiffres(String nom) {
        if (nom == null || nom.isEmpty()) return false;
        for (int i = 0; i < nom.length(); i++) {
            char c = nom.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        EditText editNom = findViewById(R.id.editNom);
        EditText editPrenom = findViewById(R.id.editPrenom);
        EditText editEmail = findViewById(R.id.editEmail);
        EditText editPoste = findViewById(R.id.editPoste);
        EditText editTelephone = findViewById(R.id.editTelephone);
        EditText editDateDebut=findViewById(R.id.editDateDebut);
        EditText editMat = findViewById(R.id.editMat);
        EditText editCin=findViewById(R.id.editCin);

        Button buttonInscrire = findViewById(R.id.buttonInscrire);
        Button buttonRetour = findViewById(R.id.buttonRetour);

        editDateDebut.setOnClickListener(v -> showDate(editDateDebut));

        buttonInscrire.setOnClickListener(v -> {
            String nom=editNom.getText().toString().trim();
            String prenom=editPrenom.getText().toString().trim();
            String email=editEmail.getText().toString().trim();
            String poste=editPoste.getText().toString().trim();
            String tel=editTelephone.getText().toString().trim();
            String mat=editMat.getText().toString().trim();
            String deb = editDateDebut.getText().toString().trim();
            String cin=editCin.getText().toString().trim();

            if (nom.isEmpty() || !verifLettres(nom)) {
                new AlertDialog.Builder(this)
                        .setTitle("Erreur")
                        .setMessage("Nom invalide")
                        .setPositiveButton("OK", null)
                        .show();
            }
            else if (prenom.isEmpty() || !verifLettres(prenom)) {
                new AlertDialog.Builder(this)
                        .setTitle("Erreur")
                        .setMessage("Prenom invalide!")
                        .setPositiveButton("OK", null)
                        .show();
            }
            else if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                new AlertDialog.Builder(this)
                        .setTitle("Erreur")
                        .setMessage("Email invalide!")
                        .setPositiveButton("OK", null)
                        .show();
            } else if (poste.isEmpty() || !verifLettres(poste)) {
                new AlertDialog.Builder(this)
                        .setTitle("Erreur")
                        .setMessage("Poste invalide!")
                        .setPositiveButton("OK", null)
                        .show();
            } else if (!verifChiffres(tel) || tel.length()!=8) {
                new AlertDialog.Builder(this)
                        .setTitle("Erreur")
                        .setMessage("Numero Telephone invalide!")
                        .setPositiveButton("OK", null)
                        .show();
            } else if (!verifChiffres(mat) || mat.length()!=8) {
                new AlertDialog.Builder(this)
                        .setTitle("Erreur")
                        .setMessage("Matricule invalide!")
                        .setPositiveButton("OK", null)
                        .show();
            }
            else if (!verifChiffres(cin) || cin.length()!=8) {
                new AlertDialog.Builder(this)
                        .setTitle("Erreur")
                        .setMessage("Cin invalide!")
                        .setPositiveButton("OK", null)
                        .show();
            }
            else
            {
                Agent u1=new Agent(mat,cin,nom,  prenom,  email,  poste,  tel,parseDate(deb));
                BaseDonnee baseDonnee = new BaseDonnee(this);
                try {
                    if(baseDonnee.existeAgent(mat,cin)) {
                        new AlertDialog.Builder(this)
                                .setTitle("Erreur")
                                .setMessage("Agent existant!")
                                .setPositiveButton("OK", null)
                                .show();
                    } else {
                        baseDonnee.addAgent(u1);
                        Toast.makeText(this, "Inscription réussie!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace(); // Pour voir l'erreur dans Logcat
                    new AlertDialog.Builder(this)
                            .setTitle("Erreur BDD")
                            .setMessage("Une erreur est survenue : " + e.getMessage())
                            .setPositiveButton("OK", null)
                            .show();
                }
                finally {
                    baseDonnee.close();
                }


            }

        });

        buttonRetour.setOnClickListener(v -> finish());
    }
    private void showDate(EditText targetEditText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(InscriptionActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    targetEditText.setText(selectedDate);
                }, year, month, day);

        datePickerDialog.show();
    }
    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}