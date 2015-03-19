'use strict';

var gulp = require('gulp');
var paths = gulp.paths;
var util = require('util');
var browserSync = require('browser-sync');

gulp.task('serve', ['watch'], function () {
  browserSync({
    // By default, Play is listening on port 9000
    proxy: 'localhost:9000',
    // We will set BrowserSync on the port 9001
    port: 9001,
    // Reload all assets
    // Important: you need to specify the path on your source code
    // not the path on the url
    files: ['public/.tmp/*.css', 'public/javascripts/*.js', 'app/views/*.html'],
    open: false
  });
});
