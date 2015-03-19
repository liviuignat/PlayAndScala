'use strict';

var gulp = require('gulp');
var paths = gulp.paths;
var $ = require('gulp-load-plugins')();
var wiredep = require('wiredep').stream;

gulp.task('inject', ['styles', 'scripts'], function () {
  var injectStyles = gulp.src([
    paths.tmp + '/serve/{app,components}/**/*.css',
    '!' + paths.tmp + '/serve/app/vendor.css'
  ], { read: false });

  var injectScripts = gulp.src([
    paths.tmp + '/serve/app/index.js',
    paths.tmp + '/serve/{app,components}/**/*.js',
    '!' + paths.tmp + '/serve/app/templateCacheHtml.js',
    '!' + paths.tmp + '/serve/{app,components}/**/*.spec.js',
    '!' + paths.tmp + '/serve/{app,components}/**/*.mock.js'
  ], {
    read: false
  });

  var partialsInjectFile = gulp.src(paths.tmp + '/serve/app/templateCacheHtml.js', { read: false });
  var partialsInjectOptions = {
    starttag: '<!-- inject:partials -->',
    ignorePath: paths.tmp + '/serve/app/',
    addRootSlash: true,
    addPrefix: 'assets'
  };

  var injectOptions = {
    ignorePath: [paths.src, paths.tmp + '/serve/app/'],
    addPrefix: 'assets',
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
    .pipe(gulp.dest('app/views'));

});
