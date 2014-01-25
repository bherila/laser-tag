from PIL import Image
from math import sqrt
import re

im = Image.open("./samples/b.jpg")
pix = im.load()

def distance(rgb1, rgb2):
    a,b,c = rgb1
    d,e,f = rgb2
    return sqrt((a-d)**2 + (b-e)**2 + (c-f)**2)

def color(rgb):
    options = [('w',255,255,255),('k',0,0,0),('r',255,128,128)]
    d = lambda o: distance(rgb, o[1:])
    return min(options, key=d)[0]

xs, xe, ys, ye = 1000, 1500, 1300, 1800

rows = [''.join([color(pix[x,y]) for x in range(xs, xe)]) for y in range(ys, ye)]

template = 'rkrwwrkr'
patterns = []

for i in range(2, 10):
    reps = "{%d,%d}" % (i, 3*i)
    patterns.append(''.join([x+reps for x in template]))

for p in patterns:
    l = len(p)
    for i in range(len(rows)):
        row = rows[i]
        m = re.search(p, row)
        if m:
            print "Found! Row %d, range: %d -- %d, %s" % (i, m.start(), m.end(), row[m.start():m.end()])

