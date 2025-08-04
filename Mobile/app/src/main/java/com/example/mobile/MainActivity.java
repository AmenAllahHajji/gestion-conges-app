package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    BaseDonnee baseDonnee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baseDonnee = new BaseDonnee(this);

        EditText editMat = findViewById(R.id.editMat);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        EditText editCin=findViewById(R.id.editCin);
        buttonLogin.setOnClickListener(v -> {
            String matricule= editMat.getText().toString().trim();
            String cin=editCin.getText().toString().trim();

            if (matricule.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir le login", Toast.LENGTH_SHORT).show();
            }
            else if(cin.isEmpty())
            {
                Toast.makeText(this,"Veuillez remplir le champs cin!!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Connexion", Toast.LENGTH_SHORT).show();
                if (matricule.equals("admin") && cin.equals("admin")) {
                    Toast.makeText(this, "Connexion réussie!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, EspaceRH.class);
                    intent.putExtra("matricule", matricule);
                    startActivity(intent);
                }
                else if (baseDonnee.existeAgent(matricule,cin)) {
                Toast.makeText(this, "Connexion réussie!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Accueil.class);
                intent.putExtra("matricule", matricule);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
            }
            }

        });
    }
}
