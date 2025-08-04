# 📱 Gestion Congés App

**Gestion Congés App** est une application mobile Android simple, intuitive et accessible, développée dans le cadre de mon stage au **Centre National de l’Informatique**. Elle permet la gestion efficace des demandes de congé du personnel, tout en centralisant les informations liées aux agents.

---

## 🎯 Objectifs

- Digitaliser le processus de demande et de validation des congés
- Réduire les délais de traitement grâce à une gestion optimisée
- Offrir une interface simple et fluide, accessible à tous les employés

---

## 🧩 Fonctionnalités

### 👤 Gestion des utilisateurs
- Ajouter, modifier ou supprimer un agent
- Champs gérés : Nom, Prénom, Matricule, CIN, Téléphone, Email, Poste Occupé

### 🗓️ Gestion des congés
- Création et envoi de demandes de congé
- Consultation de l’état des demandes : *En attente*, *Acceptée*, *Refusée*
- Annulation des demandes avant validation
- Historique complet des demandes
- Calcul automatique du **solde de congé** :  
  > `30 jours/an` → `0,5 jour tous les 6 jours travaillés`
- Type de congé pris en charge : **congé annuel cumulable**

### 👥 Utilisateurs de l'application

#### Agent
- Envoie des demandes de congé
- Consulte son solde et son profil
- Annule une demande si nécessaire

#### Administrateur RH
- Gère les comptes utilisateurs (ajout/suppression)
- Accède à toutes les demandes de congé
- Valide ou refuse les demandes

---

## 🛠️ Technologies utilisées

- **Android Studio**
- **Langage : Java**
- **Base de données locale : SQLite**
- XML pour les interfaces utilisateur

---

## 🔨 Étapes de réalisation

Le projet s’est structuré autour des phases classiques du développement logiciel :

1. Analyse fonctionnelle
2. Conception (modèles, interfaces, base de données)
3. Réalisation (développement et tests)

---

## 📸 Captures d’écran

> *(Ajoute ici quelques captures de l'application si possible)*

---

## 🚀 Lancement du projet

1. Cloner le dépôt :
   ```bash
   git clone https://github.com/AmenAllahHajji/gestion-conges-app.git
