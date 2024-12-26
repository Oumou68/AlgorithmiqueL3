import java.util.*;

public class AStar {

    // Trouver le chemin le plus court entre deux cellules
    public List<Cell> trouverChemin(Graphe graphe, Cell depart, Cell arrivee) {
        PriorityQueue<Cell> openSet = new PriorityQueue<>(Comparator.comparingDouble(Cell::getCoutTotal));
        Set<Cell> closedSet = new HashSet<>();
        Map<Cell, Double> gScore = new HashMap<>(); // Coût actuel pour chaque cellule
        Map<Cell, Cell> parents = new HashMap<>(); // Pour reconstruire le chemin

        // Initialisation
        gScore.put(depart, 0.0);
        depart.setHeuristique(calculerHeuristique(depart, arrivee));
        openSet.add(depart);

        while (!openSet.isEmpty()) {
            // Extraire la cellule avec le plus faible coût total (g + h)
            Cell courant = openSet.poll();

            // Si nous avons atteint la cellule d'arrivée, reconstruire le chemin
            if (courant.equals(arrivee)) {
                return reconstruireChemin(parents, arrivee);
            }

            closedSet.add(courant);

            // Explorer les voisins
            for (Cell voisin : graphe.getVoisins(courant)) {
                if (closedSet.contains(voisin)) {
                    continue; // Ignorer les voisins déjà explorés
                }

                double tentativeGScore = gScore.getOrDefault(courant, Double.POSITIVE_INFINITY) + 1; // Coût d'une cellule à son voisin

                if (tentativeGScore < gScore.getOrDefault(voisin, Double.POSITIVE_INFINITY)) {
                    // Mettre à jour les scores et le parent du voisin
                    parents.put(voisin, courant);
                    gScore.put(voisin, tentativeGScore);
                    voisin.setHeuristique(calculerHeuristique(voisin, arrivee));

                    if (!openSet.contains(voisin)) {
                        openSet.add(voisin);
                    }
                }
            }
        }

        // Retourner une liste vide si aucun chemin n'est trouvé
        return new ArrayList<>();
    }

    // Calculer l'heuristique (distance de Manhattan)
    private double calculerHeuristique(Cell courant, Cell arrivee) {
        return Math.abs(courant.getX() - arrivee.getX()) + Math.abs(courant.getY() - arrivee.getY());
    }

    // Reconstruire le chemin à partir des parents
    private List<Cell> reconstruireChemin(Map<Cell, Cell> parents, Cell arrivee) {
        List<Cell> chemin = new ArrayList<>();
        Cell courant = arrivee;

        while (courant != null) {
            chemin.add(courant);
            courant = parents.get(courant);
        }

        Collections.reverse(chemin); // Inverser pour obtenir le chemin dans le bon ordre
        return chemin;
    }
}
