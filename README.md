# Compiladores: Go para Mips
Um compilador da linguagem Go


**Autores:** Abraão Santos, Igor Varejão

---
Esse é um compilador que transforma a linguagem Go para a linguagem de montagem MIPS. Para isso foi utilizada
a plataforma MARS para auxiliar na visualização do funcionamento do código fonte gerado pelo compilador.
Nosso compilador é capaz de gerar a partir do código fonte em Go as várias funcionalidade relatadas no CP2. 
Já no funcionamento do backend esses foram as funcionalidades construídas:
+ For loop
+ If statement
+ Operações artiméticas:
  + adição
  + multiplicação
  + subtração
  + divisão
+ Operações Lógicas:
  + E
  + OU
  + Negação
+ Operação Comparativas:
  + Menor que
  + Maior que
  + Maior igual
  + Menor igual
  + Igualdade
  + Diferença
+ Impressão na saída padrão 
+ Escaneio da saída padrão


É importante dizer que essas operações funcionam apenas para o tipo inteiro, com exceção da impressão na saída padrão
que adimite strings estáticas.
Além disso, todas as variáveis são declaradas no campo `.data` do código em MIPS, assim como as string estáticas
em strTable. E, por último mas não menos importante, o código das funções são geradas, contudo, as chamadas de funções
não foram implementadas.

Na pasta `./tests` há testes que executam a funcionalidade do compilador.

--- 
### Execução
Para executar a análise semântica com a geração do código em mips basta executar na linha de comando:
```shell
make clean
make ast
```