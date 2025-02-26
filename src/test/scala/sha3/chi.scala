package sha3

import chiseltest._

class ChiModuleTests extends TestWithBackendSelect with ChiselScalatestTester {
  behavior of "ChiModule"
  // YOUR TEST CODE BELOW
  it should "do the Chi function" in {
    test(new ChiModule()).withAnnotations(
      simAnnos
    ) { c =>
      for (iter <- 0 to 100) {
        // initialize variables
        val state = Array.fill(5 * 5) {
          BigInt(util.Random.nextInt(Integer.MAX_VALUE))
        }
        var st = state.clone
        var bc = Array.fill(5) { BigInt(0) }

        // Chi
        for (j_0 <- 0 until 5) {
          val j = j_0 * 5
          for (i <- 0 until 5) {
            bc(i) = st(j + i)
          }
          for (i <- 0 until 5) {
            st(j + i) = st(j + i) ^ (~bc((i + 1) % 5) & bc((i + 2) % 5))
          }
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
