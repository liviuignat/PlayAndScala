# Play Framework with Scala, MongoDB, AngularJS template project. 

Isues covered on this repository:

* Have the Play app set up
* Have the AngularJS app set up with ES6
* Have the tests for both Scala and AngularJS apps run in Travis-CI
* Have the deployment AngularJS set up with ```gulp``` for production and development environments that will: compile ES6 JS with traceur, compile LESS, minimize CSS and JS and many more.
* Install browser-sync
* Deploy it on Heroku cedar stack

####Install app
```
~ sudo npm install
~ bower install
~ cd ./app/assets/client
~ bower install
// go back to root folder
~ sbt compile run
//in a second window enable browser-sync
~ guld serve
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
