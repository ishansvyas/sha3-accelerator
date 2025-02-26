package sha3

import chiseltest._
class IotaModuleTests extends TestWithBackendSelect with ChiselScalatestTester {
  behavior of "IotaModule"

  it should "do the iota function" in {
    test(new IotaModule()).withAnnotations(simAnnos) { c =>
      // this is a very weak tester
      val W = 64
      val round = 0
      val state = Array.fill(5 * 5) { BigInt(3) }
      val out_state = Array.fill(5 * 5) { BigInt(3) }
      out_state(0) = state(0) ^ BigInt(1)
      for (i <- 0 until 25)
        c.io.state_i(i).poke(state(i))
      c.io.round.poke(round)
      c.clock.step(1)
      for (j <- 0 until 25)
        c.io.state_o(j).expect(out_state(j))
    }
  }

}
