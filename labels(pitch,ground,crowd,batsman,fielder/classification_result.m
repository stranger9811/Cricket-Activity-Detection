%level1_classification
%pitch_ground
%crowd_batsman_fielder
for i=5545:10000
	input = strcat('testing_snap_png/snaps',int2str(i),'.png');
	I = imread(input);
	I = rgb2hsv(I);
	T = I(:,:,1);
	T= transp(imhist(T));
	level_1_result = svmclassify(SVMStruct_level1,T);
	disp(input)
	if level_1_result == 1

		disp(' classified as class A')
		result2 = svmclassify(SVM_pitch_other,T)
		
	else
		disp('classified as class B')
		predict(nb_crowd_batsman_fielder,T)
	end
end