import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Charger et résoudre un labyrinthe depuis un fichier
        testLabyrintheDepuisFichier();
    }

    private static void testLabyrintheDepuisFichier() {
        // Charger le labyrinthe depuis un fichier
        Labyrinthe labyrinthe = new Labyrinthe("MiniProjet/labComplexe.txt");

        // Afficher la grille du labyrinthe
        System.out.println("Grille du labyrinthe :");
        labyrinthe.displayLabyrinthe();

        // Obtenir le graphe du labyrinthe
        Graphe graphe = labyrinthe.getGraphe();

        // Afficher les connexions du graphe (optionnel)
        System.out.println("\nGraphe du labyrinthe :");
        graphe.afficherGraphe();

        // Résoudre le labyrinthe avec l'algorithme A*
        AStar aStar = new AStar();
        Cell entree = labyrinthe.getEntree(); // Cellule de départ
        Cell sortie = labyrinthe.getSortie(); // Cellule de sortie

        System.out.println("\nRésolution du labyrinthe avec A* :");
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
