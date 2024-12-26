import java.util.*;

public class Graphe {
    private Map<Cell, List<Cell>> adjacence; // Liste d'adjacence

    // Constructeur
    public Graphe() {
        this.adjacence = new HashMap<>();
    }

    // Ajouter un nœud (cellule) au graphe
    public void ajouterNoeud(Cell cell) {
        adjacence.putIfAbsent(cell, new ArrayList<>());
    }

    // Ajouter une arête entre deux cellules
    public void ajouterArete(Cell cell1, Cell cell2) {
        adjacence.putIfAbsent(cell1, new ArrayList<>());
        adjacence.putIfAbsent(cell2, new ArrayList<>());

        if (!adjacence.get(cell1).contains(cell2)) {
            adjacence.get(cell1).add(cell2);
        }
        if (!adjacence.get(cell2).contains(cell1)) {
            adjacence.get(cell2).add(cell1);
        }
    }

    // Supprimer une arête entre deux cellules
    public void supprimerArete(Cell cell1, Cell cell2) {
        adjacence.getOrDefault(cell1, new ArrayList<>()).remove(cell2);
        adjacence.getOrDefault(cell2, new ArrayList<>()).remove(cell1);
    }

    // Supprimer un nœud et ses connexions
    public void supprimerNoeud(Cell cell) {
        adjacence.values().forEach(liste -> liste.remove(cell));
        adjacence.remove(cell);
    }

    // Obtenir les voisins d'une cellule
    public List<Cell> getVoisins(Cell cell) {
        return adjacence.getOrDefault(cell, new ArrayList<>());
    }

    // Afficher le graphe pour vérification
    public void afficherGraphe() {
        System.out.println("Liste des connexions dans le graphe :");
        for (Map.Entry<Cell, List<Cell>> entry : adjacence.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            for (Cell voisin : entry.getValue()) {
                System.out.print(voisin + " ");
            }
            System.out.println();
        }
    }

    // Vérifier s'il existe un chemin entre deux cellules (BFS)
    public boolean existeChemin(Cell source, Cell destination) {
        Set<Cell> visite = new HashSet<>();
        Queue<Cell> queue = new LinkedList<>();
        queue.add(source);
        visite.add(source);

        while (!queue.isEmpty()) {
            Cell courant = queue.poll();
            if (courant.equals(destination)) return true;

            for (Cell voisin : getVoisins(courant)) {
                if (!visite.contains(voisin)) {
                    visite.add(voisin);
                    queue.add(voisin);
                }
            }
        }
        return false;
    }

    // Obtenir le degré d'un nœud
    public int getDegre(Cell cell) {
        return adjacence.getOrDefault(cell, new ArrayList<>()).size();
    }
}
