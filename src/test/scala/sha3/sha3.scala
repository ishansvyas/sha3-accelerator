package sha3

import chiseltest._

class Sha3AccelTests extends TestWithBackendSelect with ChiselScalatestTester {
  behavior of "Sha3Module"

  it should "do SHA3" in {
    test(new Sha3Accel(64)).withAnnotations(simAnnos) { c =>
      // This test is the same as the one in the keccak code on github
      // https://github.com/gvanas/KeccakCodePackage/blob/master/TestVectors/KeccakF-1600-IntermediateValues.txt
      val test_message = Array.fill(c.round_size_words) { BigInt(0) }
      val test_hash = Array(
        BigInt("F1258F7940E1DDE7", 16),
        BigInt("84D5CCF933C0478A", 16),
        BigInt("D598261EA65AA9EE", 16),
        BigInt("BD1547306F80494D", 16)
      )
      var timeout =
        10000 // Set a time big enough such that we can handle the case that valid is never high.

      for (i <- 0 until c.round_size_words)
        c.io.message.bits(i).poke(test_message(i))
      c.io.message.valid.poke(1)
      c.io.hash.ready.poke(1)
      do {
        c.clock.step(1)
        c.io.message.valid.poke(0)
        timeout = timeout - 1
      } while (c.io.hash.valid.peekBoolean() == false && timeout > 0)

      assert(timeout != 0, "FAIL - Sha3AccelTest timed out.")

      c.io.hash.valid.expect(1)

      for (i <- 0 until 4)
        c.io.hash.bits(i).expect(test_hash(i))
    }
  }
}
