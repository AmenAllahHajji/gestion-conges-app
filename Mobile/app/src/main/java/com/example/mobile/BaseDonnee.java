package com.example.mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import android.database.DatabaseUtils;
import android.database.Cursor;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;
import java.text.ParseException;


public class BaseDonnee extends SQLiteOpenHelper {
    private static final String dbname = "BaseAgent";
    private static final int dbversion = 28;

    private static final String dbtable = "Agent";
    private static final String Demandes = "Demandes";

    private static final String nom = "nom";
    private static final String prenom = "prenom";
    private static final String email = "email";
    private static final String poste = "poste";
    private static final String telephone = "telephone";
    private static final String matricule = "matricule";
    private static final String cin= "cin";
    private static final String datePrise = "datePrise";
    private static final String dateDebutConge = "dateDebut";
    private static final String dateFinConge = "dateFin";
    private static final String motif = "motif";
    private static final String statut = "statut";
    private static final String solde = "solde";


    public BaseDonnee(@Nullable Context context) {
        super(context, dbname, null, dbversion);
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création table Agent
        String query = "CREATE TABLE " + dbtable + " (" +
                matricule + " TEXT PRIMARY KEY, " +
                cin + " TEXT, " +
                nom + " TEXT, " +
                prenom + " TEXT, " +
                email + " TEXT, " +
                poste + " TEXT, " +
                datePrise + " TEXT, " +
                solde + " FLOAT, " +
                telephone + " TEXT" +
                ");";
        db.execSQL(query);

        String queryDemandes = "CREATE TABLE " + Demandes + " (" +
                matricule + " TEXT, " +
                dateDebutConge + " TEXT, " +
                dateFinConge + " TEXT, " +
                motif + " TEXT, " +
                statut + " TEXT, " +
                "PRIMARY KEY (" + matricule + ", " + dateDebutConge + ", " + dateFinConge + ")" +
                ");";
        db.execSQL(queryDemandes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + dbtable);
        db.execSQL("DROP TABLE IF EXISTS " + Demandes);
        onCreate(db);
    }


    public void addAgent(Agent agent) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String datePriseStr = sdf.format(agent.getDatePrise());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(matricule, agent.getMatricule());
        values.put(cin,agent.getCin());
        values.put(nom, agent.getNom());
        values.put(prenom, agent.getPrenom());
        values.put(email, agent.getEmail());
        values.put(datePrise, datePriseStr);
        values.put(solde, agent.avoir_solde());
        values.put(poste, agent.getPoste());
        values.put(telephone, agent.getTelephone());

        db.insert(dbtable, null, values);

