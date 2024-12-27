# README - Projet de Labyrinthe (Java)

## Description du projet

Ce projet consiste à modeliser la recherche d'un chemin de résolution dans un graphe d’états par une stratégie A*. Deux heuristiques différentes ont été utilisés : la distance de Manhattan et la distance Euclidienne. Les labyrinthes sont fournis sous forme de fichiers texte, et le programme permet d'afficher les résultats graphiquement et textuellement.

## Fonctionnalités principales
1. Lecture de labyrinthes à partir de fichiers texte fournis par l'utilisateur.
2. Résolution du labyrinthe avec l'algorithme A* en utilisant deux heuristiques :
   - Distance de Manhattan
   - Distance Euclidienne
3. Affichage graphique et textuel du chemin trouvé dans le labyrinthe.
4. Comparaison des résultats pour différentes heuristiques.

## Prérequis
- Java Development Kit (JDK) version 11 ou supérieure
- Un éditeur de code compatible Java (IntelliJ IDEA, Eclipse, ou autre)

## Instructions d'installation et d'exécution

1. **Cloner le dépôt**
   ```bash
   git clone <URL_du_dépôt>
   cd <nom_du_dépôt>
   ```

2. **Compiler les fichiers Java**
   ```bash
   javac -d bin src/*.java
   ```

3. **Exécuter le programme**
   ```bash
   java -cp bin Main
   ```

## Structure du projet

```
<racine_du_projet>/
├── MiniProjet/src/
│   ├── Main.java
│   ├── Labyrinthe.java
│   ├── Graphe.java
│   ├── Cell.java
│   └── AStar.java
├── labyrinthes/
│   ├── labMoyen.txt
│   ├── labComplexe.txt
    ├── labTresComplexe.txt (on ne trouve pas de chemin)
│   └── labyrinthe
├── bin/ (dossier pour les fichiers compilés)
└── README.md
```

## Instructions pour tester le projet

1. **Préparer les fichiers de labyrinthe**
   - Placer les fichiers de labyrinthe (par exemple, `labSimple.txt`) dans le dossier `labyrinthes`.
   - Chaque fichier doit suivre le format suivant :
     - `#` représente un mur.
     - `.` représente un chemin libre.
     - `S` représente le point de départ.
     - `E` représente le point d'arrivée.

2. **Exécution avec un fichier spécifique**
   - Modifiez le chemin d'accès au fichier dans la méthode `testLabyrintheDepuisFichier()` de la classe `Main` pour pointer vers le fichier désiré.
   - Exemple :
     ```java
     Labyrinthe labyrinthe = new Labyrinthe("labyrinthes/labSimple.txt");
     ```

3. **Résultats attendus**
   - Le programme détectera automatiquement l'entrée (`S`) et la sortie (`E`) du labyrinthe.
   - Il affichera :
     1. La grille du labyrinthe.
     2. Le graphe représentant les connexions entre les cases accessibles.
     3. Le chemin trouvé pour atteindre la sortie.
     4. Une version graphique du labyrinthe avec le chemin marqué.
