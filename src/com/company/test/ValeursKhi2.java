package com.company.test;

/**
 * Classe pour gérer les valeurs de Khi2
 */
public class ValeursKhi2 {
    /**
     * Valeur des Khi2 en fonction du risque et des degrés de liberté
     */
    private final double[][] valeurKhi2 = {
        { 0.004, 0.02, 0.06, 0.15, 0.46,  1.07,  1.64,  2.71,  3.84,  6.63, 10.83 }, //  1 degré liberté
        {  0.10, 0.21, 0.45, 0.71, 1.39,  2.41,  3.22,  4.61,  5.99,  9.21, 13.82 }, //  2 degrés liberté
        {  0.35, 0.58, 1.01, 1.42, 2.37,  3.66,  4.64,  6.25,  7.81, 11.34, 16.27 }, //  3 degrés liberté
        {  0.71, 1.06, 1.65, 2.20, 3.36,  4.88,  5.99,  7.78,  9.49, 13.28, 18.47 }, //  4 degrés liberté
        {  1.14, 1.61, 2.34, 3.00, 4.35,  6.06,  7.29,  9.24, 11.07, 15.09, 20.52 }, //  5 degrés liberté
        {  1.63, 2.20, 3.07, 3.83, 5.35,  7.23,  8.56, 10.64, 12.59, 16.81, 22.46 }, //  6 degrés liberté
        {  2.17, 2.83, 3.82, 4.67, 6.35,  8.38,  9.80, 12.02, 14.07, 18.48, 24.32 }, //  7 degrés liberté
        {  2.73, 3.49, 4.59, 5.53, 7.34,  9.52, 11.03, 13.36, 15.51, 20.09, 26.12 }, //  8 degrés liberté
        {  3.32, 4.17, 5.38, 6.39, 8.34, 10.66, 12.24, 14.68, 16.92, 21.67, 27.88 }, //  9 degrés liberté
        {  3.94, 4.87, 6.18, 7.27, 9.34, 11.78, 13.44, 15.99, 18.31, 23.21, 29.59 }, // 10 degrés liberté
        {  4.57, 5.58, 6.99, 8.15, 10.3,  12.9,  14.6,  17.3,  19.7,  24.7,  31.3 }, // 11 degrés liberté
        {  5.23, 6.30, 7.81, 9.03, 11.3,  14.0,  15.8,  18.5,  21.0,  26.2,  32.9 }, // 12 degrés liberté
        {  5.89, 7.04, 8.63, 9.93, 12.3,  15.1,  17.0,  19.8,  22.4,  27.7,  34.5 }, // 13 degrés liberté
        {  6.57, 7.79, 9.47, 10.8, 13.3,  16.2,  18.2,  21.1,  23.7,  29.1,  36.1 }, // 14 degrés liberté
        {  7.26, 8.55, 10.3, 11.7, 14.3,  17.3,  19.3,  22.3,  25.0,  30.6,  37.7 }  // 15 degrés liberté
    };
    
    /**
     * Valeurs du risque
     */
    private final double[] alphaValue = {
        0.95, 0.9, 0.8, 0.7, 0.5, 0.3, 0.2, 0.1, 0.05, 0.01, 0.001
    };
    
    /**
     * Récupère la valeur du Khi2 en fonction du degré de liberté et du risque
     * 
     * @param degreLiberte Le degré de liberté de notre série de données
     * @param indiceAlpha L'indice du tableau correspondant au risque choisit
     * @return La valeur du Khi2 correspondante
     */
    public double getKhi2Value(int degreLiberte, int indiceAlpha) {
        return this.valeurKhi2[degreLiberte-1][indiceAlpha];
    }
    
    /**
     * Récupère le risque en fonction d'un indice du tableau
     * 
     * @param indiceAlpha Un indice du tableau des valeurs du risque
     * @return La valeur de ce risque
     */
    public double getAlpha(int indiceAlpha) {
        return this.alphaValue[indiceAlpha];
    }
    
    /**
     * Retourne la taille du tableau de risque qui correspond au
     * nombre de risques dont les valeurs du Khi2 ont été défini
     * 
     * @return La taille du tableau des risques
     */
    public int getArraySize() {
        return this.alphaValue.length;
    }
}