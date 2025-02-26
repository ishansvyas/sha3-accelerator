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

