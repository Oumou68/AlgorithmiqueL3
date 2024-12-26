import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Labyrinthe {
    private Cell[][] grid;  // Grille représentant le labyrinthe
    private int width;      // Largeur
    private int height;     // Hauteur
    private Graphe graphe;  // Graphe représentant le labyrinthe

    public Labyrinthe(String filePath) {
        this.graphe = new Graphe();
        loadLabyrinthe(filePath); // Appel de la méthode pour charger le fichier
        construireGraphe();      // Construire le graphe après avoir chargé la grille
    }

    private void loadLabyrinthe(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            List<String> lignes = new ArrayList<>();
            String line;
    
            // Charger toutes les lignes tout en vérifiant leur longueur
            int maxLength = 0;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    lignes.add(line);
                    maxLength = Math.max(maxLength, line.length());
                }
            }
    
            // Initialiser la grille avec les dimensions correctes
            height = lignes.size();
            width = maxLength;
            grid = new Cell[height][width];
    
            // Remplir la grille en tenant compte des lignes manquantes ou des lignes plus courtes
            for (int row = 0; row < height; row++) {
                String ligne = lignes.get(row);
                for (int col = 0; col < width; col++) {
                    char c = (col < ligne.length()) ? ligne.charAt(col) : '#'; // Remplir les colonnes manquantes avec des murs
                    boolean isWall = (c == '#');
                    boolean isStart = (c == 'S');
                    boolean isEnd = (c == 'E');
                    grid[row][col] = new Cell(row, col, isWall, isStart, isEnd);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier : " + e.getMessage());
        }
    }
    

    private void construireGraphe() {
        boolean entreeAjoutee = false;
        boolean sortieAjoutee = false;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell cell = grid[row][col];
                if (!cell.isWall()) {
                    graphe.ajouterNoeud(cell);

                    // Vérifier si la cellule est l'entrée ou la sortie
                    if (cell.isStart()) {
                        System.out.println("Cellule d'entrée détectée : " + cell);
                        entreeAjoutee = true;
                    }
                    if (cell.isEnd()) {
                        System.out.println("Cellule de sortie détectée : " + cell);
                        sortieAjoutee = true;
                    }

                    // Ajouter les voisins
                    if (row > 0 && !grid[row - 1][col].isWall())
                        graphe.ajouterArete(cell, grid[row - 1][col]);
                    if (row < height - 1 && !grid[row + 1][col].isWall())
                        graphe.ajouterArete(cell, grid[row + 1][col]);
                    if (col > 0 && !grid[row][col - 1].isWall())
                        graphe.ajouterArete(cell, grid[row][col - 1]);
                    if (col < width - 1 && !grid[row][col + 1].isWall())
                        graphe.ajouterArete(cell, grid[row][col + 1]);
                }
            }
        }

        if (!entreeAjoutee || !sortieAjoutee) {
            throw new IllegalStateException("L'entrée (S) ou la sortie (E) n'a pas été ajoutée au graphe !");
        }
    }

    public void displayLabyrinthe() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j].isWall()) System.out.print("#");
                else if (grid[i][j].isStart()) System.out.print("S");
                else if (grid[i][j].isEnd()) System.out.print("E");
                else System.out.print(".");
            }
            System.out.println();
        }
    }

    public void displayLabyrintheWithPath(List<Cell> chemin) {
        char[][] displayGrid = new char[height][width];

        // Copier la grille existante
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j].isWall()) displayGrid[i][j] = '#';
                else if (grid[i][j].isStart()) displayGrid[i][j] = 'S';
                else if (grid[i][j].isEnd()) displayGrid[i][j] = 'E';
                else displayGrid[i][j] = '.';
            }
        }

        // Ajouter le chemin au rendu
        for (Cell cell : chemin) {
            if (!cell.isStart() && !cell.isEnd()) {
                displayGrid[cell.getX()][cell.getY()] = '*';
            }
        }

        // Afficher la grille
        for (char[] line : displayGrid) {
            System.out.println(line);
        }
    }

    public char[][] toCharGridWithPath(List<Cell> chemin) {
        // Copier la grille actuelle dans un tableau de caractères
        char[][] displayGrid = new char[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j].isWall()) {
                    displayGrid[i][j] = '#';
                } else if (grid[i][j].isStart()) {
                    displayGrid[i][j] = 'S';
                } else if (grid[i][j].isEnd()) {
                    displayGrid[i][j] = 'E';
                } else {
                    displayGrid[i][j] = '.';
                }
            }
        }

        // Ajouter le chemin trouvé dans la grille
        for (Cell cell : chemin) {
            if (!cell.isStart() && !cell.isEnd()) { // Ne pas modifier les points de départ et d'arrivée
                displayGrid[cell.getX()][cell.getY()] = '*';
            }
        }

        return displayGrid; // Retourner la grille avec le chemin
    }

    public Graphe getGraphe() {
        return graphe;
    }

    public Cell getEntree() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j].isStart()) { // Vérifie si la cellule est l'entrée
                    return grid[i][j];
                }
            }
        }
        throw new IllegalStateException("Aucune entrée 'S' trouvée dans le labyrinthe !");
    }

    public Cell getSortie() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j].isEnd()) { // Vérifie si la cellule est la sortie
                    return grid[i][j];
                }
            }
        }
        throw new IllegalStateException("Aucune sortie 'E' trouvée dans le labyrinthe !");
    }
}

