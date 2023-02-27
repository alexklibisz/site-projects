package com.alexklibisz;

public interface VectorOperations {
    // https://en.wikipedia.org/wiki/Cosine_similarity
    double cosineSimilarity(float[] v1, float[] v2);

    // https://en.wikipedia.org/wiki/Dot_product
    double dotProduct(float[] v1, float[] v2);

    // https://en.wikipedia.org/wiki/Taxicab_geometry
    double l1Distance(float[] v1, float[] v2);

    // https://en.wikipedia.org/wiki/Euclidean_distance
    double l2Distance(float[] v1, float[] v2);
}
