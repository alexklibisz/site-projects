package math

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class AddExamples extends AnyFreeSpec with Matchers {
  "add two positive integers" in {
    Add.add(1, 1) shouldBe 2
  }
  "add two negative integers" in {
    Add.add(-1, -1) shouldBe -2
  }
  "add one positive and one negative integer" in {
    Add.add(1, -1) shouldBe 0
  }
}
