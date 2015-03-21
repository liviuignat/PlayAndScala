'use strict';

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
    ]
  });
};
