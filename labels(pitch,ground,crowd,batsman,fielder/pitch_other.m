system('python pitch_ground.py')
get_count = fopen('img_pitch_other.txt', 'r');
tline = fgetl(get_count); currentLine = 1;
while ischar(tline)       
    tline = fgetl(get_count);
    currentLine = currentLine + 1;
end;  
fclose(get_count);
number_of_training_data = currentLine - 1;



imgListFile = fopen('img_pitch_other.txt', 'r');
training_data = zeros(number_of_training_data,256);
labels = cell(number_of_training_data,1);

tline = fgetl(imgListFile); currentLine = 1;
while ischar(tline)       
    disp(tline)
    splittedLine = regexp(tline, ',[ ]*', 'split');
   
    imagePath = fullfile('train_snap_png', splittedLine{1});
    I = im2double(imread(imagePath));
    I = rgb2hsv(I);
    
    I = imhist(I(:,:,1));
   
    training_data(currentLine,:) = I;
    disp(splittedLine{2})
    labels{currentLine} = splittedLine{2};
    tline = fgetl(imgListFile);
    currentLine = currentLine + 1;
end;


SVM_pitch_other = svmtrain(training_data, labels);
