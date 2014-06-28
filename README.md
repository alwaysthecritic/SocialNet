# SocialNet

A small exercise in implementing a trivial command line social network sim, for the sake of learning and practice. It was fun to mess with.

## Notes

The code tries to be idiomatic, functional Scala, developed mostly via TDD. It should be reasonably understandable, but a few comments will help understand the choices I made.

* The SocialNet class is the core of it, being an immutable representation of the state of the whole network. It implements all the actions that can be taken, returning a new SocialNet when it is updated. Being immutable there are no threading concerns to worry about here and unit testing is easy.
* The Cli class runs the command line interface and is effectively just a translator between commands entered and SocialNet itself, plus the niceties of formatting output. So far, its formatting functions are unit tested but the actual command line handling is not as it relies on StdIn. That could be abstracted to an Output trait to make it testable.
* Unit testing is complicated by time, as messages record the 'now' at which they were posted but tests need predictable outputs. Also as a unit test tends to post a series of messages in immediate succession, the granularity of Date means they usually all end up posted at indistinguishable times, which ruins sorting of wall posts. The solution is to abstract time and inject a Clock wherever it is needed, which can supply the current time. This is injected via some nice use of implicits and default params, so it hardly interferes at all. Tests can use an appropriately rigged Clock implementation to control time as required.

## Building and running

The project is defined with [sbt](http://www.scala-sbt.org), which is the only thing you need installed to build and run it. On Mac it's easiest to `brew install sbt`, assuming you have [homebrew](http://brew.sh). Project files for Eclipse (I've been using Scala IDE, an Eclipse variant) are easily generated with `sbt eclipse` then you can import the project into the IDE. Be sure to treat the sbt definition as definitive and edit that rather than the IDE's version - then rerun `sbt eclipse`.

No IDE is required - sbt at the command line can do everything:

    sbt run
    sbt test
    sbt compile