        db.close();
    }

    public List<Agent> afficherAgents() {
        List<Agent> agents = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curseur = db.rawQuery("SELECT * FROM " + dbtable, null);
        if (curseur.moveToFirst()) {
            do {
                String mat = curseur.getString(curseur.getColumnIndexOrThrow("matricule"));
                String cin=curseur.getString(curseur.getColumnIndexOrThrow("cin"));
                String nom = curseur.getString(curseur.getColumnIndexOrThrow("nom"));
                String prenom = curseur.getString(curseur.getColumnIndexOrThrow("prenom"));
                String email = curseur.getString(curseur.getColumnIndexOrThrow("email"));
                String poste = curseur.getString(curseur.getColumnIndexOrThrow("poste"));
                String deb = curseur.getString(curseur.getColumnIndexOrThrow("datePrise"));
                String tel = curseur.getString(curseur.getColumnIndexOrThrow("telephone"));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date datePrise = null;
                try {
                    datePrise = sdf.parse(deb);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Agent agent = new Agent(mat,cin, nom, prenom, email, poste, tel, datePrise);
                agents.add(agent);
            } while (curseur.moveToNext());

        }
        curseur.close();
        db.close();
        return agents;

    }

    public void addDemande(String matUser, String dateDebutUser, String dateFinUser, String motifUser, String statutUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(matricule, matUser);
        values.put(dateDebutConge, dateDebutUser);
        values.put(dateFinConge, dateFinUser);
        values.put(motif, motifUser);
        values.put(statut, statutUser);
        long res = db.insert(Demandes, null, values);
        Log.d("DEBUG_DB", "Résultat insert : " + res);
        db.close();
    }

    public List<Demande> afficher_demandes(String matUser) {
        List<Demande> demandes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("DEBUG_DB", "Recherche demandes pour matricule: " + matUser);
        Cursor cursor = db.rawQuery("SELECT * FROM " + Demandes + " WHERE " + matricule + " = ?", new String[]{matUser});
        Log.d("DEBUG_DB", "Nombre résultats : " + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                String dateDebutVal = cursor.getString(cursor.getColumnIndexOrThrow(dateDebutConge));
                String dateFinVal = cursor.getString(cursor.getColumnIndexOrThrow(dateFinConge));
                String motifVal = cursor.getString(cursor.getColumnIndexOrThrow(motif));
                String statutVal = cursor.getString(cursor.getColumnIndexOrThrow(statut));
                demandes.add(new Demande(matUser, dateDebutVal, dateFinVal, motifVal, statutVal));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return demandes;
    }

    public boolean existeAgent(String matUser, String cinUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM Agent WHERE matricule = ? AND cin = ?",
                new String[]{matUser, cinUser}
        );

        int compteur = 0;
        if (cursor.moveToFirst()) {
            compteur = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return compteur > 0;
    }


    public float getSolde(String matricule) {
        float solde = 0f;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT solde FROM " + dbtable + " WHERE matricule = ?", new String[]{matricule});
        if (cursor.moveToFirst()) {
            solde = cursor.getFloat(cursor.getColumnIndexOrThrow("solde"));
        }
        cursor.close();
        db.close();
        return solde;
    }
    public List<Demande> afficher_toute_demande() {
        List<Demande> demandes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor curseur = db.rawQuery("SELECT * FROM Demandes WHERE statut = ?", new String[]{"En attente"});

        if (curseur.moveToFirst()) {
            do {
                String mat = curseur.getString(curseur.getColumnIndexOrThrow("matricule"));
                String dateDebutStr = curseur.getString(curseur.getColumnIndexOrThrow("dateDebut"));
                String dateFinStr = curseur.getString(curseur.getColumnIndexOrThrow("dateFin"));
                String motif = curseur.getString(curseur.getColumnIndexOrThrow("motif"));
                String statut = curseur.getString(curseur.getColumnIndexOrThrow("statut"));

                Demande demande = new Demande(mat, dateDebutStr, dateFinStr, motif, statut);
                demandes.add(demande);
            } while (curseur.moveToNext());
        }

        curseur.close();
        db.close();
        return demandes;
    }
    public void mettreAJourStatut(String matricule, String dateDebut, String dateFin, String nouveauStatut) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(statut, nouveauStatut);

        String whereClause = this.matricule + "=? AND " + dateDebutConge + "=? AND " + dateFinConge + "=?";
        String[] whereArgs = {matricule, dateDebut, dateFin};

        int nbLignes = db.update(Demandes, values, whereClause, whereArgs);
        Log.d("DEBUG_DB", "UPDATE statut -> lignes affectées = " + nbLignes);

        db.close();
    }
    public void mettreAJourSolde(String matricule, String dateDebutUser, String dateFinUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor curseur = db.rawQuery(
                "SELECT dateDebut, dateFin FROM Demandes WHERE matricule = ? AND dateDebut = ? AND dateFin = ?",
                new String[]{matricule, dateDebutUser, dateFinUser}
        );

        if (curseur.moveToFirst()) {
            String dateDebutStr = curseur.getString(curseur.getColumnIndexOrThrow("dateDebut"));
            String dateFinStr = curseur.getString(curseur.getColumnIndexOrThrow("dateFin"));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            try {
                Date dateDebut = sdf.parse(dateDebutStr);
                Date dateFin = sdf.parse(dateFinStr);

                if (dateDebut != null && dateFin != null) {
                    long diffMillis = dateFin.getTime() - dateDebut.getTime();
                    float duree = (diffMillis / (1000 * 60 * 60 * 24)) + 1;

                    db.execSQL("UPDATE Agent SET solde = solde - ? WHERE matricule = ?",
                            new Object[]{duree, matricule});

                    Log.d("DEBUG_SOLDE", "Solde mis à jour pour " + matricule + " de -" + duree + " jours");
                } else {
                    Log.e("DEBUG_SOLDE", "Dates null après parse.");
                }
            } catch (Exception e) {
                Log.e("DEBUG_SOLDE", "Erreur parse date : " + e.getMessage());
            }
        } else {
            Log.d("DEBUG_SOLDE", "Aucune demande trouvée pour ce matricule et ces dates.");
        }

        curseur.close();
        db.close();
    }
    public void annulerDemande(Demande demande) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("statut", "Annulée");
        db.update("Demandes", values,
                "matricule=? AND dateDebut=? AND dateFin=? AND statut='En attente'",
                new String[]{demande.getMatricule(), demande.getDateDebut(), demande.getDateFin()}
        );
        db.close();
    }
    public Agent getAgentParMatricule(String matricule) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Agent WHERE matricule = ?", new String[]{matricule});
        if (cursor.moveToFirst()) {
            Agent agent = new Agent();

            int idxMatricule = cursor.getColumnIndex("matricule");
            if (idxMatricule != -1)
                agent.setMatricule(cursor.getString(idxMatricule));

            int idxNom = cursor.getColumnIndex("nom");
            if (idxNom != -1)
                agent.setNom(cursor.getString(idxNom));

            int idxPrenom = cursor.getColumnIndex("prenom");
            if (idxPrenom != -1)
                agent.setPrenom(cursor.getString(idxPrenom));

            int idxEmail = cursor.getColumnIndex("email");
            if (idxEmail != -1)
                agent.setEmail(cursor.getString(idxEmail));
            int idxtel=cursor.getColumnIndex("telephone");
            if (idxtel != -1)
                agent.setTelephone(cursor.getString(idxtel));
            int idxposte=cursor.getColumnIndex("poste");
            if (idxposte!= -1)
                agent.setPoste(cursor.getString(idxposte));

            cursor.close();
            return agent;
        }
        cursor.close();
        return null;
    }
    public void supprimerAgent(String matricule) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Demandes", "matricule=?", new String[]{matricule});
        int rows = db.delete("Agent", "matricule=?", new String[]{matricule});
        Log.d("DEBUG_DB", "Agent supprimé avec succés : " + rows + " ligne(s)");
        db.close();
    }
    public void modifemail(Agent u1)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE Agent SET email =? WHERE matricule = ?",
                new Object[]{u1.getEmail(), u1.getMatricule()});
        Log.d("DEBUG_SOLDE", "Mis à jour effectué avec succés");
    }
    public void modifposte(Agent u1)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE Agent SET poste =? WHERE matricule = ?",
                new Object[]{u1.getPoste(), u1.getMatricule()});
        Log.d("DEBUG_SOLDE", "Mis à jour effectué avec succés");
    }
    public void modiftel(Agent u1) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE Agent SET telephone = ? WHERE matricule = ?",
                    new Object[]{u1.getTelephone(), u1.getMatricule()});
        Log.d("DEBUG_SOLDE", "Mis à jour effectué avec succés");

    }











}