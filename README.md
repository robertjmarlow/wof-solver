# wof-solver
Java swing project that will help to solve puzzles on "Wheel of Fortune"

# Building
wof-solver uses [Maven](http://maven.apache.org/) for its build lifecycle. Download and install the [latest version of Maven](http://maven.apache.org/download.cgi).

Download and install a JDK [from Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html). The latest version is recommended, but anything above 1.5 should work fine.

Clone this repo onto a local drive. From the project directory, run `mvn clean package`. If everything goes well, there should be a directory created at `./target/appassembler/bin`. Navigate to here and run `wof-solver.bat` or `wof-solver`, depending on your operating system (Windows or Unix families, respectively).
