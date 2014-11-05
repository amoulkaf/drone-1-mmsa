from PIL.Image import *

filePath = "../../doc/IHM/ihm.png"

def detectPix(pix, img):
	(l,h) = img.size
	for x in range(l):
		for y in range(h):
			currentPix = img.getpixel((x,y))
			if (currentPix == pix):
				print("ok")

def main():
	img = open(filePath)
	detectPix((255,0,255,255), img)

if __name__ == "__main__": 
	main()