package com.example.mobile;

import androidx.annotation.NonNull;

public class Demande {
    private String matricule;
    private String dateDebut;
    private String dateFin;
    private String motif;
    private String statut;

    public Demande(String matricule, String dateDebut, String dateFin, String motif, String statut) {
        this.matricule = matricule;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.motif = motif;
        this.statut = statut;
    }

    // Getters
    public String getMatricule() {
        return matricule;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public String getMotif() {
        return motif;
    }

    public String getStatut() {
        return statut;
    }

    // Setters
    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    @NonNull
    public String toString() {
        return "Demande{" +
                "matricule='" + matricule + '\'' +
                ", dateDebut='" + dateDebut + '\'' +
                ", dateFin='" + dateFin + '\'' +
                ", motif='" + motif + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }
}
