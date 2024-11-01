// liste.h
#ifndef LISTE_H
#define LISTE_H

typedef struct point {
    int x;
    int y;
} point;

typedef struct cellule {
    point data;
    struct cellule *prev;
    struct cellule *next;
} cellule;

cellule *NouvCel(point p);
void InsererCellule(int pl, cellule *cel, cellule *liste);
void SupprimeCellule(int pl, cellule *liste);
void Afficher(cellule *liste);

#endif
