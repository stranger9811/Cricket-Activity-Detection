level1_classification
pitch_other
crowd_batsman_fielder
count = 0;
for i=1:10000
	input = strcat('testing_snap_png/snaps',int2str(i),'.png');
	I = imread(input);
	I = rgb2hsv(I);
	T = I(:,:,1);
	T= transp(imhist(T));
	level_1_result = svmclassify(SVMStruct_level1,T);
	if level_1_result == 1

		result2 = svmclassify(SVM_pitch_other,T);
		if result2{1}(1) == 'p'
			count = count + 1;

			if count == 50
				disp('cricket shot detected');
				disp(i-25);
			end
		else
			count = 0;
		end 
		
	else
		count = 0;
		predict(nb_crowd_batsman_fielder,T);
	end
end