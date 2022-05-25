rm -rf src-parser
java -jar /usr/local/lib/antlr-4.10.1-complete.jar -no-listener -o src-parser GoLexer.g4 GoParser.g4
javac -cp .:/usr/local/lib/antlr-4.10.1-complete.jar ParserBase/GoParserBase.java src-parser/*.java
cp ParserBase/GoParserBase.class src-parser/
cd src-parser
java org.antlr.v4.gui.TestRig Go sourceFile ../tests/in/in.go -gui