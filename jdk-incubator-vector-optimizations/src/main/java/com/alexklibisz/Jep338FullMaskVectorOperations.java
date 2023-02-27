package com.alexklibisz;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class Jep338FullMaskVectorOperations implements VectorOperations{

    private final VectorSpecies<Float> species = FloatVector.SPECIES_PREFERRED;

    public double cosineSimilarity(float[] v1, float[] v2) {
        double dotProd = 0.0;
        double v1SqrSum = 0.0;
        double v2SqrSum = 0.0;
        int bound = species.loopBound(v1.length);
        FloatVector pv1, pv2;
        for (int i = 0; i < bound; i += species.length()) {
            VectorMask<Float> m = species.indexInRange(i, v1.length);
            pv1 = FloatVector.fromArray(species, v1, i, m);
            pv2 = FloatVector.fromArray(species, v2, i, m);
            dotProd += pv1.mul(pv2).reduceLanes(VectorOperators.ADD);
            v1SqrSum += pv1.mul(pv1).reduceLanes(VectorOperators.ADD);
            v2SqrSum += pv2.mul(pv2).reduceLanes(VectorOperators.ADD);
        }
        return dotProd / (Math.sqrt(v1SqrSum) * Math.sqrt(v2SqrSum));
    }

    public double dotProduct(float[] v1, float[] v2) {
        double dp = 0f;
        FloatVector pv1, pv2;
        for (int i = 0; i < v1.length; i += species.length()) {
            VectorMask<Float> m = species.indexInRange(i, v1.length);
            pv1 = FloatVector.fromArray(species, v1, i, m);
            pv2 = FloatVector.fromArray(species, v2, i, m);
            dp += pv1.mul(pv2).reduceLanes(VectorOperators.ADD);
        }
        return dp;
    }

    public double l1Distance(float[] v1, float[] v2) {
        double sumAbsDiff = 0.0;
        FloatVector pv1, pv2;
        for (int i = 0; i < v1.length; i += species.length()) {
            VectorMask<Float> m = species.indexInRange(i, v1.length);
            pv1 = FloatVector.fromArray(species, v1, i, m);
            pv2 = FloatVector.fromArray(species, v2, i, m);
            sumAbsDiff += pv1.sub(pv2).abs().reduceLanes(VectorOperators.ADD);
        }
        return sumAbsDiff;
    }

    public double l2Distance(float[] v1, float[] v2) {
        double sumSqrDiff = 0f;
        FloatVector pv1, pv2, pv3;
        for (int i = 0; i < v1.length; i+= species.length()) {
            VectorMask<Float> m = species.indexInRange(i, v1.length);
            pv1 = FloatVector.fromArray(species, v1, i, m);
            pv2 = FloatVector.fromArray(species, v2, i, m);
            pv3 = pv1.sub(pv2);
            // For some unknown reason, pv3.mul(pv3) is significantly faster than pv3.pow(2).
            sumSqrDiff += pv3.mul(pv3).reduceLanes(VectorOperators.ADD);
        }
        return Math.sqrt(sumSqrDiff);
    }
}