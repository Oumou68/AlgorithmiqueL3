package MainApp;
// Par Sylvain Lobry, pour le cours "IF05X040 Algorithmique avanc�e"
// de l'Universit� de Paris, 11/2020


import MainApp.WeightedGraph.Edge;
import MainApp.WeightedGraph.Graph;
import MainApp.WeightedGraph.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.HashSet;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JFrame;

import java.util.Scanner;


//Classe pour g�rer l'affichage
class Board extends JComponent
{
    private static final long serialVersionUID = 1L;
    Graph graph;
    int pixelSize;
    int ncols;
    int nlines;
    HashMap<Integer, String> colors;
    int start;
    int end;
    double max_distance;
    int current;
    LinkedList<Integer> path;

    public Board(Graph graph, int pixelSize, int ncols, int nlines, HashMap<Integer, String> colors, int start, int end)
    {
        super();
        this.graph = graph;
        this.pixelSize = pixelSize;
        this.ncols = ncols;
        this.nlines = nlines;
        this.colors = colors;
        this.start = start;
        this.end = end;
        this.max_distance = ncols * nlines;
        this.current = -1;
        this.path = null;
    }

    //Mise � jour de l'affichage
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //Ugly clear of the frame
        g2.setColor(Color.cyan);
        g2.fill(new Rectangle2D.Double(0,0,this.ncols*this.pixelSize, this.nlines*this.pixelSize));


        int num_case = 0;
        for (WeightedGraph.Vertex v : this.graph.vertexlist)
        {
            double type = v.indivTime;
            int i = num_case / this.ncols;
            int j = num_case % this.ncols;

            if (colors.get((int)type).equals("green"))
                g2.setPaint(Color.green);
            if (colors.get((int)type).equals("gray"))
                g2.setPaint(Color.gray);
            if (colors.get((int)type).equals("blue"))
                g2.setPaint(Color.blue);
            if (colors.get((int)type).equals("yellow"))
                g2.setPaint(Color.yellow);
            g2.fill(new Rectangle2D.Double(j*this.pixelSize, i*this.pixelSize, this.pixelSize, this.pixelSize));

            if (num_case == this.current)
            {
                g2.setPaint(Color.red);
                g2.draw(new Ellipse2D.Double(j*this.pixelSize+this.pixelSize/2, i*this.pixelSize+this.pixelSize/2, 6, 6));
            }
            if (num_case == this.start)
            {
                g2.setPaint(Color.white);
                g2.fill(new Ellipse2D.Double(j*this.pixelSize+this.pixelSize/2, i*this.pixelSize+this.pixelSize/2, 4, 4));

            }
            if (num_case == this.end)
            {
                g2.setPaint(Color.black);
                g2.fill(new Ellipse2D.Double(j*this.pixelSize+this.pixelSize/2, i*this.pixelSize+this.pixelSize/2, 4, 4));
            }

            num_case += 1;
        }

        num_case = 0;
        for (WeightedGraph.Vertex v : this.graph.vertexlist)
        {
            int i = num_case / this.ncols;
            int j = num_case % this.ncols;
            if (v.timeFromSource < Double.POSITIVE_INFINITY)
            {
                float g_value = (float) (1 - v.timeFromSource / this.max_distance);
                if (g_value < 0)
                    g_value = 0;
                g2.setPaint(new Color(g_value, g_value, g_value));
                g2.fill(new Ellipse2D.Double(j*this.pixelSize+this.pixelSize/2, i*this.pixelSize+this.pixelSize/2, 4, 4));
                WeightedGraph.Vertex prev = v.prev;
                if (prev != null)
                {
                    int i2 = prev.num / this.ncols;
                    int j2 = prev.num % this.ncols;
                    g2.setPaint(Color.black);
                    g2.draw(new Line2D.Double(j * this.pixelSize + this.pixelSize/2, i * this.pixelSize + this.pixelSize/2, j2 * this.pixelSize + this.pixelSize/2, i2 * this.pixelSize + this.pixelSize/2));
                }
            }

            num_case += 1;
        }

