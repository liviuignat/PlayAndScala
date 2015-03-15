'use strict';

var gulp = require('gulp');

var paths = gulp.paths;

var $ = require('gulp-load-plugins')();

var wiredep = require('wiredep').stream;

gulp.task('inject', ['styles', 'browserify'], function () {

  var injectStyles = gulp.src([
    paths.tmp + '/serve/styles/**/*.css',
    '!' + paths.tmp + '/serve/styles/vendor.css'
  ], { read: false });

  var injectScripts = gulp.src([
    paths.tmp + '/serve/**/*.js',
    '!' + paths.src + '/**/*.spec.js',
    '!' + paths.src + '/**/*.mock.js'
  ], { read: false });

  var injectOptions = {
    ignorePath: [paths.src, paths.tmp + '/serve'],
    addRootSlash: false
  };

  var wiredepOptions = {
    directory: 'bower_components',
    exclude: [/bootstrap\.js/, /bootstrap\.css/, /bootstrap\.css/, /foundation\.css/]
  };

  return gulp.src(paths.src + '/*.html')
    .pipe($.inject(injectStyles, injectOptions))
    .pipe($.inject(injectScripts, injectOptions))
    .pipe(wiredep(wiredepOptions))
    .pipe(gulp.dest(paths.tmp + '/serve'));

});
