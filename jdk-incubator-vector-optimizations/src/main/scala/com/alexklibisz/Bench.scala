package com.alexklibisz

import org.openjdk.jmh.annotations._

import scala.util.Random

@State(Scope.Benchmark)
class BenchFixtures {
  implicit private val rng: Random = new Random(0)
  private val length = 999
  val v1: Array[Float] = (0 until length).map(_ => rng.nextFloat()).toArray
  val v2: Array[Float] = (0 until length).map(_ => rng.nextFloat()).toArray
  val baseline = new BaselineVectorOperations
  val fma = new FmaVectorOperations
  val jep338FullMask = new Jep338FullMaskVectorOperations()
  val jep338TailLoop = new Jep338TailLoopVectorOperations()
  val jep338TailMask = new Jep338TailMaskVectorOperations()
}

class Bench {

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def cosineSimilarityBaseline(f: BenchFixtures): Unit =
    f.baseline.cosineSimilarity(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def cosineSimilarityFma(f: BenchFixtures): Unit =
    f.fma.cosineSimilarity(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def cosineSimilarityJep338FullMask(f: BenchFixtures): Unit =
    f.jep338FullMask.cosineSimilarity(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def cosineSimilarityJep338TailLoop(f: BenchFixtures): Unit =
    f.jep338TailLoop.cosineSimilarity(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def cosineSimilarityJep338TailMask(f: BenchFixtures): Unit =
    f.jep338TailMask.cosineSimilarity(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def dotProductBaseline(f: BenchFixtures): Unit =
    f.baseline.dotProduct(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def dotProductFma(f: BenchFixtures): Unit =
    f.fma.dotProduct(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def dotProductJep338FullMask(f: BenchFixtures): Unit =
    f.jep338FullMask.dotProduct(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def dotProductJep338TailLoop(f: BenchFixtures): Unit =
    f.jep338TailLoop.dotProduct(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def dotProductJep338TailMask(f: BenchFixtures): Unit =
    f.jep338TailMask.dotProduct(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def l1DistanceBaseline(f: BenchFixtures): Unit =
    f.baseline.l1Distance(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def l1DistanceFma(f: BenchFixtures): Unit =
    f.fma.l1Distance(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def l1DistanceJep338FullMask(f: BenchFixtures): Unit =
    f.jep338FullMask.l1Distance(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def l1DistanceJep338TailLoop(f: BenchFixtures): Unit =
    f.jep338TailLoop.l1Distance(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def l1DistanceJep338TailMask(f: BenchFixtures): Unit =
    f.jep338TailMask.l1Distance(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def l2DistanceBaseline(f: BenchFixtures): Unit =
    f.baseline.l2Distance(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def l2DistanceFma(f: BenchFixtures): Unit =
    f.fma.l2Distance(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def l2DistanceJep338FullMask(f: BenchFixtures): Unit =
    f.jep338FullMask.l2Distance(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def l2DistanceJep338TailLoop(f: BenchFixtures): Unit =
    f.jep338TailLoop.l2Distance(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def l2DistanceJep338TailMask(f: BenchFixtures): Unit =
    f.jep338TailMask.l2Distance(f.v1, f.v2)

}
