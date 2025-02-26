package sha3

import chiseltest._
class ThetaModuleTests
    extends TestWithBackendSelect
    with ChiselScalatestTester {
  behavior of "ThetaModule"

  it should "do the Theta function" in {
    test(new ThetaModule()).withAnnotations(
      simAnnos
    ) { c =>
      def ROTL64(x: BigInt, y: Int) =
        (((x) << (y)) | ((x) >> (64 - (y)))) & (((1: BigInt) << 64) - 1)
      val W = 64
      // main test loop
      for (iter <- 0 to 100) {
        // generate random inputs
        val state = Array.fill(5 * 5) {
          BigInt(util.Random.nextInt(Integer.MAX_VALUE))
        }

        // calculate groundtruth outputs (direct translation of C code)
        var st = state.clone
        var bc = Array.fill(5) { BigInt(0) }
        for (i <- 0 to 4)
          bc(i) = st(i) ^ st(i + 5) ^ st(i + 10) ^ st(i + 15) ^ st(i + 20)
        for (i <- 0 to 4) {
          val tmp = bc((i + 4) % 5) ^ ROTL64(bc((i + 1) % 5), 1)
          for (j <- 0 to 24 by 5)
            st(j + i) ^= tmp
        }
        val out_state = st

        // peekpoke testing
        for (i <- 0 until 25)
          c.io.state_i(i).poke(state(i))
        c.clock.step(1)
        for (j <- 0 until 25)
          c.io.state_o(j).expect(out_state(j))
      }
    }
  }
}
