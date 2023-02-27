package com.alexklibisz;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class Jep338TailLoopVectorOperations implements VectorOperations{

    private final VectorSpecies<Float> species = FloatVector.SPECIES_PREFERRED;

    public double cosineSimilarity(float[] v1, float[] v2) {
        double dotProd = 0.0;
        double v1SqrSum = 0.0;
        double v2SqrSum = 0.0;
        int i = 0;
        int bound = species.loopBound(v1.length);
        FloatVector fv1, fv2;
        for (; i < bound; i += species.length()) {
            fv1 = FloatVector.fromArray(species, v1, i);
            fv2 = FloatVector.fromArray(species, v2, i);
            dotProd += fv1.mul(fv2).reduceLanes(VectorOperators.ADD);
            v1SqrSum += fv1.mul(fv1).reduceLanes(VectorOperators.ADD);
            v2SqrSum += fv2.mul(fv2).reduceLanes(VectorOperators.ADD);
        }
        for (; i < v1.length; i++) {
            dotProd = Math.fma(v1[i], v2[i], dotProd);
            v1SqrSum = Math.fma(v1[i], v1[i], v1SqrSum);
            v2SqrSum = Math.fma(v2[i], v2[i], v2SqrSum);
        }
        return dotProd / (Math.sqrt(v1SqrSum) * Math.sqrt(v2SqrSum));
    }

    public double dotProduct(float[] v1, float[] v2) {
        double dotProd = 0f;
        int i = 0;
        int bound = species.loopBound(v1.length);
        FloatVector fv1, fv2;
        for (; i < bound; i += species.length()) {
            fv1 = FloatVector.fromArray(species, v1, i);
            fv2 = FloatVector.fromArray(species, v2, i);
            dotProd += fv1.mul(fv2).reduceLanes(VectorOperators.ADD);
        }
        for (; i < v1.length; i++) {
            dotProd = Math.fma(v1[i], v2[i], dotProd);
        }
        return dotProd;
    }


    public double l1Distance(float[] v1, float[] v2) {
        double sumAbsDiff = 0.0;
        int i = 0;
        int bound = species.loopBound(v1.length);
        FloatVector fv1, fv2;
        for (; i < bound; i += species.length()) {
            fv1 = FloatVector.fromArray(species, v1, i);
            fv2 = FloatVector.fromArray(species, v2, i);
            sumAbsDiff += fv1.sub(fv2).abs().reduceLanes(VectorOperators.ADD);
        }
        for (; i < v1.length; i++) {
            sumAbsDiff += Math.abs(v1[i] - v2[i]);
        }
        return sumAbsDiff;
    }

    public double l2Distance(float[] v1, float[] v2) {
        double sumSqrDiff = 0f;
        int i = 0;
        int bound = species.loopBound(v1.length);
        FloatVector fv1, fv2, fv3;
        for (; i < bound; i+= species.length()) {
            fv1 = FloatVector.fromArray(species, v1, i);
            fv2 = FloatVector.fromArray(species, v2, i);
            fv3 = fv1.sub(fv2);
            // For some unknown reason, fv3.mul(fv3) is significantly faster than fv3.pow(2).
            sumSqrDiff += fv3.mul(fv3).reduceLanes(VectorOperators.ADD);
        }
        for (; i < v1.length; i++) {
            float diff = v1[i] - v2[i];
            sumSqrDiff = Math.fma(diff, diff, sumSqrDiff);
        }
        return Math.sqrt(sumSqrDiff);
    }
}
