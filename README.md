Cricket-Activity-Detection
=========
---
AUTHORS
---
- Ashok Kumar
- Javesh Garg

The code is written in matlab, java and python using open source libraries. All the code is separated into three parts and the code for one part is bundled up togather.
Part 1: Shot Boundary Detection
---
 - This code finds out cut and fades in the video using supervised learning.

```sh
file_splitter (matlab file to split video into frames - .ppm and .png)
javac ColorHistogram1.java
java ColorHistogram1     (To Create ARFF file for testing or training data)
python nonCut_Cut_marker.py (to mark cut points in training data)
python nonCut_gradual_marker.py  (To mark gradual points in training data)
```
> Note: L1classify.java and L2classify.java respectively classify cuts and gradual frames respectively.

> Weka java library is used to run the code and has been included in the tarball.

Part 2: Frame Classification
---
- This classifies the indivual frames into four different classes using multi-class SVM. 
- First classifes into field view and non - field view (level1_classification.m)
- Separately classifes field view into pitch view or ground view (pitch_other.m)
- Non-field view is classified into fielder view or others (crowd_batsman_fielder.m)
- Also reports when a cricket shot is played in a video (continous pitch views).

```sh
classification_result (input: folder containing .png images of the test data)
```

Part 3: Optical Flow Analysis
---
- This is the final part which reports the direction of the shot played into four classes.
- Input: The sequence of frames where the batsman plays the shot. This is the basic entity we got from part 2.
- Output: The direction of the shot played in the given input. Gives a GUI of the vector fields showing motion lines. 

```sh
test.m (uses optical_flow.m to find out the optical flow of the frames)
```

Version
----

1.0

License
---

GPLv3


**Free Softwares and Codes, Hell Yeah!**

http://www.cse.iitk.ac.in/users/vision/dipen/code

http://weka.wikispaces.com/