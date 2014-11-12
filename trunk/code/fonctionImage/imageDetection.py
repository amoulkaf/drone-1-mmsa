# this script finds in an image if there is a black/dark grey
# area of 1/SCALE area

from PIL.Image import *
from threading import Thread
import time

FILEPATH = "../../doc/IHM/ihm.png"
# limit inf of r,g,b component
RANGE_INF = 0
# limit sup of r,g,b component
RANGE_SUP = 0
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
def detectPix(img, x1, y1, x2, y2):
	#(l,h) = img.size
	(i,j) = (x1,y1)
	for i in range(x1,x2):
		for j in range(y1,y2):
			currentPix = img.getpixel((i,j))
			#pixelEqualsTo(currentPix)
			if isDetectedArea(img,i,j,currentPix):
				#print("Area detected")
				#print "color : ",currentPix
				print "coordinates : ",(i,j)
				print ("true")
				return
	print("false")


##	TEST DES THREADS

class Calcul(Thread):
	def __init__(self,img,x1,y1,x2,y2):
		Thread.__init__(self)
		self.img = img
		self.x1 = x1
		self.y1 = y1
		self.x2 = x2
		self.y2 = y2
	
	def run(self):
		detectPix(self.img, self.x1, self.y1, self.x2, self.y2)

def main():
	img = open(FILEPATH)
	img1 = open(FILEPATH)
	img2 = open(FILEPATH)
#	detectPix(img)
	(l,h) = img.size
	length = l//3

	t = Calcul(img, 0, 0, l, h)
	"""	
	t = Calcul(img, 0, 0, length, h)
	d = Calcul(img1, length, 0, 2*length, h)
	p = Calcul(img2, 2*length, 0, l, h)
	d.start()
	p.start()
	"""	
	t.start()

if __name__ == "__main__": 
	main()

