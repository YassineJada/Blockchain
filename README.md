# Blockchain Voting System - SDGLR Voting App

## Description
Ce projet est une application de vote basée sur une blockchain privée utilisant **Ganache** comme simulateur Ethereum. Il permet de :
- Déployer un contrat intelligent sur Ethereum.
- Effectuer des votes sécurisés via une interface graphique.
- Consulter les résultats des votes en temps réel grâce à une application dédiée.

L'objectif principal est de garantir la transparence et l'intégrité des votes à l'aide de la technologie blockchain.

---

## Fonctionnalités
1. **Déploiement du contrat intelligent** :
   - Déployé sur un réseau Ethereum local simulé avec Ganache.
   - Les détails du déploiement sont gérés par l'application `ContractDeployment`.

2. **Vote sécurisé** :
   - Chaque utilisateur peut soumettre un vote en utilisant la clé privée et l'adresse du contrat.
   - Les votes sont irréversibles et enregistrés de manière immuable sur la blockchain.

3. **Consultation des résultats** :
   - L'application `AdminApp` permet d’afficher les résultats agrégés pour chaque option de vote.

---

## Prérequis
- **Ganache** : pour simuler un réseau Ethereum local.
- **Java 8+** : pour exécuter les applications.
- **Web3j** : bibliothèque Java pour interagir avec Ethereum.
- **Solidity** : langage utilisé pour coder le contrat intelligent (optionnel si le contrat est déjà déployé).

---

## Structure du Projet

### 1. **ContractDeployment**
- **Fichier** : `ContractDeployment.java`  
- **Description** : Responsable du déploiement du contrat intelligent sur le réseau Ethereum local simulé.  
  - Initialise une instance Web3j pour interagir avec Ganache.
  - Déploie le contrat et retourne l’adresse déployée.

### 2. **VoteApp**
- **Fichier** : `VoteApp.java`  
- **Description** : Interface utilisateur pour voter.
  - Entrer la clé privée et l’adresse du contrat.
  - Choisir une option parmi les candidats disponibles.
  - Soumettre le vote sur la blockchain.

### 3. **AdminApp**
- **Fichier** : `AdminApp.java`  
- **Description** : Interface utilisateur pour visualiser les résultats.
  - Connexion à la blockchain via Web3j.
  - Extraction des scores pour chaque option de vote.
  - Affichage des résultats dans une zone de texte.

---

## Instructions

### 1. Configurer Ganache
- Télécharger et installer Ganache.
- Démarrer un serveur RPC local (par exemple, `HTTP://127.0.0.1:7545`).
- Copier l'une des clés privées générées pour les utiliser avec les applications.

### 2. Déployer le Contrat
- Compiler et exécuter `ContractDeployment.java`.
- Notez l'adresse retournée par le programme, qui représente l'adresse du contrat.

### 3. Lancer le Vote
- Lancer `VoteApp.java`.
- Entrer votre clé privée et l’adresse du contrat.
- Sélectionner une option et soumettre votre vote.

### 4. Consulter les Résultats
- Lancer `AdminApp.java`.
- Cliquez sur **Show Result** pour voir les scores de chaque option.

---

## Technologies Utilisées
- **Java** (Swing pour l'interface graphique)
- **Web3j** (Interaction avec Ethereum)
- **Ganache** (Simulateur Ethereum)
- **Solidity** (Développement du contrat intelligent)

---

## Aperçu
### Exemple de vote :
- Options disponibles : `Abderrahmane`, `Zakaria`, `Amine`, `Yassine`, `Hamza`, `Oussama`.
- Interface simple avec des champs pour la clé privée, l’adresse du contrat et les choix de vote.

### Résultats attendus :
- Chaque option affiche le nombre de votes reçus.

---

## Sécurité
- Chaque vote est protégé par une clé privée unique.
- Une fois soumis, un vote ne peut être modifié.
- Les données sont immuables et transparentes sur la blockchain.


Pour toute contribution ou amélioration, n'hésitez pas à soumettre une demande de modification (pull request).
