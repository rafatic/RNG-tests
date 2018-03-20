package com.company.test;

import com.company.generator;

/**
 * Classe de test pour notre générateur de nombres aléatoires
 */
public class Khi2 {
    /**
     * Le nombre d'itérations pour le test
     */
    private static final int ITERATIONS = 1000;
    
    /**
     * Le nombre de valeurs possibles pendant le test
     */
    private static final int K = 9;
    
    /**
     * Les probabilités connues d'avoir un résultat donné
     */
    private static final double[] PROBASLANCERS = {
        1.0 / 12.0, // 2 et 3
        1.0 / 12.0, // 4
        1.0 /  9.0, // 5
        5.0 / 36.0, // 6
        1.0 /  6.0, // 7
        5.0 / 36.0, // 8
        1.0 /  9.0, // 9
        1.0 / 12.0, // 10
        1.0 / 12.0  // 11 et 12
    };
    
    /**
     * Fonction de départ
     * 
     * @param args Les arguments donnés en ligne de commande
     */
    public static void main(String[] args) {
        double[] nombreLancers = lancer();
        double[] nombreAttendu = lancerAttendu();
        
        display(nombreLancers, nombreAttendu);
        testAdequation(nombreLancers, nombreAttendu);
    }
    
    /**
     * Affiche le nombre de fois qu'on a obtenu certaines valeurs
     * ainsi que le nombre de fois qu'on devrait avoir en théorie
     * 
     * @param nombreLancers Nombre de fois qu'on a obtenu certaines valeurs
     * @param nombreAttendu Nombre de fois qu'on devrait obtenir certaines valeurs
     */
    public static void display(double[] nombreLancers, double[] nombreAttendu) {
        System.out.println("Nombre d'itérations : " + ITERATIONS);
        System.out.println("        +-------------------+------------------+");
        System.out.println("        | Lancers effectués | Lancers attendus |");
        System.out.println("+-------+-------------------+------------------+");
        
        for (int i=0; i < K; i++) {
            switch (i) {
                case 0:
                    System.out.print("|   2-3 | ");
                    break;
                case K-1:
                    System.out.print("| 11-12 | ");
                    break;
                default:
                    System.out.format("| %5d | ", i+4);
                    break;
            }
            
            System.out.format("%17.0f | %16.0f |%n", nombreLancers[i], nombreAttendu[i]);
        }
        
        System.out.println("+-------+-------------------+------------------+\n");
    }
    
    /**
     * Test de notre générateur de nombres aléatoires à l'aide du Khi2
     * 
     * @param nombreLancers Nombre de fois qu'on a obtenu certaines valeurs
     * @param nombreAttendu Nombre de fois qu'on devrait obtenir certaines valeurs
     */
    public static void testAdequation(double[] nombreLancers, double[] nombreAttendu) {
        ValeursKhi2 valeursKhi2 = new ValeursKhi2();
        double T = 0;
        
        for (int i=0; i < K; i++) {
            T += Math.pow(nombreLancers[i] - nombreAttendu[i], 2) / nombreAttendu[i];
        }
        
        System.out.format("T = %.3f%n", T);
        System.out.println("Test du Khi2 avec un risque à 5%");
        System.out.println("    H0: Les dés ne sont pas truqués");
        System.out.println("    H1: Les dés sont truqués");
        System.out.format("TEST: P(T < " + valeursKhi2.getKhi2Value(K-1, 8) + ") = %.3f%n", (1-valeursKhi2.getAlpha(8)));
        System.out.format("    %.3f < " + valeursKhi2.getKhi2Value(K-1, 8) +" : ", T);
        
        if (T < valeursKhi2.getKhi2Value(K-1, 8)) {
            System.out.println("VRAI. L'hypothèse H0 est vérifié, les dés ne sont pas truqués");
        } else {
            System.out.println("FAUX. L'hypothèse H1 est vérifié, les dés sont truqués");
        }
    }
    
    /**
     * Donne le nombre de lancers qu'on devrait avoir en
     * fonctions des probabilités qu'on a de les avoir
     * 
     * @return Nombre de fois qu'on devrait obtenir certaines valeurs
     */
    public static double[] lancerAttendu() {
        double[] nombreAttendu = new double[K];
        
        for (int i=0; i < K; i++) {
            nombreAttendu[i] = PROBASLANCERS[i] * ITERATIONS;
        }
        
        return nombreAttendu;
    }
    
    /**
     * Effectue des lancers de dés sur un certain nombre d'itération
     * à chaque itération on lance 2 dé et on calcule la somme des valeurs
     * les résultat pour 2 et 3 ainsi que pour 11 et 12 sont fusionnés
     * 
     * @return Nombre de fois qu'on a obtenu certaines valeurs
     */
    public static double[] lancer() {
        generator gen = new generator(System.currentTimeMillis());
        double[] resultatLancers = new double[K];
        
        for (int i=0; i < ITERATIONS; i++) {
            int dice1 = (int) (gen.nextDouble() * 6) % 6;
            int dice2 = (int) (gen.nextDouble() * 6) % 6;
            
            if (dice1 + dice2 <= 1) {
                resultatLancers[0]++;
            } else if (dice1 + dice2 >= 9) {
                resultatLancers[8]++;
            } else {
                resultatLancers[dice1 + dice2 - 1]++;
            }
        }
        
        return resultatLancers;
    }
}