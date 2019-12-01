JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = AssignNode.java CompNode.java CompOpNode.java CondNode.java DeclNode.java \
DeclSeqNode.java Executer.java ExprNode.java FacNode.java IdListNode.java \
IdNode.java IfNode.java InputNode.java LoopNode.java OutputNode.java \
ProgramNode.java StmtNode.java StmtSeqNode.java TermNode.java Tokenizer.java
default: classes
classes: $(CLASSES:.java=.class)
clean:
	$(RM) *.class
