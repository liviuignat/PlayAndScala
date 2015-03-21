'use strict';

var wiredep = require('wiredep');
var bowerDeps = wiredep({
  directory: 'public/bower_components',
  exclude: ['bootstrap-sass-official'],
  dependencies: true,
  devDependencies: true
});

var bowerFiles = bowerDeps.js.map(function (file) {
  return '../../../' + file;
});

var files = bowerFiles.concat([
  '../../../public/dev/assets/app/index.js',
  '../../../public/dev/assets/app/templateCacheHtml.js',
  '../../../public/dev/assets/app/**/*.js',
]);

module.exports = function(config) {

  config.set({
    autoWatch : false,

    frameworks: ['jasmine'],

    reporters: ['mocha'],

    browsers : ['Firefox'],

    captureTimeout: 60000,

    plugins : [
      'karma-mocha-reporter',
      'karma-phantomjs-launcher',
      'karma-chrome-launcher',
      'karma-firefox-launcher',
      'karma-jasmine'
    ],

    files: files
  });
};
