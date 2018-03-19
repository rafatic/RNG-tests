package com.company;

public class generator {
    private double m1 = 4294967087.0, m2 = 4294944443L;
    private double x1, x2, x3, y1, y2, y3, xn, yn, un;
    //private double norm = 2.328306549295728e-10;
    private double norm = 1;

    // Default constructor, initializes the seed to '12345'
    public generator()
    {
        x1 = x2 = x3 = y1 = y2 = y3 = 12345L;
    }

    public generator(double seed)
    {
        x1 = x2 = x3 = y1 = y2 = y3 = seed;
    }
    // Reproduit le fonctionnement du générateur à double congruence linéaire "MRG32k3a"
    public double nextDouble()
    {
        int k;
        double p1, p2;


        // Premier terme
        p1 = 1403580 * x2 - 810728 * x3;
        k = (int)(p1 / m1);
        p1 -= k * m1;
        if(p1 < 0)
        {
            p1 += m1;
        }

        x3 = x2;
        x2 = x1;
        x1 = p1;



        // Second terme
        p2 = 527612 * y1 - 1370589 * y3;
        k = (int)(p2 / m2);
        p2 -= k * m2;

        if(p2 < 0)
        {
            p2 += m2;
        }

        y3 = y2;
        y2 = y1;
        y1 = p2;


        // Combinaison
        if(p1 <= p2)
        {
            return ((p1 - p2 + m1) * norm);
        }
        else
        {
            return ((p1 - p2) * norm);
        }
    }
}
