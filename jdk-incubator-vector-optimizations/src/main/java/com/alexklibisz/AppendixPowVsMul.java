package com.alexklibisz;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class AppendixPowVsMul {

    private final VectorSpecies<Float> species = FloatVector.SPECIES_PREFERRED;

    public double pow(float[] v1, float[] v2) {
        double sumSqrDiff = 0f;
        int bound = species.loopBound(v1.length);
        FloatVector pv1, pv2, pv3;
        for (int i = 0; i < bound; i+= species.length()) {
            pv1 = FloatVector.fromArray(species, v1, i);
            pv2 = FloatVector.fromArray(species, v2, i);
            pv3 = pv1.sub(pv2);
            sumSqrDiff += pv3.pow(2).reduceLanes(VectorOperators.ADD);
        }
        return Math.sqrt(sumSqrDiff);
    }

    public double mul(float[] v1, float[] v2) {
        double sumSqrDiff = 0f;
        int bound = species.loopBound(v1.length);
        FloatVector pv1, pv2, pv3;
        for (int i = 0; i < bound; i+= species.length()) {
            pv1 = FloatVector.fromArray(species, v1, i);
            pv2 = FloatVector.fromArray(species, v2, i);
            pv3 = pv1.sub(pv2);
            sumSqrDiff += pv3.mul(pv3).reduceLanes(VectorOperators.ADD);
        }
        return Math.sqrt(sumSqrDiff);
    }
}
