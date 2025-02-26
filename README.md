# sha3-accelerator
## Overview
This project implements a hardware accelerator for the SHA-3 cryptographic hash function. The accelerator is designed to improve hashing performance by offloading computations from the processor, enabling faster and more efficient cryptographic operations.

## Features
- Implements the full SHA-3 hash function family (SHA3-224, SHA3-256, SHA3-384, SHA3-512)
- Optimized Keccak-f[1600] permutation for efficient sponge construction
- Pipelined and parallelized architecture for high throughput
- Verilog-based implementation compatible with FPGA/ASIC design
- Testbench included for functional verification

## Architecture
The SHA-3 accelerator follows a modular design with the following key components:
- **Keccak-f[1600] Core**: Implements the permutation function using a round-based pipeline.
- **State Registers**: Maintain intermediate hash state during processing.
- **Control Unit**: Manages the flow of data and execution of the hashing algorithm.

## Verification
- The design is verified using a testbench that checks hash outputs against known SHA-3 test vectors.

## References
- NIST FIPS 202: SHA-3 Standard: Permutation-Based Hash and Extendable-Output Functions
- Keccak Team: https://keccak.team


## Descriptions of Circuits
**Theta:** Fundamentally, the Theta circuit involves creating a reliance of all the bits on all the other bits through a series of XORs and rotations. My circuit includes a for loop that first initializes the variable bc through different xor circuits, then zips the bc array (functionally a zipWithIndex) to calculate and assign io.state_o.

**RhoPi:** I did not write the RhoPi; the idea of the circuit however is to randomize the order and rotate all of the inputs. The testbench mirrors the software implementation.

**Chi:** The chi module is the final step in the abridged hashing algorithm, once again interlocking values with its neighbor values. I implemented this using a series of zipWithIndex functions, using them equivalently to how the software uses for loops. 

**SHA3:** The top level module creates the communication interface between the control circuit and the datapath. In the module, I pad the input message so that the input length matches the datapath input, and then when the control circuit begins computation, I connect the datapath to the input. When the control circuit needs to stop the computation, the datapath is then disconnected.

**CTRL:** The control module holds the state machine. Based on the ready and valid signals, the state progresses, and when enough rounds have been implemented, the state machine moves to the final state, and sets the input ready and output valid bits.

**DPATH:** The datapath connects the lower level modules together; without pipelining, the Theta, RhoPi, Chi, and Iota modules are connected in series. The datapath module outputs the final result after all the preceding computations are complete. 
