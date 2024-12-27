import java.util.*;

public class AStar {

    // Enum pour choisir l'heuristique
    public enum Heuristique {
        MANHATTAN,
        EUCLIDIENNE
    }

    private Heuristique typeHeuristique;

    // Constructeur pour définir l'heuristique
    public AStar(Heuristique typeHeuristique) {
        this.typeHeuristique = typeHeuristique;
    }

    /**
     * Trouver le chemin le plus court entre deux cellules dans un graphe en utilisant A*.
     * @param graphe Le graphe représentant le labyrinthe.
     * @param depart La cellule de départ.
     * @param arrivee La cellule d'arrivée.
     * @return Une liste des cellules formant le chemin le plus court, ou une liste vide si aucun chemin n'existe.
     */
    public List<Cell> trouverChemin(Graphe graphe, Cell depart, Cell arrivee) {
        // Initialisation de la file de priorité et des scores
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

            if (courant.equals(arrivee)) {
                return reconstruireChemin(parents, arrivee);
            }

            closedSet.add(courant);

            // Explorer les voisins
            for (Cell voisin : graphe.getVoisins(courant)) {
                if (closedSet.contains(voisin)) {
                    continue;
                }

                double tentativeGScore = gScore.getOrDefault(courant, Double.POSITIVE_INFINITY) + graphe.getCout(courant, voisin);

                if (tentativeGScore < gScore.getOrDefault(voisin, Double.POSITIVE_INFINITY)) {
                    // Mise à jour des parents et des scores
                    parents.put(voisin, courant);
                    gScore.put(voisin, tentativeGScore);
                    voisin.setHeuristique(calculerHeuristique(voisin, arrivee));

                    if (!openSet.contains(voisin)) {
                        openSet.add(voisin);
                    }
                }
            }
        }

        // Aucun chemin trouvé
        return new ArrayList<>();
    }

    // Calculer l'heuristique en fonction du type choisi
    private double calculerHeuristique(Cell courant, Cell arrivee) {
        switch (typeHeuristique) {
            case MANHATTAN:
                return Math.abs(courant.getX() - arrivee.getX()) + Math.abs(courant.getY() - arrivee.getY());
            case EUCLIDIENNE:
                return Math.sqrt(Math.pow(courant.getX() - arrivee.getX(), 2) + Math.pow(courant.getY() - arrivee.getY(), 2));
            default:
                throw new IllegalArgumentException("Type d'heuristique non reconnu");
        }
    }

    // Reconstruire le chemin à partir des parents
    private List<Cell> reconstruireChemin(Map<Cell, Cell> parents, Cell arrivee) {
        List<Cell> chemin = new ArrayList<>();
        Cell courant = arrivee;

        while (courant != null) {
            chemin.add(courant);
            courant = parents.get(courant);
        }

        Collections.reverse(chemin);
        return chemin;
    }
}
