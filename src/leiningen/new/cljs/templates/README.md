# StuW things to do #

```
- [ ] Add automatic CSS reloading
- [X] Update clean target
- [ ] Load stylesheets from a local resource
```

# ClojureScript Single Page Application #

A basic ClojureScript single-page application with batteries built in:

- Pretty UI components
- Basic navigation pages
- Browser history and navigation

All of this is just plugging together the awesome work of others:

- [bhauman/figwheel-main](https://github.com/bhauman/figwheel-main) for development tooling
- [reagent-project/reagent](https://github.com/reagent-project/reagent) to interface to React
- [priornix/antizer](https://github.com/priornix/antizer) to interface to the Ant Design React UI component library
- [venantius/accountant](https://github.com/venantius/accountant) for single-page application navigation
- [clj-commons/secretary](https://github.com/clj-commons/secretary) for routing

## Set-up ##

If you haven't previously done so, configure your browser to link preprocessed code to source:

- [Map Preprocessed Code to Source Code](https://developers.google.com/web/tools/chrome-devtools/javascript/source-maps?hl=en)
    - From the menu select `View` > `Developer Tools`
    - Select `Sources` tab
    - Open the drop down menu (three dots) and select `Settings`
    - Tick `Enable Javascript source maps`

## Overview

## Running a REPL ##

- Run `lein fig:build`
- Browse to http://localhost:9500/

Thanks to `figwheel`, any source changes will automatically be compiled and the browser reloaded.

## Running unit tests in the browser ##

Thanks to `figwheel`, unit tests are automatically run in the browser. To view the results and receive notifications when
tests pass or fail:

- Start a REPL as described above
- Browse to http://localhost:9500/figwheel-extra-main/auto-testing

## Creating a production build ##

- TBD

