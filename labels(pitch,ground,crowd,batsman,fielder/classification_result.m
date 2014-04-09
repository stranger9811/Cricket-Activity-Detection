I = imread('testing_snap_png/snaps938.png');
I = rgb2hsv(I);
T = I(:,:,1);
level_1_result = svmclassify(SVMStruct_level1,transp(imhist(T)))
if level_1_result == 1
	disp('classified as class A')
else
	disp('classified as class B')
end