crowd = [[1,243],[641,839]]
fi = open("img_crowd_batsman_fielder.txt","w")
batsman = [[244,632],[1001,1035]]
bowler = []
for a in crowd:
	for i in range(a[0],a[1]+1):
		s = "snaps" + str(i) + ".png, " + "crowd\n"
		fi.write(s)

for a in batsman:
	for i in range(a[0],a[1] + 1):
		s = "snaps" + str(i) + ".png, " + "batsman\n"
		fi.write(s)

for a in bowler:
	for i in range(a[0],a[1]+1):
		s = "snaps" + str(i) + ".png, " + "bowler\n"
		fi.write(s)
