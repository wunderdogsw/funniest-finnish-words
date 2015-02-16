Description
-----------
- This little project solves Wunderdog's coding puzzle http://wunderdog.fi/koodaus-hassuimmat-sanat/.
- Goal is to resolve funniest Finnish words from novel "Alastalon Salissa" by Volter Kilpi.
- Implementation optimizes LOC and readability. Not performance, testability or reliablity.
- Also, I wanted to check how we can mix functional programming style with Java in solving this.

Prerequisites
-------------
- To run this code you need Java 8 and Gradle installed on your machine.

```
$ sudo brew cask install java
$ sudo brew cask install gradle
```

Running the code
----------------

```
- Print out funniest words from src/test/resources/alastalon_salissa.txt.

```
$ cd wunderdog-funny-finnish-words-java8
$ gradle run
:compileJava UP-TO-DATE
:processResources UP-TO-DATE
:classes UP-TO-DATE
:run
seremoniioissa
liioittelematta
seremoniioilla
leeaakaan
puuaineen
niiaamaan

BUILD SUCCESSFUL

Total time: 3.05 secs
```

- Run tests

```
$ cd wunderdog-funny-finnish-words-java8
$ gradle test
:compileJava UP-TO-DATE
:processResources UP-TO-DATE
:classes UP-TO-DATE
:compileTestJava
:processTestResources UP-TO-DATE
:testClasses
:test

BUILD SUCCESSFUL

Total time: 6.559 secs
```
