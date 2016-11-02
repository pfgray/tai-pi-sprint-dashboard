# TAI Sprint Dashboard Webapp #

## Build & Run ##

```sh
$ cd TAI_Sprint_Dashboard_Webapp
$ ./sbt
> jetty:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.

After making changes, to auto reload:

```sh
> ~;jetty:stop;jetty:start
```

## Compile fat jar ###

```sh
$ ./sbt
> assembly
```
