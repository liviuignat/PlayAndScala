# Play Framework with Scala, AngularJS, ECMAScript 6, MongoDB template project. [![Build Status](https://travis-ci.org/liviuignat/PlayAndScala.svg?branch=master)](https://travis-ci.org/liviuignat/PlayAndScala)

Published on heroku: https://scalaplaymongo.herokuapp.com/

### Play Framework, Scala, MongoDB, AngularJS, ECMAScript 6, gulp, browser-sync, TravisCI. 
####Isues covered on this repository:

* Have the Play app set up
* Have the AngularJS app set up with ES6
* Have the tests for both Scala and AngularJS apps run in Travis-CI
* Have the deployment AngularJS set up with ```gulp``` for production and development environments that will: compile ES6 JS with traceur, compile LESS, minimize CSS and JS and many more.
* Install browser-sync
* Deploy it on Heroku cedar stack, run ```gulp build:prod``` on Heroku before deployment

####Install/run app
Make sure you have installed: Scala, SBT, NodeJS, bower, gulp-cli 

```
~ sudo npm install
~ bower install
~ sbt compile run
//in a second window enable browser-sync
~ guld serve
```

####Run tests
```
~ sbt test
~ gulp test
~ gulp test:auto  //watch on tests
```

####Travis setup
Make sure you enabled the project to run on Travis CI on your travis account. This is the .travis.yml:
```
language: scala
scala:
  - 2.11.6
node_js:
  - 0.10

before_install:
  - "export DISPLAY=:99.0"
  - "sh -e /etc/init.d/xvfb start"

before_script:
  - "npm install -g gulp-cli"
  - "npm install -g karma"
  - "npm install"

script:
  - "sbt clean test"
  - "./node_modules/.bin/karma start ./app/assets/client/karma.conf.js --browsers Firefox --single-run"
```

####Heroku deployment
This app has to run 2 deployment scripts, first one for the AngulaJS app with ```gulp``` and the second one for the Scala app with ```sbt```

To make this possible, you need to enable ```multi-build-pack``` on Heroku for your app. Make sure you add the following file in your root: ```.buildpacks``` and add this content to the file:
```
https://github.com/heroku/heroku-buildpack-nodejs
https://github.com/heroku/heroku-buildpack-scala
```
Make sure you have a ```package.json``` in your root file.

Make sure you run your ```gulp production build task``` inside ```package.json```:
```
"scripts": {
  "postinstall": "./node_modules/.bin/gulp build:prod"
},
```

Run these commands to support multi-build-pack on Heroku:
```
heroku buildpack:set https://github.com/heroku/heroku-buildpack-multi
heroku config:set SBT_OPTS="-Dsbt.jse.engineType=Node"
```

Add Heroku as a second remote:
```
~ git remote add heroku git@heroku.com:{app_name}.git
```

Publish to Heroku master:
```
~ git push heroku {local_branch_name}:master
```
