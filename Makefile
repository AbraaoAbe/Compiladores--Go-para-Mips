# Modifique as variaveis conforme o seu setup.

JAVA=java
JAVAC=javac

ROOT=$(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
# Certifique-se de que o antlr esteja instalado em /usr/local/lib

ANTLR_PATH=/mnt/c/Users/AbraaoAbe/Documents/UFES/Trab_comp/Compiladores--Go-para-Mips/antlr-4.10.1-complete.jar
#ANTLR_PATH=${ROOT}/antlr-4.10.1-complete.jar
CLASS_PATH_OPTION=-cp .:$(ANTLR_PATH)

CP=-cp .;$(ANTLR_PATH)

# Comandos como descritos na página do ANTLR.
ANTLR4=$(JAVA) -jar $(ANTLR_PATH)
GRUN=$(JAVA) $(CP) org.antlr.v4.gui.TestRig

# Diretório para aonde vão os arquivos gerados.
GEN_PATH=parser
MAIN_PATH=checker
BIN_PATH=bin

# Diretório para os casos de teste
#DATA=$(ROOT)/tests
DATA=/mnt/c/Users/AbraaoAbe/Documents/UFES/Trab_comp/Compiladores--Go-para-Mips
IN=$(DATA)/tests
FILE=$(IN)/simple.go
OUT=./ast.txt

all: antlr javac
	@echo "Done."

# Opção -no-listener foi usada para que o ANTLR não gere alguns arquivos
# desnecessários para o momento. Isto será explicado melhor nos próximos labs.
antlr: GoLexer.g4 GoParser.g4
	$(ANTLR4) -no-listener -visitor -o $(GEN_PATH) GoLexer.g4 GoParser.g4
	cp GoParserBase.java $(GEN_PATH)

javac:
	rm -rf $(BIN_PATH)
	mkdir $(BIN_PATH)
	$(JAVAC) $(CLASS_PATH_OPTION) -d $(BIN_PATH) */*.java
	#cp ParserBase/GoParserBase.class $(GEN_PATH)

# 'Go' é o prefixo comum das duas gramáticas (GoLexer e GoParser).
# 'sourceFile' é a regra inicial de GoParser.
run:
	cd $(GEN_PATH) && $(GRUN) Go sourceFile $(FILE) && \
	echo "\e[33mRunning ${FILE} \e[0m" && \
	cd ..

# Executa o grun com a opção de mostrar os tokens
run-tokens:
	cd $(GEN_PATH) && $(GRUN) Go sourceFile $(FILE) -tokens
	cd ..

# Executa o grun com a opção de mostrar a árvore com GUI
run-gui:
	cd $(GEN_PATH) && $(GRUN) Go sourceFile $(FILE) -gui
	cd ..

# Executa todos os testes
runall:
	-for FILENAME in $(IN)/*.go; do \
		cd $(GEN_PATH) && \
		echo "\e[34m \nRunning $${FILENAME} \e[0m" && \
		$(GRUN) Go sourceFile $${FILENAME} && \
		cd .. ; \
	done;

run-ast:
	$(JAVA) $(CLASS_PATH_OPTION):$(BIN_PATH) $(MAIN_PATH)/Main $(FILE)

clean:
	@rm -rf $(GEN_PATH) $(BIN_PATH) $(OUT)