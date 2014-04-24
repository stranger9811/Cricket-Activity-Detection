crowd = [[3161, 3265],[3625,3803],[4113,4116] ]
fi = open("img_crowd_batsman_fielder.txt","w")
batsman = [[2720,2792],[2974,2980], [3114, 3160],[3625,3804],[4065,4112]
,[4297,4303],[7601,7704]]
fielder = [[244, 628], [856, 1034], [1438, 1510], [2226, 2264],[8215,8267],[8614,8660]]
other = [[1934, 2043],  [3089, 3113]]
for a in crowd:
	for i in range(a[0],a[1]+1):
		s = "snaps" + str(i) + ".png, " + "crowd\n"
		fi.write(s)

for a in batsman:
	for i in range(a[0],a[1] + 1):
		s = "snaps" + str(i) + ".png, " + "batsman\n"
		fi.write(s)

for a in fielder:
	for i in range(a[0],a[1]+1):
		s = "snaps" + str(i) + ".png, " + "fielder\n"
		fi.write(s)
