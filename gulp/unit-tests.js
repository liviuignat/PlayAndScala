'use strict';

var gulp = require('gulp');
var $ = require('gulp-load-plugins')();
var wiredep = require('wiredep');

var paths = gulp.paths;

Function.prototype.bind = Function.prototype.bind || function (thisp) {
  var fn = this;
  return function () {
    return fn.apply(thisp, arguments);
  };
};

function runTests (singleRun, done) {
  var bowerDeps = wiredep({
    directory: paths.bower,
    exclude: ['bootstrap-sass-official'],
    dependencies: true,
    devDependencies: true
  });

  var testFiles = [
    paths.src + '/../karma.fix.js'
  ].concat(bowerDeps.js.concat([
    paths.tmp + '/assets/app/index.js',
    paths.tmp + '/assets/app/templateCacheHtml.js',
    paths.tmp + '/assets/app/**/*.js',
    paths.tmp + '/assets/components/**/*.js',
    paths.src + '/**/*.spec.js'
  ]));

  gulp.src(testFiles)
    .pipe($.plumber())
    .pipe($.karma({

      configFile: paths.src + '/../karma.conf.js',
      action: (singleRun)? 'run': 'watch'
    }));
}

gulp.task('test', [], function (done) { runTests(true /* singleRun */, done) });
gulp.task('test:auto', [], function (done) { runTests(false /* singleRun */, done) });
