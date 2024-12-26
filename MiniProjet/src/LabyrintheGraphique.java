import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LabyrintheGraphique extends JPanel {
    private char[][] grille;

    public LabyrintheGraphique(char[][] grille) {
        this.grille = grille;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellSize = 40; // Taille de chaque cellule
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                if (grille[i][j] == '#') {
                    g.setColor(Color.BLACK);
                } else if (grille[i][j] == 'S') {
                    g.setColor(Color.GREEN);
                } else if (grille[i][j] == 'E') {
                    g.setColor(Color.RED);
                } else if (grille[i][j] == '*') {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g.setColor(Color.GRAY);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }

    public static void afficherLabyrinthe(char[][] grille) {
        JFrame frame = new JFrame("Labyrinthe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 900);
        frame.add(new LabyrintheGraphique(grille));
        frame.setVisible(true);
    }
}
