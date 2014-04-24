ground = [[1, 19], [670, 830], [1042, 1073], [1214, 1297],
 [2265, 2712], [2054, 2121], [2982, 3064], [3390, 3410], [3457, 3499], [3812, 3845], [3907, 3961], [4007, 4063]]
f1 = open("img_pitch_other.txt","w")
f2 = open("img_ground_long.txt","w")
pitch = [[20, 71], [72, 77], [78,243],[1298, 1381], [2122, 2209], [2177, 2208], [2930, 2980],
 [3862, 3902], [3962, 4006], [4273, 4293]]
long_shot = [[1385, 1437],[1511, 1724],  [2054, 2121],[2210, 2225], [3065, 3086], [3266, 3333], [3354, 3389], 
[3411, 3453], [3500, 3518], [3850, 3861], [4167, 4176], [4177, 4256]]
for a in ground:
	for i in range(a[0],a[1]+1):
		s = "snaps" + str(i) + ".png, " + "ground\n"
		s1 = "snaps" + str(i) + ".png, " + "other\n"
		f2.write(s)
		f1.write(s1)

for a in pitch:
	for i in range(a[0],a[1] + 1):
		s = "snaps" + str(i) + ".png, " + "pitch\n"
		f1.write(s)

for a in long_shot:
	for i in range(a[0],a[1] + 1):
		s = "snaps" + str(i) + ".png, " + "long_shot\n"
		s1 = "snaps" + str(i) + ".png, " + "other\n"
		f2.write(s)
		f1.write(s1)


