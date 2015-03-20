'use strict';
var gulp = require('gulp');
var async = require('async');
var path = require('path');
var fs = require('fs');

var paths = gulp.paths;
var routesPath = './conf/routes';

gulp.task('replace_prod', function (done) {
  async.waterfall([
    function (next) {
      replaceInFile(routesPath, 'path="/public/dev/assets"', 'path="/public/dist/assets"', function(err) {
        return next(err);
      });
    },
    function (next) {
      fs.unlink('./app/views/layout.scala.html', function (err) {
        if(err) {
          return next(err);
        }

        fs.createReadStream('./public/dist/layout.scala.html')
           .pipe(fs.createWriteStream('./app/views/layout.scala.html'));

        return next();
      });
    }
  ], function (err) {
    return done(err);
  });

});

gulp.task('replace_dev', function (done) {
  async.waterfall([
      function (next) {
        replaceInFile(routesPath, 'path="/public/dist/assets"', 'path="/public/dev/assets"', function(err) {
          next(err);
        });
      }
    ], function (err) {
      return done(err);
    });
});

function replaceInFile(filePath, find, replace, done) {
  fs.exists(filePath, function (exists) {
    if(!exists) {
      return done();
    }

    fs.readFile(filePath, 'utf8', function (err, data) {
      if(err) {
        return done(err);
      }

      var dataString = data.toString();
      dataString = dataString.replace(find, replace);

      fs.writeFile(filePath, dataString, function (err) {
        done(err);
      })
    })
  });
}
