// liste.c
#include <stdio.h>
#include <stdlib.h>
#include "liste.h"

cellule *NouvCel(point p) {
    cellule *new_cell = (cellule *)malloc(sizeof(cellule));
    if (new_cell == NULL) {
        printf("Erreur d'allocation mémoire\n");
        return NULL;
    }
    new_cell->data = p;
    new_cell->prev = NULL;
    new_cell->next = NULL;
    return new_cell;
}

void InsererCellule(int pl, cellule *cel, cellule *liste) {
    cellule *current = liste;
    for (int i = 0; i < pl && current != NULL; i++) {
        current = current->next;
    }
    if (current == NULL) {
        printf("Position invalide\n");
        return;
    }
    cel->next = current->next;
    cel->prev = current;
    if (current->next != NULL) {
        current->next->prev = cel;
    }
    current->next = cel;
}

void SupprimeCellule(int pl, cellule *liste) {
    cellule *current = liste;
    for (int i = 0; i < pl && current != NULL; i++) {
        current = current->next;
    }
    if (current == NULL) {
        printf("Position invalide\n");
        return;
    }
    if (current->prev != NULL) {
        current->prev->next = current->next;
    }
    if (current->next != NULL) {
        current->next->prev = current->prev;
    }
    free(current);
}

void Afficher(cellule *liste) {
    cellule *current = liste;
    while (current != NULL) {
        printf("Point (%d, %d)\n", current->data.x, current->data.y);
        current = current->next;
    }
}
