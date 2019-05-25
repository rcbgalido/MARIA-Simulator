INPUT a
INPUT b
INPUT c

LOAD a
SQUARE a
LOAD b
MULTI c
STORE product
LOAD a
SUBT product
OUTPUT
CNJUMP end
end, HALT


a, dec 0
b, dec 0
c, dec 0
product, dec 0 
