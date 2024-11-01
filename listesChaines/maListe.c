// maListe.c
#include <stdio.h>
#include "liste.h"

int main() {
    cellule *liste = NULL;
    int choix, x, y, position;

    while (1) {
        printf("\nMenu:\n");
        printf("1. Ajouter un point\n");
        printf("2. Supprimer un point\n");
        printf("3. Afficher la liste\n");
        printf("4. Quitter\n");
        printf("Choisissez une option: ");
        scanf("%d", &choix);

        if (choix == 1) {
            printf("Entrez les coordonnées du point (x y): ");
            scanf("%d %d", &x, &y);
            point p = {x, y};
            cellule *new_cell = NouvCel(p);
            if (liste == NULL) {
                liste = new_cell;  // Première insertion
            } else {
                printf("Entrez la position où insérer (0 pour début): ");
                scanf("%d", &position);
                InsererCellule(position, new_cell, liste);
            }
        } 
        else if (choix == 2) {
            if (liste == NULL) {
                printf("La liste est vide!\n");
                continue;
            }
            printf("Entrez la position du point à supprimer: ");
            scanf("%d", &position);
            SupprimeCellule(position, liste);
        } 
        else if (choix == 3) {
            printf("Liste des points:\n");
            Afficher(liste);
        } 
        else if (choix == 4) {
            printf("Au revoir!\n");
            break;
        } 
        else {
            printf("Choix invalide, veuillez réessayer.\n");
        }
    }

    return 0;
}
