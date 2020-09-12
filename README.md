# Linear Equations Solver

Can be use for solve real or complex linear equations.

## Description

A java console program that solves linear equations that handle complex numbers. This program
use **Gauss Jordan** algorithm to reduce the matrix of coefficients. The input is a text file 
with the size and coeficients of the linear equations, and output a text file with the solutions 
of the system.

## Getting Started

### Dependencies

* Gradle
* JDK >= 1.8

### Installing

* Clone this repository
* Windows:
```
gradlew.bat build
```
* Linux
```
gradle build
```
* Find the file _solver.jar_ in the directory ./build/libs/
* Use the jar file to run the program

### Executing program

* How to run using the _solver.jar_
The program need 2 parameters: a mandatory input text file name with a specific format describe below
and an optional output file name where the solution it will be save. The way to call is:
```
java -jar solver.jar -in <filemane> [-out <filename>]
```

#### Input text file format
This text file contain the dimension of the linear equation (number of equations and number of variables) 
in the first line, inmediatly in the next line the coeficients separated by a space of the equations plus 
the constant value of the equation. For example for the real system equation:

2x -3y + z= 1

4x +5y - 4z= -5

-3x +2y +5z = 0

You have to write the input file as follow
```
3 2
2 -3 1 1
4 5 -4 -5
-3 2 5 0
```

In the case of complex coeficients, you have to write the coeficients separate by a space between coefficiets
but DO NOT add space that separate the complex part from the real part. That means that the complex parts
have to have the plus or minus sign when also have a real part. To indicate the complex part only add an **i** 
to the number.

(1 + 2i)x + (-1.5-1.1)y + 2.12z = 91 + 5i

(-1 + 3i)x + (1.2 + 3.5i)y -3.3z = 1 + 15i

12.31x (1.3 - 5i)y + 12.31i = -78.3i

You have to write to the input file as follow

```
3 3
1+2i -1.5-1.1i 2.12 91+5i
-1+3i 1.2+3.5i -3.3 1+15i
12.31 1.3-5i 12.3i -78.3i
```

## Version History

* 0.1
    * Initial Release

## License

This project is licensed under the [MIT] License

## Acknowledgments

Inspiration, code snippets, etc.
* [awesome-readme](https://github.com/matiassingers/awesome-readme)
* [PurpleBooth](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2)
* [dbader](https://github.com/dbader/readme-template)
* [zenorocha](https://gist.github.com/zenorocha/4526327)
* [fvcproductions](https://gist.github.com/fvcproductions/1bfc2d4aecb01a834b46)