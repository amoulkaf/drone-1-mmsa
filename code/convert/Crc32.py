#!/usr/bin/python

import zlib

var = "ses"
tmp = zlib.crc32(var)
hextmp = tmp & 0xffffffff
crc32 = format(hextmp, '08x')
print(str(var) + " --> " + str(crc32))
