package com.company;

public class generator {
    private final double norm = 2.328306549295728e-10;
    private final double m1 = 4294967087.0;
    private final double m2 = 4294944443.0;
    private final double a12 = 1403580.0;
    private final double a13n = 810728.0;
    private final double a21 = 527612.0;
    private final double a23n = 1370589.0;
    
    private double x1, x2, x3, y1, y2, y3, xn, yn, un;
    
    public generator()
    {
        x1 = x2 = x3 = y1 = y2 = y3 = 12345L;
    }
    
    public generator(double seed)
    {
        x1 = x2 = x3 = y1 = y2 = y3 = seed;
    }
   
    public double nextDouble()
    {
        long k;
        double p1, p2;

        // Premier terme
        p1 = a12 * x2 - a13n * x3;
        k = (long) (p1 / m1);
        p1 -= k * m1;
        if(p1 < 0) {
            p1 += m1;
        }
        x3 = x2;
        x2 = x1;
        x1 = p1;

        // Second terme
        p2 = a21 * y1 - a23n * y3;
        k = (long)(p2 / m2);
        p2 -= k * m2;
        if(p2 < 0) {
            p2 += m2;
        }
        y3 = y2;
        y2 = y1;
        y1 = p2;

        // Combinaison
        if(p1 <= p2) {
            return ((p1 - p2 + m1) * norm);
        }
        else {
            return ((p1 - p2) * norm);
        }
    }
}