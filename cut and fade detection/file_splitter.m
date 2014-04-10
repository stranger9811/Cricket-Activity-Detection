clc;
close all;
clear all;
filename = 'testing_data.mp4';
mov = VideoReader(filename);
opFolder_png = fullfile(cd, 'snap_png');
opFolder_ppm = fullfile(cd, 'snap_ppm');
if ~exist(opFolder_png, 'dir')
    mkdir(opFolder_png);
end
if ~exist(opFolder_ppm, 'dir')
    mkdir(opFolder_ppm);
end
numFrames = mov.NumberOfFrames;
numFramesWritten = 0;
%for loop to traverse & process from frame '1' to 'last' frames
for t = 1 : 17000
    currFrame = read(mov, t);    %reading individual frames
    currFrame = imresize(currFrame,[800 800]);
    opBaseFileName_png = sprintf('snaps%d.png', t);
    opBaseFileName_ppm = sprintf('snaps%d.ppm', t);
    opFullFileName_png = fullfile(opFolder_png, opBaseFileName_png);
    opFullFileName_ppm = fullfile(opFolder_ppm, opBaseFileName_ppm);
    imwrite(currFrame, opFullFileName_png, 'png');   %saving as 'png' file
    imwrite(currFrame, opFullFileName_ppm, 'ppm');   %saving as ppm file
    %indicating the current progress of the file/frame written
    progIndication = sprintf('Wrote frame %4d of %d.', t, numFrames);
    disp(progIndication);
    numFramesWritten = numFramesWritten + 1;
end      %end of 'for' loop
progIndication = sprintf('Wrote %d frames to folder "%s"',numFramesWritten, opFolder);
disp(progIndication);
%End of the code
 
