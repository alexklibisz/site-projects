package com.alexklibisz

import jdk.incubator.vector.{FloatVector, VectorSpecies}
import org.openjdk.jmh.annotations._

import java.lang
import scala.util.Random

@State(Scope.Benchmark)
class BenchPowVsMulFixtures {
  implicit private val rng: Random = new Random(0)
  val species: VectorSpecies[lang.Float] = FloatVector.SPECIES_PREFERRED
  val v: Array[Float] = (0 until species.length()).map(_ => rng.nextFloat()).toArray
  val fv: FloatVector = FloatVector.fromArray(species, v, 0)
}

class BenchPowVsMul {

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def pow(f: BenchPowVsMulFixtures): Unit = f.fv.pow(2)

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @Fork(value = 1)
  @Warmup(time = 5, iterations = 3)
  @Measurement(time = 5, iterations = 6)
  def mul(f: BenchPowVsMulFixtures): Unit = f.fv.mul(f.fv)
}
