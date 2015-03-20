'use strict';

var gulp = require('gulp');

gulp.paths = {
  bower: './app/assets/client/bower_components',
  src: './app/assets/client/src',
  dist: './public/dist',
  tmp: './public/dev',
  views: './app/views',
  e2e: './app/assets/client/e2e'
};

require('require-dir')('./gulp');

gulp.task('default', ['clean'], function () {
    gulp.start('build');
});
