#!/usr/bin/python

import struct

var = 0.8
tmp = struct.pack('>f', var)
var754 = struct.unpack('>l', tmp)[0]
print(str(var) + " --> " + str(var754))