        int prev = -1;
        if (this.path != null)
        {
            g2.setStroke(new BasicStroke(3.0f));
            for (int cur : this.path)
            {
                if (prev != -1)
                {
                    g2.setPaint(Color.red);
                    int i = prev / this.ncols;
                    int j = prev % this.ncols;
                    int i2 = cur / this.ncols;
                    int j2 = cur % this.ncols;
                    g2.draw(new Line2D.Double(j * this.pixelSize + this.pixelSize/2, i * this.pixelSize + this.pixelSize/2, j2 * this.pixelSize + this.pixelSize/2, i2 * this.pixelSize + this.pixelSize/2));
                }
                prev = cur;
            }
        }
    }

    //Mise � jour du graphe (� appeler avant de mettre � jour l'affichage)
    public void update(Graph graph, int current)
    {
        this.graph = graph;
        this.current = current;
        repaint();
    }

    //Indiquer le chemin (pour affichage)
    public void addPath(Graph graph, LinkedList<Integer> path)
    {
        this.graph = graph;
        this.path = path;
        this.current = -1;
        repaint();
    }
}

//Classe principale. C'est ici que vous devez faire les modifications
public class App {

    //Initialise l'affichage
    private static void drawBoard(Board board, int nlines, int ncols, int pixelSize)
    {
        JFrame window = new JFrame("Plus court chemin");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, ncols*pixelSize+20, nlines*pixelSize+40);
        window.getContentPane().add(board);
        window.setVisible(true);
    }

    //M�thode A*
    //graph: le graphe repr�sentant la carte
    //start: un entier repr�sentant la case de d�part
    //       (entier unique correspondant � la case obtenue dans le sens de la lecture)
    //end: un entier repr�sentant la case d'arriv�e
    //       (entier unique correspondant � la case obtenue dans le sens de la lecture)
    //ncols: le nombre de colonnes dans la carte
    //numberV: le nombre de cases dans la carte
    //board: l'affichage
    //retourne une liste d'entiers correspondant au chemin.
    private static LinkedList<Integer> AStar(Graph graph, int start, int end, int ncols, int numberV, Board board) {
        // Initialisation : la distance de la source est 0, toutes les autres sont infinies.
        graph.vertexlist.get(start).timeFromSource=0;
        int number_tries = 0;

        //TODO: mettre tous les noeuds du graphe dans la liste des noeuds � visiter:
        HashSet<Integer> to_visit = new HashSet<Integer>();
        for (int i = 0; i < numberV; i++) {
            to_visit.add(i);
        }

        //TODO: Remplir l'attribut graph.vertexlist.get(v).heuristic pour tous les noeuds v du graphe:
        // Calculer l'heuristique pour tous les sommets
        for (int v = 0; v < numberV; v++) {
            int vx = v % ncols; // Coordonnée x du sommet
            int vy = v / ncols; // Coordonnée y du sommet
            int ex = end % ncols; // Coordonnée x de l'arrivée
            int ey = end / ncols; // Coordonnée y de l'arrivée
            graph.vertexlist.get(v).heuristic = Math.sqrt(Math.pow(vx - ex, 2) + Math.pow(vy - ey, 2)); // Distance euclidienne
        }


        while (to_visit.contains(end)) {
            //TODO: trouver le noeud min_v parmis tous les noeuds v ayant la distance temporaire
            //(graph.vertexlist.get(v).timeFromSource + heuristic) minimale.
            int min_v = -1;
            double min_f = Double.MAX_VALUE;
            for (int v : to_visit) {
                double f = graph.vertexlist.get(v).timeFromSource + graph.vertexlist.get(v).heuristic;
                if (f < min_f) {
                    min_f = f;
                    min_v = v;
                }
            }

            // Si aucun chemin n'est trouvable, on arrête
            if (min_v == -1) {
                System.out.println("Aucun chemin disponible!");
                return new LinkedList<>();
            }

            //On l'enl�ve des noeuds � visiter
            to_visit.remove(min_v);
            number_tries ++;

            //TODO: pour tous ses voisins, on v�rifie si on est plus rapide en passant par ce noeud.
            for (int i = 0; i < graph.vertexlist.get(min_v).adjacencylist.size(); i++) {
                int to_try = graph.vertexlist.get(min_v).adjacencylist.get(i).destination;
                //A completer
                double weight = graph.vertexlist.get(min_v).adjacencylist.get(i).weight;

                // Relaxation : vérifier si une meilleure distance est trouvée
                if (to_visit.contains(to_try)) {
                    double new_distance = graph.vertexlist.get(min_v).timeFromSource + weight;
                    if (new_distance < graph.vertexlist.get(to_try).timeFromSource) {
                        graph.vertexlist.get(to_try).timeFromSource = new_distance;
                        graph.vertexlist.get(to_try).prev = graph.vertexlist.get(min_v); // Garder une trace du chemin
                    }
                }
            }
            //On met � jour l'affichage
            try {
                board.update(graph, min_v);
                Thread.sleep(10);
            } catch(InterruptedException e) {
                System.out.println("stop");
            }
        }

        System.out.println("Done! Using A*:");
        System.out.println("	Number of nodes explored: " + number_tries);
        System.out.println("	Total time of the path: " + graph.vertexlist.get(end).timeFromSource);
        LinkedList<Integer> path=new LinkedList<Integer>();
        int current = end;
        //TODO: remplir la liste path avec le chemin
        while (current != -1) {
            path.addFirst(current);
            current = graph.vertexlist.get(current).prev != null ? graph.vertexlist.get(current).prev.num : -1;
        }

        board.addPath(graph, path);
        return path;
    }

    //M�thode Dijkstra
    //graph: le graphe repr�sentant la carte
    //start: un entier repr�sentant la case de d�part
    //       (entier unique correspondant � la case obtenue dans le sens de la lecture)
    //end: un entier repr�sentant la case d'arriv�e
    //       (entier unique correspondant � la case obtenue dans le sens de la lecture)
    //numberV: le nombre de cases dans la carte
    //board: l'affichage
    //retourne une liste d'entiers correspondant au chemin.
    private static LinkedList<Integer> Dijkstra(Graph graph, int start, int end, int numberV, Board board) {
        graph.vertexlist.get(start).timeFromSource = 0;

        //TODO: mettre tous les noeuds du graphe dans la liste des noeuds � visiter:
        // Mettre tous les noeuds dans la liste des noeuds à visiter
        HashSet<Integer> to_visit = new HashSet<Integer>();
        for (int i = 0; i < numberV; i++) {
            to_visit.add(i);
        }
        
        int number_tries = 0;

        // Tant que le noeud final n'a pas été visité
        while (to_visit.contains(end)) {
            //TODO: trouver le noeud min_v parmis tous les noeuds v ayant la distance temporaire
            int min_v = -1;
            double min_dist = Double.MAX_VALUE;
            for (int v : to_visit) {
                double dist = graph.vertexlist.get(v).timeFromSource;
                if (dist < min_dist) {
                    min_dist = dist;
                    min_v = v;
                }
            }

            // Si aucun chemin n'est trouvable, on arrête
            if (min_v == -1) {
                System.out.println("Aucun chemin disponible!");
                return new LinkedList<>();
            }

            // Enlever min_v des noeuds à visiter
            to_visit.remove(min_v);
            number_tries += 1;

            // Pour tous ses voisins, mettre à jour la distance si on trouve un chemin plus court
            for (int i = 0; i < graph.vertexlist.get(min_v).adjacencylist.size(); i++) {
                int to_try = graph.vertexlist.get(min_v).adjacencylist.get(i).destination;
                double weight = graph.vertexlist.get(min_v).adjacencylist.get(i).weight;

                // Relaxation : mettre à jour la distance si un meilleur chemin est trouvé
                if (to_visit.contains(to_try)) {
                    double new_distance = graph.vertexlist.get(min_v).timeFromSource + weight;
                    if (new_distance < graph.vertexlist.get(to_try).timeFromSource) {
                        graph.vertexlist.get(to_try).timeFromSource = new_distance;
                        graph.vertexlist.get(to_try).prev = graph.vertexlist.get(min_v);; // Garder une trace du chemin
                    }
                }
            }

            // Mettre à jour l'affichage
            try {
                board.update(graph, min_v);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("stop");
            }
        }

        // Récupérer le chemin en remontant les noeuds précédents
        System.out.println("Done! Using Dijkstra:");
        System.out.println("	Number of nodes explored: " + number_tries);
        System.out.println("	Total time of the path: " + graph.vertexlist.get(end).timeFromSource);
        LinkedList<Integer> path=new LinkedList<Integer>();
        int current = end;
        //TODO: remplir la liste path avec le chemin
        while (current != -1) {
            path.addFirst(current);
            current = graph.vertexlist.get(current).prev != null ? graph.vertexlist.get(current).prev.num : -1;
        }

        board.addPath(graph, path);
        return path;
    }

    //M�thode principale
    public static void main(String[] args) {
        //Lecture de la carte et cr�ation du graphe
        try {
            //TODO: obtenir le fichier qui d�crit la carte
            File myObj = new File("PartieB/src/graph.txt");
            Scanner myReader = new Scanner(myObj);
            String data = "";
            //On ignore les deux premi�res lignes
            for (int i=0; i < 3; i++)
                data = myReader.nextLine();

            //Lecture du nombre de lignes
            int nlines = Integer.parseInt(data.split("=")[1]);
            //Et du nombre de colonnes
            data = myReader.nextLine();
            int ncols = Integer.parseInt(data.split("=")[1]);

            //Initialisation du graphe
            Graph graph = new Graph();

            HashMap<String, Integer> groundTypes = new HashMap<String, Integer>();
            HashMap<Integer, String> groundColor = new HashMap<Integer, String>();
            data = myReader.nextLine();
            data = myReader.nextLine();
            //Lire les diff�rents types de cases
            while (!data.equals("==Graph=="))
            {
                String name = data.split("=")[0];
                int time = Integer.parseInt(data.split("=")[1]);
                data = myReader.nextLine();
                String color = data;
                groundTypes.put(name, time);
                groundColor.put(time, color);
                data = myReader.nextLine();
            }

            //On ajoute les sommets dans le graphe (avec le bon type)
            for (int line=0; line < nlines; line++)
            {
                data = myReader.nextLine();
                for (int col=0; col < ncols; col++)
                {
                    graph.addVertex(groundTypes.get(String.valueOf(data.charAt(col))));
                }
            }

            //TODO: ajouter les arr�tes
            for (int line = 0; line < nlines; line++) {
                for (int col = 0; col < ncols; col++) {
                    int source = line * ncols + col;
                    double sourceTime = graph.vertexlist.get(source).indivTime;
            
                    // Connexion aux voisins directs
                    if (col < ncols - 1) { // À droite
                        int dest = line * ncols + (col + 1);
                        double destTime = graph.vertexlist.get(dest).indivTime;
                        double weight = (sourceTime + destTime) / 2;
                        graph.addEgde(source, dest, weight);
                        graph.addEgde(dest, source, weight);
                    }
                    if (line < nlines - 1) { // En bas
                        int dest = (line + 1) * ncols + col;
                        double destTime = graph.vertexlist.get(dest).indivTime;
                        double weight = (sourceTime + destTime) / 2;
                        graph.addEgde(source, dest, weight);
                        graph.addEgde(dest, source, weight);
                    }
            
                    // Connexion aux voisins diagonaux
                    if (line < nlines - 1 && col < ncols - 1) { // Diagonale en bas à droite
                        int dest = (line + 1) * ncols + (col + 1);
                        double destTime = graph.vertexlist.get(dest).indivTime;
                        double weight = Math.sqrt(2) * (sourceTime + destTime) / 2;
                        graph.addEgde(source, dest, weight);
                        graph.addEgde(dest, source, weight);
                    }
                    if (line < nlines - 1 && col > 0) { // Diagonale en bas à gauche
                        int dest = (line + 1) * ncols + (col - 1);
                        double destTime = graph.vertexlist.get(dest).indivTime;
                        double weight = Math.sqrt(2) * (sourceTime + destTime) / 2;
                        graph.addEgde(source, dest, weight);
                        graph.addEgde(dest, source, weight);
                    }
                }
            }

            //On obtient les noeuds de d�part et d'arriv�
            data = myReader.nextLine();
            data = myReader.nextLine();
            int startV = Integer.parseInt(data.split("=")[1].split(",")[0]) * ncols + Integer.parseInt(data.split("=")[1].split(",")[1]);
            data = myReader.nextLine();
            int endV = Integer.parseInt(data.split("=")[1].split(",")[0]) * ncols + Integer.parseInt(data.split("=")[1].split(",")[1]);

            myReader.close();

            //A changer pour avoir un affichage plus ou moins grand
            int pixelSize = 10;
            Board board = new Board(graph, pixelSize, ncols, nlines, groundColor, startV, endV);
            drawBoard(board, nlines, ncols, pixelSize);
            board.repaint();

            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                System.out.println("stop");
            }

            //On appelle Dijkstra
            //LinkedList<Integer> path = Dijkstra(graph, startV, endV, nlines*ncols, board);

            //TODO: laisser le choix entre Dijkstra et A*

            // boolean useAStar = true; // Changez cette valeur à "true" ou "false" pour utiliser `Dijkstra` ou `A*` 

            // LinkedList<Integer> path;
            // if (useAStar) {
            //     path = AStar(graph, startV, endV, ncols, nlines * ncols, board);
            // } else {
            //     path = Dijkstra(graph, startV, endV, nlines * ncols, board);
            // }

            Scanner scanner = new Scanner(System.in);

            // Demander à l'utilisateur de choisir un algorithme
            System.out.println("Veuillez choisir un algorithme :");
            System.out.println("1. A*");
            System.out.println("2. Dijkstra");
            System.out.print("Entrez votre choix (1 ou 2) : ");

            int choix = scanner.nextInt();
            boolean useAStar;

            // Validation du choix
            if (choix == 1) {
                useAStar = true;
                System.out.println("Vous avez choisi A*.");
            } else if (choix == 2) {
                useAStar = false;
                System.out.println("Vous avez choisi Dijkstra.");
            } else {
                System.out.println("Choix invalide. Veuillez relancer le programme.");
                return; // Quitter le programme si le choix est invalide
            }


            // Capture du temps avant l'exécution de l'algorithme
            long startTime = System.nanoTime();

            // Appel de l'algorithme choisi
            LinkedList<Integer> path;
            if (useAStar) {
                path = AStar(graph, startV, endV, ncols, nlines * ncols, board);
            } else {
                path = Dijkstra(graph, startV, endV, nlines * ncols, board);
            }

            // Capture du temps après l'exécution
            long endTime = System.nanoTime();

            // Calcul et affichage du temps d'exécution
            double executionTimeInSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.println("Temps d'exécution réel : " + executionTimeInSeconds + " secondes");

            //�criture du chemin dans un fichier de sortie
            try {
                File file = new File("PartieB/src/out.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);

                for (int i: path)
                {
                    bw.write(String.valueOf(i));
                    bw.write('\n');
                }
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}

