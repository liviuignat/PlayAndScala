'use strict';

var gulp = require('gulp');
var paths = gulp.paths;
var $ = require('gulp-load-plugins')();
var wiredep = require('wiredep').stream;

gulp.task('inject', ['styles', 'scripts'], function () {
  var injectStyles = gulp.src([
    paths.tmp + '/assets/{app,components}/**/*.css',
    '!' + paths.tmp + '/assets/app/vendor.css'
  ], { read: false });

  var injectScripts = gulp.src([
    paths.tmp + '/assets/app/index.js',
    paths.tmp + '/assets/{app,components}/**/*.js',
    '!' + paths.tmp + '/assets/app/templateCacheHtml.js',
    '!' + paths.tmp + '/assets/{app,components}/**/*.spec.js',
    '!' + paths.tmp + '/assets/{app,components}/**/*.mock.js'
  ], {
    read: false
  });

  var partialsInjectFile = gulp.src(paths.tmp + '/assets/app/templateCacheHtml.js', { read: false });
  var partialsInjectOptions = {
    starttag: '<!-- inject:partials -->',
    ignorePath: paths.tmp,
    addRootSlash: true
  };

  var injectOptions = {
    ignorePath: [paths.src, paths.tmp],
    addRootSlash: true
  };

  var wiredepOptions = {
    ignorePath: '..',
    directory: paths.bower,
    exclude: [/bootstrap\.js/, /bootstrap\.css/, /bootstrap\.css/, /foundation\.css/]
  };

  return gulp.src(paths.src + '/*.html')
    .pipe($.inject(injectStyles, injectOptions))
    .pipe($.inject(injectScripts, injectOptions))
    .pipe($.inject(partialsInjectFile, partialsInjectOptions))
    .pipe(wiredep(wiredepOptions))
    .pipe(gulp.dest(paths.tmp + '/assets'));

});
