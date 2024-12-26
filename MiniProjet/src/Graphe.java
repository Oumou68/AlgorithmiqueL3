import java.util.*;

public class Graphe {
    private Map<Cell, List<Cell>> adjacence; // Liste d'adjacence
    private Map<String, Double> poids;      // Poids des arêtes (clé : "cell1-cell2")

    // Constructeur
    public Graphe() {
        this.adjacence = new HashMap<>();
        this.poids = new HashMap<>();
    }

    // Ajouter un nœud (cellule) au graphe
    public void ajouterNoeud(Cell cell) {
        adjacence.putIfAbsent(cell, new ArrayList<>());
    }

    // Ajouter une arête avec un coût entre deux cellules
    public void ajouterArete(Cell cell1, Cell cell2, double cout) {
        adjacence.putIfAbsent(cell1, new ArrayList<>());
        adjacence.putIfAbsent(cell2, new ArrayList<>());

        if (!adjacence.get(cell1).contains(cell2)) {
            adjacence.get(cell1).add(cell2);
            poids.put(genererCle(cell1, cell2), cout);
            poids.put(genererCle(cell2, cell1), cout); // Symétrique si graphe non orienté
        }
    }

    // Supprimer une arête entre deux cellules
    public void supprimerArete(Cell cell1, Cell cell2) {
        adjacence.getOrDefault(cell1, new ArrayList<>()).remove(cell2);
        adjacence.getOrDefault(cell2, new ArrayList<>()).remove(cell1);
        poids.remove(genererCle(cell1, cell2));
        poids.remove(genererCle(cell2, cell1));
    }

    // Obtenir les voisins d'une cellule
    public List<Cell> getVoisins(Cell cell) {
        return adjacence.getOrDefault(cell, new ArrayList<>());
    }

    // Obtenir le coût entre deux cellules
    public double getCout(Cell cell1, Cell cell2) {
        return poids.getOrDefault(genererCle(cell1, cell2), Double.POSITIVE_INFINITY);
    }

    // Méthode utilitaire pour générer une clé unique pour une arête
    private String genererCle(Cell cell1, Cell cell2) {
        return cell1.hashCode() + "-" + cell2.hashCode();
    }

    // Afficher le graphe pour vérification
    public void afficherGraphe() {
        System.out.println("Liste des connexions dans le graphe :");
        for (Map.Entry<Cell, List<Cell>> entry : adjacence.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            for (Cell voisin : entry.getValue()) {
                System.out.print(voisin + " (poids: " + getCout(entry.getKey(), voisin) + ") ");
            }
            System.out.println();
        }
    }
}
