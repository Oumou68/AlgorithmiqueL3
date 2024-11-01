// arbre.c
#include <stdio.h>
#include <stdlib.h>
#include "arbre.h"

static int compteur = 0;  // Compteur global pour numérotation

noeud *nouvNoeud(char carac) {
    noeud *n = (noeud *)malloc(sizeof(noeud));
    if (n == NULL) {
        printf("Erreur d'allocation mémoire\n");
        return NULL;
    }
    n->val = carac;
    n->num = compteur++;
    n->filsG = NULL;
    n->filsD = NULL;
    return n;
}

noeud *rechercheNoeud(noeud *n, int num) {
    if (n == NULL) return NULL;
    if (n->num == num) return n;

    noeud *result = rechercheNoeud(n->filsG, num);
    if (result != NULL) return result;
    return rechercheNoeud(n->filsD, num);
}

void insererFG(noeud *racine, noeud *new_noeud, int num) {
    noeud *parent = rechercheNoeud(racine, num);
    if (parent == NULL) {
        printf("Nœud avec le numéro %d non trouvé\n", num);
        return;
    }
    new_noeud->filsG = parent->filsG;
    parent->filsG = new_noeud;
}

// Fonction récursive pour générer un arbre binaire synthétique
noeud *creerArbreRecursif(int profondeur) {
    static char val = 'A';  // Lettre initiale
    if (profondeur <= 0) return NULL;

    // noeud *n = nouvNoeud(val);  // Crée un nœud avec la valeur spécifiée
    noeud *n = nouvNoeud(val++);  // Utilise une lettre unique pour chaque nœud et l'incrémente
    // n->filsG = creerArbreRecursif(val + 1, profondeur - 1); // Fils gauche avec profondeur - 1
    n->filsG = creerArbreRecursif(profondeur - 1); // Fils gauche avec profondeur - 1
    // n->filsD = creerArbreRecursif(val + 2, profondeur - 1); // Fils droit avec profondeur - 1
    n->filsD = creerArbreRecursif(profondeur - 1); // Fils droit avec profondeur - 1

    return n;
}

// Parcours préfixe (racine, gauche, droite)
void parcoursPrefixe(noeud *n) {
    if (n == NULL) return;
    printf("%c ", n->val);
    parcoursPrefixe(n->filsG);
    parcoursPrefixe(n->filsD);
}

// Parcours infixe (gauche, racine, droite)
void parcoursInfixe(noeud *n) {
    if (n == NULL) return;
    parcoursInfixe(n->filsG);
    printf("%c ", n->val);
    parcoursInfixe(n->filsD);
}

// Parcours postfixe (gauche, droite, racine)
void parcoursPostfixe(noeud *n) {
    if (n == NULL) return;
    parcoursPostfixe(n->filsG);
    parcoursPostfixe(n->filsD);
    printf("%c ", n->val);
}
