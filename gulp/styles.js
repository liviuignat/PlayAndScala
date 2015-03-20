'use strict';

var gulp = require('gulp');
var paths = gulp.paths;
var $ = require('gulp-load-plugins')();

gulp.task('styles', function () {
  return gulp.src([
    paths.src + '/app/index.less',
    paths.src + '/app/vendor.less'
  ])
    .pipe($.less())
    .pipe($.autoprefixer()).on('error', function handleError(err) {
      console.error(err.toString());
      this.emit('end');
    })
    .pipe(gulp.dest(paths.tmp + '/assets/app/'));
});
