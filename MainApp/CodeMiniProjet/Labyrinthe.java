package MainApp.CodeMiniProjet;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Labyrinthe {
    /**
     * Classe interne pour représenter une position dans la grille.
     */
    static class Position {
        int x, y; // Coordonnées
        double cost, heuristic; // Coût actuel et heuristique estimée

        public Position(int x, int y, double cost, double heuristic) {
            this.x = x;
            this.y = y;
            this.cost = cost;
            this.heuristic = heuristic;
        }
    }

    /**
     * Résout le labyrinthe à l'aide de l'algorithme A*.
     * @param grille La grille du labyrinthe.
     * @param feuDynamique Indique si le feu se propage.
     * @param heuristiqueAvancee Utilise une heuristique avancée si vrai.
     * @return true si le prisonnier peut atteindre la sortie, false sinon.
     */
    public static boolean scenario(char[][] grille, boolean feuDynamique, boolean heuristiqueAvancee) {
        int nodesExplored = 0; // Compteur de nœuds explorés
        int n = grille.length, m = grille[0].length;

        Position start = null, end = null;
        Queue<Position> queueFeu = new LinkedList<>();
        int[][] tempsFeu = new int[n][m];
        Map<Position, Position> prev = new HashMap<>();

        // Initialisation des temps de propagation du feu
        for (int i = 0; i < n; i++) Arrays.fill(tempsFeu[i], -1);

        // Parcourir la grille pour identifier les points clés
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grille[i][j] == 'D') start = new Position(i, j, 0, 0); // Départ
                if (grille[i][j] == 'S') end = new Position(i, j, 0, 0); // Sortie
                if (grille[i][j] == 'F') { // Feu
                    queueFeu.add(new Position(i, j, 0, 0));
                    tempsFeu[i][j] = 0;
                }
            }
        }

        // Vérification des positions de départ et sortie
        if (start == null || end == null) {
            throw new IllegalArgumentException("Grille invalide : Le prisonnier (D) ou la sortie (S) est manquant.");
        }

        // Propagation dynamique du feu
        if (feuDynamique) {
            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};
            while (!queueFeu.isEmpty()) {
                Position feu = queueFeu.poll();
                for (int d = 0; d < 4; d++) {
                    int fx = feu.x + dx[d];
                    int fy = feu.y + dy[d];
                    if (fx >= 0 && fy >= 0 && fx < n && fy < m && grille[fx][fy] == '.' && tempsFeu[fx][fy] == -1) {
                        tempsFeu[fx][fy] = (int) feu.cost + 1;
                        queueFeu.add(new Position(fx, fy, feu.cost + 1, 0));
                    }
                }
            }
        }

        // A* pour trouver le chemin
        PriorityQueue<Position> openSet = new PriorityQueue<>(Comparator.comparingDouble(p -> p.cost + p.heuristic));
        boolean[][] visited = new boolean[n][m];
        openSet.add(start);
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        while (!openSet.isEmpty()) {
            Position current = openSet.poll();
            nodesExplored++;

            if (visited[current.x][current.y]) continue;
            visited[current.x][current.y] = true;

            if (grille[current.x][current.y] == 'S') {
                int pathLength = marquerChemin(current, prev, grille); // Obtenez la longueur du chemin
                System.out.println("Longueur du chemin : " + pathLength);
                System.out.println("Nombre de nœuds explorés : " + nodesExplored);
                return true;
            }

            for (int d = 0; d < 4; d++) {
                int nx = current.x + dx[d];
                int ny = current.y + dy[d];
                if (nx >= 0 && nx < n && ny >= 0 && ny < m && !visited[nx][ny]) {
                    if (grille[nx][ny] == '.' || grille[nx][ny] == 'S') {
                        double newCost = current.cost + 1;
                        double heuristic = heuristiqueAvancee
                                ? Math.abs(nx - end.x) + Math.abs(ny - end.y) + (tempsFeu[nx][ny] == -1 ? 0 : tempsFeu[nx][ny])
                                : Math.abs(nx - end.x) + Math.abs(ny - end.y);
                        Position next = new Position(nx, ny, newCost, heuristic);
                        openSet.add(next);
                        prev.put(next, current);
                    }
                }
            }
        }

        System.out.println("Nombre de nœuds explorés : " + nodesExplored);
        return false;
    }

    /**
     * Marque le chemin suivi par le prisonnier avec '*'.
     */
    private static int marquerChemin(Position end, Map<Position, Position> prev, char[][] grille) {
        Position current = end;
        int pathLength = 0; // Compteur pour la longueur du chemin

        while (prev.containsKey(current)) {
            if (grille[current.x][current.y] != 'S') { // Ne pas remplacer la sortie
                grille[current.x][current.y] = '*';
                pathLength++; // Incrémentez chaque fois qu'une case est marquée
            }
            current = prev.get(current);
        }
        return pathLength;
    }


    /**
     * Affiche la grille dans une fenêtre graphique.
     */
    public static void afficherGrille(char[][] grille) {
        JFrame frame = new JFrame("Labyrinthe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.add(new Board1(grille));
        frame.setVisible(true);
    }

    /**
     * Affiche la grille dans la console.
     */
    private static void afficherGrilleConsole(char[][] grille) {
        for (char[] ligne : grille) {
            System.out.println(Arrays.toString(ligne));
        }
        System.out.println();
    }

    /**
     * Clone une grille pour éviter de modifier l'original.
     */
    private static char[][] cloneGrille(char[][] grille) {
        int n = grille.length;
        int m = grille[0].length;
        char[][] clone = new char[n][m];
        for (int i = 0; i < n; i++) {
            System.arraycopy(grille[i], 0, clone[i], 0, m);
        }
        return clone;
    }

    // Pour  afficher les grilles côte à côte
    private static void afficherComparaisonGrilles(char[][] grilleBase, char[][] grilleAvancee) {
        System.out.println("Comparaison des chemins (base vs avancée) :");
        int n = grilleBase.length;

        for (int i = 0; i < n; i++) {
            System.out.printf("%-30s | %-30s%n", Arrays.toString(grilleBase[i]), Arrays.toString(grilleAvancee[i]));
        }
        System.out.println(); // Ligne vide pour séparer les comparaisons
    }



    /**
     * Méthode principale pour exécuter le programme.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<char[][]> grilles = new ArrayList<>();
        int nombreInstances;

        try {
            if (args.length > 0) {
                // Lecture depuis un fichier
                System.out.println("Lecture du fichier : " + args[0]);
                Scanner fileScanner = new Scanner(new File(args[0]));
                nombreInstances = Integer.parseInt(fileScanner.nextLine());

                for (int t = 0; t < nombreInstances; t++) {
                    String[] dimensions = fileScanner.nextLine().trim().split(" ");
                    int n = Integer.parseInt(dimensions[0]);
                    int m = Integer.parseInt(dimensions[1]);

                    char[][] grille = new char[n][m];
                    for (int i = 0; i < n; i++) {
                        grille[i] = fileScanner.nextLine().trim().toCharArray();
                    }
                    grilles.add(grille);
                }
                fileScanner.close();
            } else {
                // Lecture depuis la console
                System.out.print("Nombre d'instances : ");
                nombreInstances = Integer.parseInt(scanner.nextLine());

                for (int t = 0; t < nombreInstances; t++) {
                    System.out.println("Instance " + (t + 1));
                    System.out.print("Nombre de lignes et colonnes (N M) : ");
                    String[] dimensions = scanner.nextLine().trim().split(" ");
                    int n = Integer.parseInt(dimensions[0]);
                    int m = Integer.parseInt(dimensions[1]);

                    char[][] grille = new char[n][m];
                    System.out.println("Entrez la grille :");
                    for (int i = 0; i < n; i++) {
                        grille[i] = scanner.nextLine().trim().toCharArray();
                    }
                    grilles.add(grille);
                }
            }

            // Résolution et affichage des grilles
            for (int i = 0; i < grilles.size(); i++) {
                System.out.println("Instance " + (i + 1) + " avec heuristique de base :");
                char[][] baseGrille = cloneGrille(grilles.get(i));
                boolean resultBase = scenario(baseGrille, true, false); // Résolution avec heuristique de base
                System.out.println("Résultat (heuristique de base) : " + (resultBase ? "Y" : "N"));
                afficherGrilleConsole(baseGrille); // Affiche la grille dans la console
                afficherGrille(baseGrille); // Affiche la grille graphiquement
                System.out.println("Appuyez sur Entrée pour continuer...");
                new Scanner(System.in).nextLine(); // Pause pour l'utilisateur

                System.out.println("Instance " + (i + 1) + " avec heuristique avancée :");
                char[][] advancedGrille = cloneGrille(grilles.get(i));
                boolean resultAdvanced = scenario(advancedGrille, true, true); // Résolution avec heuristique avancée
                System.out.println("Résultat (heuristique avancée) : " + (resultAdvanced ? "Y" : "N"));
                afficherGrilleConsole(advancedGrille); // Affiche la grille dans la console
                afficherGrille(advancedGrille); // Affiche la grille graphiquement
                System.out.println("Appuyez sur Entrée pour continuer......");
                new Scanner(System.in).nextLine(); // Pause pour l'utilisateur

                // Comparaison des grilles des deux heuristiques
                System.out.println("Instance " + (i + 1) + " : Comparaison des chemins (base vs avancée)");
                afficherComparaisonGrilles(baseGrille, advancedGrille);
                System.out.println("Vous pouvez fermer l'interface graphique......");
                new Scanner(System.in).nextLine(); // Pause pour l'utilisateur
            }

        } catch (FileNotFoundException e) {
            System.err.println("Erreur : Fichier non trouvé !");
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur : " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

// Classe d'affichage graphique
class Board1 extends JComponent {
    private static final long serialVersionUID = 1L;
    char[][] grille;
    int cellSize = 30;

    public Board1(char[][] grille) {
        this.grille = grille;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int rows = grille.length, cols = grille[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int x = j * cellSize, y = i * cellSize;
                if (grille[i][j] == 'F') g2.setPaint(Color.RED);
                else if (grille[i][j] == 'D') g2.setPaint(Color.BLUE);
                else if (grille[i][j] == 'S') g2.setPaint(Color.GREEN);
                else if (grille[i][j] == '*') g2.setPaint(Color.YELLOW);
                else g2.setPaint(Color.LIGHT_GRAY);
                g2.fill(new Rectangle2D.Double(x, y, cellSize, cellSize));
                g2.setPaint(Color.BLACK);
                g2.draw(new Rectangle2D.Double(x, y, cellSize, cellSize));
            }
        }
    }
}
