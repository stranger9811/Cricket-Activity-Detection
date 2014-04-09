ground = [[1,243],[641,839]]
fi = open("img_classes.txt","w")
non_ground = [[244,632],[1001,1035]]
for a in ground:
	for i in range(a[0],a[1]+1):
		s = "snaps" + str(i) + ".png, " + "1\n"
		fi.write(s)

for a in non_ground:
	for i in range(a[0],a[1] + 1):
		s = "snaps" + str(i) + ".png, " + "2\n"
		fi.write(s)

