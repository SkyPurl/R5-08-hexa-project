package org.iut.mastermind.domain.proposition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;

public class Reponse {
    private final String motSecret;
    private final List<Lettre> resultat = new ArrayList<>();

    public Reponse(String mot) {
        this.motSecret = mot;
    }

    // On récupère la lettre à la position dans le résultat
    public Lettre lettre(int position) {
        return resultat.get(position);
    }

    // On construit le résultat en analysant chaque lettre du mot proposé
    public void compare(String essai) {
        resultat.clear(); // On vide le résultat avant de le remplir à nouveau
        resultat.addAll(
                java.util.stream.IntStream.range(0, essai.length())
                        .mapToObj(i -> evaluationCaractere(essai.charAt(i), i))
                        .collect(Collectors.toList()) // On collecte les résultats
        );
    }

    // Si toutes les lettres sont placées
    public boolean lettresToutesPlacees() {
        return resultat.stream().allMatch(Lettre.PLACEE::equals);
    }

    public List<Lettre> lettresResultat() {
        return unmodifiableList(resultat);
    }

    // Renvoie le statut du caractère en tenant compte de sa position
    private Lettre evaluationCaractere(char carCourant, int position) {
        if (estPresent(carCourant)) {
            if (estPlace(carCourant, position)) {
                return Lettre.PLACEE;
            } else {
                return Lettre.NON_PLACEE;
            }
        } else {
            return Lettre.INCORRECTE;
        }
    }

    // Le caractère est présent dans le mot secret
    private boolean estPresent(char carCourant) {
        return motSecret.indexOf(carCourant) != -1;
    }

    // Le caractère est placé dans le mot secret à la bonne position
    private boolean estPlace(char carCourant, int position) {
        return motSecret.charAt(position) == carCourant;
    }
}
