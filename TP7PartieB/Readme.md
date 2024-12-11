# Projet : Algorithmes de Recherche de Chemin Optimal

## Description
Ce projet implémente et compare deux algorithmes de recherche de chemin optimal :
1. **A\*** (avec heuristique admissible)
2. **Dijkstra** (exploration exhaustive)

Ces algorithmes sont appliqués à des graphes représentant des cartes sous forme de fichiers texte, avec des configurations variées incluant des zones à coûts différents.

---

## Fonctionnalités
- Chargement des cartes depuis des fichiers texte.
- Choix de l'algorithme dans le terminal au moment de l'exécution :
  - **1** pour A\*
  - **2** pour Dijkstra.
- Visualisation graphique du chemin optimal trouvé par les deux algorithmes.
- Enregistrement des résultats dans un fichier `out.txt` contenant les sommets du chemin optimal.

---

## Prérequis
- **Java 17** ou version supérieure, avec le support des fonctionnalités "preview" activé.
- Une IDE comme IntelliJ ou VSCode, ou un terminal avec la commande `javac`.
- Bibliothèque graphique incluse (utilisation des classes Swing).

---

## Installation
1. Clonez le dépôt ou téléchargez les fichiers source.
2. Assurez-vous que les fichiers texte des graphes (à savoir `graphTest1.txt`, `graphTest2.txt`, `graphTest3.txt`) sont placés à la racine de votre dossier `src`.
3. Compilez les fichiers Java :
   ```bash
   javac MainApp/*.java
   ```
4. Exécutez le programme en indiquant la classe principale :
   ```bash
   java MainApp.App
   ```

---

## Utilisation
### 1. Choix de l'algorithme
Lors de l'exécution, le programme vous demande de choisir l'algorithme à utiliser :
- Tapez **1** pour A\*.
- Tapez **2** pour Dijkstra.

Exemple :
```
Veuillez choisir un algorithme :
1. A*
2. Dijkstra
Entrez votre choix (1 ou 2) : 1
```

### 2. Changement de fichier de graphe
Le fichier de graphe utilisé par défaut est configuré à la **ligne 371** de la classe `App.java`. Pour changer de graphe, modifiez cette ligne :
```java
File myObj = new File("src/graphTest1.txt");
```
Par exemple, pour tester `src/graphTest2.txt`, remplacez cette ligne par :
```java
File myObj = new File("src/graphTest2.txt");
```
Recompilez ensuite le projet si nécessaire :
```bash
javac MainApp/*.java
```

---

## Fichiers de Test
Trois fichiers de test sont fournis avec des configurations variées :

1. **graphTest1.txt**
   - Carte simple avec des zones uniformément accessibles.
   - Types de cases :
     - G (green) : Coût = 1.

2. **graphTest2.txt**
   - Carte avec des obstacles légers (zones grises à coût modéré).
   - Types de cases :
     - G (green) : Coût = 1.
     - W (gray)  : Coût = 250.
     - B (blue)  : Coût = 10.
     - S (yellow): Coût = 15.

3. **graphTest3.txt**
   - Carte complexe avec des obstacles majeurs (zones grises à coût élevé).
   - Types de cases :
     - G (green) : Coût = 1.
     - W (gray)  : Coût = 570.
     - B (blue)  : Coût = 10.
     - S (yellow): Coût = 15.

---

### Conclusions
1. **Efficacité :** A\* explore moins de nœuds que Dijkstra, sauf lorsque les obstacles deviennent très dominants.
2. **Rapidité :** A\* est systématiquement plus rapide que Dijkstra.
3. **Optimalité :** Les deux algorithmes trouvent toujours le chemin optimal, quel que soit le graphe.

---

## Améliorations Futures
- Tester sur des cartes plus grandes et irrégulières.
- Implémenter d’autres heuristiques pour A\* et évaluer leur impact.
- Ajouter une interface pour charger dynamiquement les fichiers de graphe sans modifier le code.

---

Merci ! 😊

