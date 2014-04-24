Cricket-Activity-Detection
=========


Part 1.(Shot Boundary Detection)
 - This code finds out cut and fades in the video using supervised learning

```sh
file_splitter (matlab file to split video into frames)
javac ColorHistogram1.java
java ColorHistogram1     (To Create ARFF file for testing or training data)
python nonCut_Cut_marker.py (to mark cut points in training data)
python nonCut_gradual_marker.py  (To mark gradual points in training data)
```
> Note: we do classification of cut and gradual point saperately. For that we use L1classify.java and L2classify.java respectively.



This text you see here is *actually* written in Markdown! To get a feel for Markdown's syntax, type some text into the left window and watch the results in the right.  

Version
----

2.0

Tech
-----------

Dillinger uses a number of open source projects to work properly:

* [Ace Editor] - awesome web-based text editor
* [Marked] - a super fast port of Markdown to JavaScript
* [Twitter Bootstrap] - great UI boilerplate for modern web apps
* [node.js] - evented I/O for the backend
* [Express] - fast node.js network app framework [@tjholowaychuk]
* [keymaster.js] - awesome keyboard handler lib by [@thomasfuchs]
* [jQuery] - duh 

Installation
--------------

```sh
git clone [git-repo-url] dillinger
cd dillinger
npm i -d
mkdir -p public/files/{md,html,pdf}
```

##### Configure Plugins. Instructions in following README.md files

* plugins/dropbox/README.md
* plugins/github/README.md
* plugins/googledrive/README.md

```sh
node app
```


License
----

MIT


**Free Software, Hell Yeah!**

[dipen_code]:http://www.cse.iitk.ac.in/users/vision/dipen/code


    