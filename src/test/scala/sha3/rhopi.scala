package sha3

import chiseltest._

class RhoPiModuleTests
    extends TestWithBackendSelect
    with ChiselScalatestTester {
  behavior of "RhoPiModule"
  // YOUR TEST CODE BELOW
  // constants
  val rotc = List(
    1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 2, 14,
    27, 41, 56, 8, 25, 43, 62, 18, 39, 61, 20, 44)
  val piln = List(
    10, 7, 11, 17, 18, 3, 5, 16, 8, 21, 24, 4,
    15, 23, 19, 13, 12, 2, 20, 14, 22, 9, 6, 1)

  it should "do the rho pi function" in {
    test(new RhoPiModule()).withAnnotations(simAnnos) { c =>
      def ROTL64(x: BigInt, y: Int) =
        (((x) << (y)) | ((x) >> (64 - (y)))) & (((1: BigInt) << 64) - 1)
      val W = 64
      // main test loop
      for (iter <- 0 to 20) {
        // generate random inputs
        val state = Array.fill(5 * 5) {
          BigInt(util.Random.nextInt(Integer.MAX_VALUE))
        }

        // calculate groundtruth outputs (direct translation of C code)
        var st = state.clone
        var bc = BigInt(0)
        var t = st(1)

        for (i <- 0 until 24) {
          val j = piln(i)
          bc = st(j)
          st(j) = ROTL64(t, rotc(i))
          t = bc
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
