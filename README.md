# IdentityE2E

Write a test automation suite which does following.
1. Reads given input file: car_input.txt
2. Extracts vehicle registration numbers based on pattern(s).
3. Each number extracted from input file is fed to https://cartaxcheck.co.uk/
(Peform Free Car Check)
4. Compare the output returned by https://cartaxcheck.co.uk/ with given
car_output.txt
5. Highlight/fail the test for any mismatches.
Showcase your skills so its easier to add more input files in future.
Utilise any JVM based language with broser automation tools.
Use design patterns where appropriate.


Tests Results:
=====================================================================
1. DN09HRM: MATCHED.
2. BW57BOW: Vehicle Not Found.
3. KT17DLX: MATCHED.
4. SG18HTN: MATCHED
