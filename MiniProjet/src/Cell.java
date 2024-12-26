public class Cell {
    private int x, y;           // Coordonnées de la cellule
    private boolean isWall;     // Indique si la cellule est un mur
    private boolean isStart;    // Indique si la cellule est l'entrée
    private boolean isEnd;      // Indique si la cellule est la sortie
    private double gScore;      // Coût pour atteindre cette cellule
    private double heuristique; // Estimation du coût restant

    public Cell(int x, int y, boolean isWall, boolean isStart, boolean isEnd) {
        this.x = x;
        this.y = y;
        this.isWall = isWall;
        this.isStart = isStart;
        this.isEnd = isEnd;
        this.gScore = Double.POSITIVE_INFINITY; // Initialisé à l'infini
        this.heuristique = 0; // Initialisé à 0
    }

    // Getters et setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWall() {
        return isWall;
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public double getGScore() {
        return gScore;
    }

    public void setGScore(double gScore) {
        this.gScore = gScore;
    }

    public double getHeuristique() {
        return heuristique;
    }

    public void setHeuristique(double heuristique) {
        this.heuristique = heuristique;
    }

    // Retourne le coût total (g + h)
    public double getCoutTotal() {
        return gScore + heuristique;
    }

    // Comparaison pour PriorityQueue (par coût total)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cell cell = (Cell) obj;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
