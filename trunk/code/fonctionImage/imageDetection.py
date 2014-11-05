# this script finds in an image if there is a black/dark grey
# area of 1/SCALE area

from PIL.Image import *

FILEPATH = "../../doc/IHM/ihm.png"
# limit inf of r,g,b component
RANGE_INF = 0
# limit sup of r,g,b component
RANGE_SUP = 50
# scale of the area to find
SCALE = 20

# is the current pixel black or dark grey 
def pixelEqualsTo(pix):
	r = pix[0]
	g = pix[1]
	b = pix[2]
	for x in range(RANGE_INF, RANGE_SUP + 1) :
		if (r,g,b) == (x,x,x):
			return True

# is there black pixels in area
# area is 1/SCALE of img
def isDetectedArea(img, x, y, currentPix):
	(l,h) = img.size
	l_area = l//SCALE
	h_area = h//SCALE
	isDifferent = False
	if (x+l_area) < l and (y+h_area) < h and pixelEqualsTo(currentPix):
		for i in range(x, x+l_area+1):
			for j in range(y, y+h_area+1):
				if img.getpixel((i,j)) != currentPix:
					isDifferent = True
					break
		if not(isDifferent):
			return True
	return False

# detect if there is an area of pixel 
# [(RANGE_INF,RANGE_INF, RANGE_INF),(RANGE_SUP,RANGE_SUP,RANGE_SUP)]
def detectPix(img):
	(l,h) = img.size
	for x in range(l):
		for y in range(h):
			currentPix = img.getpixel((x,y))
			#pixelEqualsTo(currentPix)
			if isDetectedArea(img,x,y,currentPix):
				#print("Area detected")
				#print "color : ",currentPix
				#print "coordinates : ",(x,y)
				print ("true")
				return
	print("false")


def main():
	img = open(FILEPATH)
	detectPix(img)

if __name__ == "__main__": 
	main()
