## Modifique as variaveis conforme o seu setup.
#
#JAVA=java
#JAVAC=javac
#
#ROOT=$(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
## Certifique-se de que o antlr esteja instalado em /usr/local/lib
#
#ANTLR_PATH=/usr/local/lib/antlr-4.10.1-complete.jar
##ANTLR_PATH=${ROOT}/antlr-4.10.1-complete.jar
#CLASS_PATH_OPTION=-cp .:$(ANTLR_PATH)
#
## Comandos como descritos na página do ANTLR.
#ANTLR4=$(JAVA) -jar $(ANTLR_PATH)
#GRUN=$(JAVA) $(CLASS_PATH_OPTION) org.antlr.v4.gui.TestRig
#
## Diretório para aonde vão os arquivos gerados.
#GEN_PATH=parser
#MAIN_PATH=checker
#BIN_PATH=bin
#
## Diretório para os casos de teste
##DATA=$(ROOT)/tests
#DATA=/home/igor/Desktop/ztests
#IN=$(DATA)
#FILE=$(IN)/func.go
#OUT=./tree.dot
#
#
## 'Go' é o prefixo comum das duas gramáticas (GoLexer e GoParser).
## 'sourceFile' é a regra inicial de GoParser.
#
#run-ast:
#	$(JAVA) $(CLASS_PATH_OPTION):$(BIN_PATH) $(MAIN_PATH)/Main $(FILE)
#
#clean:
#	@rm -rf $(GEN_PATH) $(BIN_PATH) $(OUT)

# Modifique as variaveis conforme o seu setup.

JAVA=java
JAVAC=javac

# Eu uso ROOT como o diretório raiz para os meus labs.
ROOT=$(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))

ANTLR_PATH=/usr/local/lib/antlr-4.10.1-complete.jar
CLASS_PATH_OPTION=-cp .:$(ANTLR_PATH)

# Comandos como descritos na página do ANTLR.
ANTLR4=$(JAVA) -jar $(ANTLR_PATH)
GRUN=$(JAVA) $(CLASS_PATH_OPTION) org.antlr.v4.gui.TestRig

# Diretório para aonde vão os arquivos gerados pelo ANTLR.
GEN_PATH=parser
MAIN_PATH=checker
BIN_PATH=bin

# Diretório para os casos de teste
DATA=/home/igor/Desktop/ztests
IN=$(DATA)
FILE=$(IN)/HelloWorld.go
TREE=./tree.dot
CODE=./code.asm

all: antlr javac
	@echo "Done."

# Como dito no README, o ANTLR provê duas funcionalidades para se caminhar
# na parse tree: um listener e um visitor. Por padrão, o ANTLR gera um listener
# mas não gera um visitor. Como a gente quer o contrário, temos de passar
# as opções -no-listener -visitor.
# Opção -no-listener foi usada para que o ANTLR não gere alguns arquivos
# desnecessários para o momento. Isto será explicado melhor nos próximos labs.
antlr: GoLexer.g4 GoParser.g4
	$(ANTLR4) -no-listener -visitor -o $(GEN_PATH) GoLexer.g4 GoParser.g4
	cp GoParserBase.java $(GEN_PATH)

# Compila todos os subdiretórios e joga todos os .class em BIN_PATH pra organizar.
javac:
	rm -rf $(BIN_PATH)
	mkdir $(BIN_PATH)
	$(JAVAC) $(CLASS_PATH_OPTION) -d $(BIN_PATH) */*.java

ast: antlr ast-javac
	$(JAVA) $(CLASS_PATH_OPTION):$(BIN_PATH) $(MAIN_PATH).Main $(FILE)
	@echo "Done."

ast-javac:
	rm -rf $(BIN_PATH)
	mkdir $(BIN_PATH)
	$(JAVAC) $(CLASS_PATH_OPTION) -d $(BIN_PATH) */*.java
	#cp ParserBase/GoParserBase.class $(GEN_PATH)

run:
	$(JAVA) $(CLASS_PATH_OPTION):$(BIN_PATH) $(MAIN_PATH).Main $(FILE)

#tm: tm.c tables.c types.c
#	gcc -Wall -Wconversion -o tmsim tm.c tables.c types.c
mips:
	java -jar Mars4_5.jar sm $(CODE)

clean:
	@rm -rf $(GEN_PATH) $(BIN_PATH) out07 tmsim $(TREE) $(CODE)