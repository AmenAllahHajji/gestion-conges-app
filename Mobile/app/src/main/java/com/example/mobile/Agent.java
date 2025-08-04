package com.example.mobile;
import java.util.Calendar;
import androidx.annotation.NonNull;
import java.util.Date;

public class Agent {
    private String nom;
    private String prenom;
    private String email;
    private String poste;
    private String telephone;
    private String matricule;
    private Date date_prise;
    private float solde;
    private String cin;

    public Agent (String matricule,String cin,String nom, String prenom, String email, String poste, String telephone,Date date_prise) {
        this.matricule = matricule;
        this.cin=cin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.poste = poste;
        this.telephone = telephone;
        this.date_prise=date_prise;

    }
    public Agent(){};
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public String getCin()
    {
        return cin;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getTelephone() {
        return telephone;
    }
    public Date getDatePrise() {
        return date_prise;
    }


    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public void setMatricule(String mat) {
        this.matricule = mat;
    }

    public String getMatricule() {
        return matricule;
    }

    @Override
    @NonNull
    public String toString(){
        return ("matricule: "+this.matricule+"cin: "+this.cin+" nom: "+this.nom+" prenom: "+this.prenom+" email: "+this.email+" tel: "+this.telephone+" poste occupé: "+this.poste);
    }
    public float avoir_solde() {
        Calendar now = Calendar.getInstance();
        int anneeActuelle = now.get(Calendar.YEAR);
        int moisActuel = now.get(Calendar.MONTH); // 0 = janvier
        int jourActuel = now.get(Calendar.DAY_OF_MONTH);
        Calendar debut = Calendar.getInstance();
        debut.setTime(this.date_prise);
        int anneeDebut = debut.get(Calendar.YEAR);
        int moisDebut = debut.get(Calendar.MONTH);

        if (anneeDebut == anneeActuelle) {
            return (moisActuel - moisDebut) * 2.5f+(jourActuel / 6) * 0.5f;
        } else {
            return ((12 - moisDebut) * 2.5f) +(anneeActuelle - anneeDebut - 1) * 30f+ (moisActuel* 2.5f)+(jourActuel / 6) * 0.5f;
        }
    }

    public float getSolde()
    {
        return solde;
    }
    public void  setSolde()
    {
        this.solde=avoir_solde();
    }
}
