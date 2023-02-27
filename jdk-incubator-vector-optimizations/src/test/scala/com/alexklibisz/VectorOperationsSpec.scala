package com.alexklibisz

import jdk.incubator.vector.FloatVector
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Random

class VectorOperationsSpec extends AnyFreeSpec with Matchers {

  private val seed = System.currentTimeMillis()
  private val rng = new Random(seed)
  info(s"Testing with random seed $seed")

  private def randomVector(length: Int): Array[Float] =
    (1 to length).map(_ => rng.nextFloat()).toArray

  private def checkParity(
    f1: (Array[Float], Array[Float]) => Double,
    f2: (Array[Float], Array[Float]) => Double,
    vectorLength: Int = 999,
    numVectors: Int = 1000,
    tolerance: Double = 0.005): Unit =
    (1 to numVectors)
      .map(_ => (randomVector(vectorLength), randomVector(vectorLength)))
      .foreach {
        case (v1, v2) =>
          val r1 = f1(v1, v2)
          val r2 = f2(v1, v2)
          val err = (r1 - r2).abs / r1.abs.min(r2.abs)
          withClue(s"$r1, $r2, $err") {
            err shouldBe <=(tolerance)
          }
      }

  private val baselineVectorOperations = new BaselineVectorOperations

  "FmaVectorOperations" - {

    val fmaVectorOperations = new FmaVectorOperations

    "dotProduct parity" in {
      checkParity(
        (v1, v2) => fmaVectorOperations.dotProduct(v1, v2),
        (v1, v2) => baselineVectorOperations.dotProduct(v1, v2)
      )
    }

    "cosineSimilarity parity" in {
      checkParity(
        (v1, v2) => fmaVectorOperations.cosineSimilarity(v1, v2),
        (v1, v2) => baselineVectorOperations.cosineSimilarity(v1, v2)
      )
    }

    "l1Distance parity" in {
      checkParity(
        (v1, v2) => fmaVectorOperations.l1Distance(v1, v2),
        (v1, v2) => baselineVectorOperations.l1Distance(v1, v2)
      )
    }

    "l2Distance parity" in {
      checkParity(
        (v1, v2) => fmaVectorOperations.l2Distance(v1, v2),
        (v1, v2) => baselineVectorOperations.l2Distance(v1, v2)
      )
    }
  }

  "Jep338FullMaskVectorOperations" - {

    val jep338FullMaskVectorOperations = new Jep338FullMaskVectorOperations()

    "dotProduct parity" in {
      checkParity(
        (v1, v2) => jep338FullMaskVectorOperations.dotProduct(v1, v2),
        (v1, v2) => baselineVectorOperations.dotProduct(v1, v2)
      )
    }

    "cosineSimilarity parity" in {
      checkParity(
        (v1, v2) => jep338FullMaskVectorOperations.cosineSimilarity(v1, v2),
        (v1, v2) => baselineVectorOperations.cosineSimilarity(v1, v2)
      )
    }

    "l1Distance parity" in {
      checkParity(
        (v1, v2) => jep338FullMaskVectorOperations.l1Distance(v1, v2),
        (v1, v2) => baselineVectorOperations.l1Distance(v1, v2)
      )
    }

    "l2Distance parity" in {
      checkParity(
        (v1, v2) => jep338FullMaskVectorOperations.l2Distance(v1, v2),
        (v1, v2) => baselineVectorOperations.l2Distance(v1, v2)
      )
    }
  }

  "Jep338TailMaskVectorOperations" - {

    val jep338TailMaskVectorOperations = new Jep338TailMaskVectorOperations()

    "dotProduct parity" in {
      checkParity(
        (v1, v2) => jep338TailMaskVectorOperations.dotProduct(v1, v2),
        (v1, v2) => baselineVectorOperations.dotProduct(v1, v2)
      )
    }

    "cosineSimilarity parity" in {
      checkParity(
        (v1, v2) => jep338TailMaskVectorOperations.cosineSimilarity(v1, v2),
        (v1, v2) => baselineVectorOperations.cosineSimilarity(v1, v2)
      )
    }

    "l1Distance parity" in {
      checkParity(
        (v1, v2) => jep338TailMaskVectorOperations.l1Distance(v1, v2),
        (v1, v2) => baselineVectorOperations.l1Distance(v1, v2)
      )
    }

    "l2Distance parity" in {
      checkParity(
        (v1, v2) => jep338TailMaskVectorOperations.l2Distance(v1, v2),
        (v1, v2) => baselineVectorOperations.l2Distance(v1, v2)
      )
    }
  }

  "Jep338TailLoopVectorOperations" - {

    val jep338TailLoopVectorOperations = new Jep338TailLoopVectorOperations()

    "dotProduct parity" in {
      checkParity(
        (v1, v2) => jep338TailLoopVectorOperations.dotProduct(v1, v2),
        (v1, v2) => baselineVectorOperations.dotProduct(v1, v2)
      )
    }

    "cosineSimilarity parity" in {
      checkParity(
        (v1, v2) => jep338TailLoopVectorOperations.cosineSimilarity(v1, v2),
        (v1, v2) => baselineVectorOperations.cosineSimilarity(v1, v2)
      )
    }

    "l1Distance parity" in {
      checkParity(
        (v1, v2) => jep338TailLoopVectorOperations.l1Distance(v1, v2),
        (v1, v2) => baselineVectorOperations.l1Distance(v1, v2)
      )
    }

    "l2Distance parity" in {
      checkParity(
        (v1, v2) => jep338TailLoopVectorOperations.l2Distance(v1, v2),
        (v1, v2) => baselineVectorOperations.l2Distance(v1, v2)
      )
    }
  }
}
