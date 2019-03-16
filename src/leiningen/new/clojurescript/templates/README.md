# StuW things to do #

```
- [ ] Add automatic CSS reloading
- [X] Update clean target
- [X] Add a basic button
- [X] Add Antd components
- [ ] Add routing
- [ ] Add integration test
- [ ] Make notes on turning on browser templating of errors
- [ ] Check all the files
- [ ] Open the figwheel-extra-main/auto-testing page at startup
- [ ] Load stylesheets from a local resource
```

# ClojureScript Single Page Application #

A basic ClojureScript single page application with batteries built in:

- `figwheel-main` for development
- `reagent` as an interface to React
- `antizer` for the Ant Design React UI component library
- `accountant` (https://github.com/venantius/accountant) for stuff
- [secretary](https://github.com/clj-commons/secretary) for routing


All of this is just plugging together the awesome work of others.

## Set-up ##

TODO: Talk about setting up your browser and enabling custom reporting

## Overview

## Running a REPL ##

- Run `lein fig:build`
- Browse to http://localhost:9500/

Thanks to `figwheel`, any source changes will automatically be compiled and the browser reloaded.

## Running tests in the browser ##

Thanks to `figwheel`, tests are automatically run in the browser. To view the results and receive notifications when
tests pass or fail:

- Start a REPL as described above
- Browse to http://localhost:9500/figwheel-extra-main/auto-testing

## Creating a production build ##

- Run `lein clean`
- Run `lein fig:min`
