package com.alexklibisz

import org.openjdk.jmh.annotations._

import scala.util.Random

@State(Scope.Benchmark)
class BenchPowVsMulFixtures {
  implicit private val rng: Random = new Random(0)
  private val length = 256
  val v1: Array[Float] = (0 until length).map(_ => rng.nextFloat()).toArray
  val v2: Array[Float] = (0 until length).map(_ => rng.nextFloat()).toArray
  val appendixPowVsMul = new AppendixPowVsMul
}

class BenchPowVsMul {

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def pow(f: BenchPowVsMulFixtures): Unit =
    f.appendixPowVsMul.pow(f.v1, f.v2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def mul(f: BenchPowVsMulFixtures): Unit =
    f.appendixPowVsMul.mul(f.v1, f.v2)
}
