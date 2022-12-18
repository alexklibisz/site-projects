package math

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

// https://www.vedantu.com/maths/properties-of-addition
class AddSpec extends AnyFreeSpec with Matchers {
  "addition" - {
    "satisfies the commutative property" in {
      Add.add(2, 3) shouldBe Add.add(3, 2)
    }
    "satisfies the associative property" in {
      Add.add(1, Add.add(2, 3)) shouldBe Add.add(Add.add(1, 2), 3)
    }
    "satisfies the distributive property" in {
      (3 * Add.add(1, 2)) shouldBe (Add.add(3 * 1, 3 * 2))
    }
    "satisfies the identity property" in {
      Add.add(0, 5) shouldBe Add.add(5, 0)
    }
  }
}
