start = 11215;
endF = 11240;
images=[];
for k = start:endF
	I = im2double(imread(strcat('/Users/Ashok/Dropbox/AI project/My Code/cut_noncut_classifier/testing_snap_png/snaps',int2str(k),'.png')));
    I = rgb2gray(I);
    
    I = imresize(I,[100 100]);

	images(:,:,k-start + 1) = I;
end

[MVx,MVy] = OpticalFlow(images,1,16);
[x,y] = size(MVx);

velocity = MVx;
angle = MVx;
start_point = x/5;
end_point = 4*x/5;
mean_velocity  = 0;
for i=1:x
	for j=1:y
		velocity(i,j) = (MVx(i,j)^2 + MVy(i,j)^2)^.5;
		mean_velocity = mean_velocity + velocity(i,j);
		if MVx(i,j) >0 && MVy(i,j) > 0
			angle(i,j) = 2;
		elseif MVx(i,j)>0 && MVy(i,j) < 0
			angle(i,j) = 1;
		elseif MVx(i,j)< 0 && MVy(i,j) < 0
			angle(i,j) = 4;
		elseif MVx(i,j) < 0 && MVy(i,j) > 0
			angle(i,j) = 3;
		else 
			angle(i,j) = 5;
		end
	end
end

mean_velocity = mean_velocity/(x*y)
count = [0,0,0,0,0];
for i=1:x 
	for j=1:y
		if velocity(i,j) > mean_velocity
			count(angle(i,j)) = count(angle(i,j)) + 1;
		end
	end
end
count

figure(1);
quiver(MVx(end:-1:1,:), MVy(end:-1:1,:));
title('Motion Vector Field');






