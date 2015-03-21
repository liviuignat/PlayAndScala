'use strict';

module.exports = function(config) {

  config.set({
    autoWatch : false,

    frameworks: ['jasmine'],

    browsers : ['Firefox'],

    captureTimeout: 60000,

    plugins : [
        'karma-phantomjs-launcher',
        'karma-chrome-launcher',
        'karma-firefox-launcher',
        'karma-jasmine'
    ]
  });
};
