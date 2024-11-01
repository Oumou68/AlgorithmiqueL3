#include <stdio.h>
#include <stdlib.h>

#define MAX 15 // Nombre maximal de sommets, ajustez selon vos besoins

// Fonction pour trier les sommets par degré décroissant
void trier_par_degre(int degre[], int ordre[], int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = i + 1; j < n; j++) {
            if (degre[ordre[i]] < degre[ordre[j]]) {
                int temp = ordre[i];
                ordre[i] = ordre[j];
                ordre[j] = temp;
            }
        }
    }
}

// Fonction pour colorer le graphe avec l'algorithme de Welsh-Powell
void welsh_powell(int **adjacency, int n) {
    int couleur[MAX];  // Tableau pour stocker les couleurs de chaque sommet
    int degre[MAX];    // Tableau pour stocker le degré de chaque sommet
    int ordre[MAX];    // Tableau pour l'ordre des sommets par degré décroissant

    // Initialiser les couleurs à -1 (non coloré)
    for (int i = 0; i < n; i++) {
        couleur[i] = -1;
        degre[i] = 0;
        ordre[i] = i;
    }

    // Calculer le degré de chaque sommet
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (adjacency[i][j] == 1) {
                degre[i]++;
            }
        }
    }

    // Trier les sommets par degré décroissant
    trier_par_degre(degre, ordre, n);

    printf("Ordre de marquage (par degré décroissant) : ");
    for (int i = 0; i < n; i++) {
        printf("%d ", ordre[i]);
    }
    printf("\n");

    // Appliquer l'algorithme de Welsh-Powell
    int couleur_max = 0;
    for (int i = 0; i < n; i++) {
        int sommet = ordre[i];
        int couleurs_utilisees[MAX] = {0}; // Tableau pour vérifier les couleurs utilisées par les voisins

        // Vérifier les couleurs des voisins
        for (int j = 0; j < n; j++) {
            if (adjacency[sommet][j] == 1 && couleur[j] != -1) {
                couleurs_utilisees[couleur[j]] = 1;
            }
        }

        // Attribuer la première couleur disponible
        int c;
        for (c = 0; c < n; c++) {
            if (couleurs_utilisees[c] == 0) {
                couleur[sommet] = c;
                if (c > couleur_max) {
                    couleur_max = c;
                }
                break;
            }
        }
    }

    // Afficher les résultats
    printf("Coloration du graphe :\n");
    for (int i = 0; i < n; i++) {
        printf("Sommet %d -> Couleur %d\n", i, couleur[i]);
    }
    printf("Nombre total de couleurs utilisées : %d\n", couleur_max + 1);
}

// Fonction pour charger le graphe depuis stdin ou un fichier
int **chargeGraphe(int *n) {
    printf("Entrez l'ordre du graphe : ");
    scanf("%d", n);

    // Allocation de la matrice d'adjacence
    int **adjacency = (int **)malloc(*n * sizeof(int *));
    for (int i = 0; i < *n; i++) {
        adjacency[i] = (int *)malloc(*n * sizeof(int));
    }

    // Lecture de la matrice d'adjacence
    printf("Entrez la matrice d'adjacence :\n");
    for (int i = 0; i < *n; i++) {
        for (int j = 0; j < *n; j++) {
            scanf("%d", &adjacency[i][j]);
        }
    }

    return adjacency;
}

// Fonction principale
int main() {
    int n;
    
    // Charger le graphe
    int **adjacency = chargeGraphe(&n);

    // Exécuter l'algorithme de Welsh-Powell
    welsh_powell(adjacency, n);

    // Libération de la mémoire
    for (int i = 0; i < n; i++) {
        free(adjacency[i]);
    }
    free(adjacency);

    return 0;
}
