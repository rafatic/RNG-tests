package com.company.test;

import com.company.generator;

/**
 *
 * @author Benjamin
 */
public class Khi2 {
    private static final int ITERATIONS = 1000;
    private static final int K = 9;
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
    
    public static void main(String[] args) {
        double[] nombreLancers = lancer();
        double[] nombreAttendu = lancerAttendu();
        
        display(nombreLancers, nombreAttendu);
        testAdequation(nombreLancers, nombreAttendu);
    }
    
    public static void display(double[] nombreLancers, double[] nombreAttendu) {
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
    
    public static void testAdequation(double[] nombreLancers, double[] nombreAttendu) {
        ValeursKhi2 valeursKhi2 = new ValeursKhi2();
        double T = 0;
        
        for (int i=0; i < K; i++) {
            T += Math.pow(nombreLancers[i] - nombreAttendu[i], 2) / nombreAttendu[i];
        }
        
        System.out.println("T = " + T);
        
        for (int i=0; i < valeursKhi2.getArraySize(); i++) {
            System.out.format("TEST: P(T < " + valeursKhi2.getKhi2Value(K, i) + ") = %.3f%n", (1-valeursKhi2.getAlpha(i)));
            
            System.out.format("    %.3f < " + valeursKhi2.getKhi2Value(K, i) +" : ", T);
        
            if (T < valeursKhi2.getKhi2Value(K, i)) {
                System.out.println("VRAI, les dés ne sont pas truqués");
            } else {
                System.out.println("FAUX, les dés sont truqués");
            }
        }
    }
    
    public static double[] lancerAttendu() {
        double[] nombreAttendu = new double[K];
        
        for (int i=0; i < K; i++) {
            nombreAttendu[i] = PROBASLANCERS[i] * ITERATIONS;
        }
        
        return nombreAttendu;
    }
    
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