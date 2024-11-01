// mainArbre.c
#include <stdio.h>
#include "arbre.h"

int main() {
    // noeud *racine = nouvNoeud('B');  // Racine de l'arbre
    // noeud *n1 = nouvNoeud('A');
    // noeud *n2 = nouvNoeud('O');
    // noeud *n3 = nouvNoeud('R');

    // insererFG(racine, n1, racine->num);  // A comme fils gauche de B
    // insererFG(racine, n2, n1->num);      // O comme fils gauche de A
    // insererFG(racine, n3, racine->num);  // R comme fils gauche de B (remplace A)

    // Création de l'arbre synthétique manuellement
    // noeud *racine = nouvNoeud('A');
    // racine->filsG = nouvNoeud('B');
    // racine->filsD = nouvNoeud('C');
    // racine->filsG->filsG = nouvNoeud('D');
    // racine->filsG->filsD = nouvNoeud('E');

    // Création d'un arbre synthétique de profondeur 3
    // noeud *racine = creerArbreRecursif('A', 3);
    noeud *racine = creerArbreRecursif(3);  // Crée un arbre synthétique de profondeur 3


     // Affichage des parcours
    printf("Parcours préfixe de l'arbre : ");
    parcoursPrefixe(racine);  // Affiche l'arbre
    printf("\n");

     printf("Parcours infixe : ");
    parcoursInfixe(racine);
    printf("\n");

    printf("Parcours postfixe : ");
    parcoursPostfixe(racine);
    printf("\n");

    return 0;
}
