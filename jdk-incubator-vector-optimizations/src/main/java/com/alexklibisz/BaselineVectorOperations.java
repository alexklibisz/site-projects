package com.alexklibisz;

public class BaselineVectorOperations implements VectorOperations {

    public double cosineSimilarity(float[] v1, float[] v2) {
        double dotProd = 0.0;
        double v1SqrSum = 0.0;
        double v2SqrSum = 0.0;
        for (int i = 0; i < v1.length; i++) {
            dotProd += v1[i] * v2[i];
            v1SqrSum += Math.pow(v1[i], 2);
            v2SqrSum += Math.pow(v2[i], 2);
        }
        return dotProd / (Math.sqrt(v1SqrSum) * Math.sqrt(v2SqrSum));
    }

    public double dotProduct(float[] v1, float[] v2) {
        float dotProd = 0f;
        for (int i = 0; i < v1.length; i++) dotProd += v1[i] * v2[i];
        return dotProd;
    }

    public double l1Distance(float[] v1, float[] v2) {
        double sumAbsDiff = 0.0;
        for (int i = 0; i < v1.length; i++) sumAbsDiff += Math.abs(v1[i] - v2[i]);
        return sumAbsDiff;
    }

    public double l2Distance(float[] v1, float[] v2) {
        double sumSqrDiff = 0.0;
        for (int i = 0; i < v1.length; i++) sumSqrDiff += Math.pow(v1[i] - v2[i], 2);
        return Math.sqrt(sumSqrDiff);
    }
}
