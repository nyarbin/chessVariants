# Makefile for Chess variants
SRCFILES = \
	Board.java \
	Direction.java \
	Generator.java \
	Move.java \
	Piece.java \
	Symmetry.java

JC = javac
JVM = java
JFLAGS = -cp ".:gson-2.8.0.jar"
MAIN = Generator

all: $(SRCFILES)
	$(JC) $(JFLAGS) $(SRCFILES)
run:
	$(JVM) $(JFLAGS) $(MAIN)
clean:
	rm -f *.class *.log
