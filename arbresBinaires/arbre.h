// arbre.h
#ifndef ARBRE_H
#define ARBRE_H

typedef struct noeud {
    char val;
    int num;
    struct noeud *filsG;
    struct noeud *filsD;
} noeud;

noeud *nouvNoeud(char carac);
noeud *rechercheNoeud(noeud *n, int num);
void insererFG(noeud *racine, noeud *new_noeud, int num);
void parcoursPrefixe(noeud *n);     // Parcours préfixe
void parcoursInfixe(noeud *n);      // Parcours infixe
void parcoursPostfixe(noeud *n);    // Parcours postfixe
// noeud *creerArbreRecursif(char val, int profondeur); 
noeud *creerArbreRecursif(int profondeur); 

#endif
