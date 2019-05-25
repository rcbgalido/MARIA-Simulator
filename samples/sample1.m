LOAD lowerbound
STORE x

a, LOAD x
ADD increment
STORE x
SKIPCOMP upperbound
JUMP b
HALT
b, OUTPUT
JUMP a

lowerbound, DEC 0
upperbound, DEC 50
increment, DEC 5
x, DEC 0
