from PIL import Image
from math import sqrt
import re

im = Image.open("./patterns/boxes.jpg")
pix = im.load()

def color(rgb):
    r, g, b = rgb
    return (0.2989*r + 0.5870*g + 0.1140*b) > 128



W = True
B = False

template = [[B, W, B, W, B],
            [W, B, B, B, W],
	    [B, B, W, B, B],
	    [W, B, B, B, W],
            [B, W, B, W, B]]

scales = range(1, 100, 5)

xs, xe, ys, ye = 1000, 1500, 1100, 1600

rows = [[color(pix[x,y]) for x in range(xs, xe)] for y in range(ys, ye)]

for row in rows:
    print(''.join([' %'[x] for x in row]))

for scale in scales:
    for sx in range(xe-xs-4*scale-1):
        for sy in range(ye-ys-4*scale-1):
            still_good = True
            for i in range(5):
                for j in range(5):
                    still_good = template[i][j] == rows[sx+i*scale][sy+j*scale]
                    if not still_good: break
                if not still_good: break
            if still_good:
                print "Found: scale, sx, sy = %d, %d, %d" % (scale, sx, sy)

