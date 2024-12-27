import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Charger et résoudre un labyrinthe depuis un fichier
        testLabyrintheDepuisFichier();
    }

    private static void testLabyrintheDepuisFichier() {
        // Charger le labyrinthe depuis un fichier
        Labyrinthe labyrinthe = new Labyrinthe("MiniProjet/labyrinthes/labyrinthe.txt");

        // Afficher la grille du labyrinthe
        System.out.println("Grille du labyrinthe :");
        labyrinthe.displayLabyrinthe();

        // Obtenir le graphe du labyrinthe
        Graphe graphe = labyrinthe.getGraphe();

        // Afficher les connexions du graphe (optionnel)
        System.out.println("\nGraphe du labyrinthe :");
        graphe.afficherGraphe();

        // Sélection de l'heuristique
        Scanner scanner = new Scanner(System.in);
        AStar.Heuristique choixHeuristique = null;

        System.out.println("\nVeuillez choisir l'heuristique pour A* :");
        System.out.println("1 - Manhattan");
        System.out.println("2 - Euclidienne");
        System.out.print("Votre choix : ");
        int choix = scanner.nextInt();

        if (choix == 1) {
            choixHeuristique = AStar.Heuristique.MANHATTAN;
        } else if (choix == 2) {
            choixHeuristique = AStar.Heuristique.EUCLIDIENNE;
        } else {
            System.out.println("Choix invalide. Utilisation de l'heuristique Manhattan par défaut.");
            choixHeuristique = AStar.Heuristique.MANHATTAN;
        }

        // Résoudre le labyrinthe avec l'algorithme A* et l'heuristique choisie
        AStar aStar = new AStar(choixHeuristique);
        Cell entree = labyrinthe.getEntree(); // Cellule de départ
        Cell sortie = labyrinthe.getSortie(); // Cellule de sortie

        System.out.println("\nRésolution du labyrinthe avec A* (" + choixHeuristique + ") :");
        List<Cell> chemin = aStar.trouverChemin(graphe, entree, sortie);

        // Afficher le chemin trouvé
        if (chemin.isEmpty()) {
            System.out.println("Aucun chemin trouvé !");
        } else {
            System.out.println("Chemin trouvé :");
            for (Cell cell : chemin) {
                System.out.print(cell + " ");
            }
            System.out.println();

            // Afficher la grille avec le chemin trouvé
            char[][] grilleAvecChemin = labyrinthe.toCharGridWithPath(chemin);
            System.out.println("\nGrille avec le chemin trouvé :");
            for (char[] ligne : grilleAvecChemin) {
                System.out.println(ligne);
            }

            // Optionnel : Affichage graphique
            LabyrintheGraphique.afficherLabyrinthe(grilleAvecChemin);
        }
    }
}
