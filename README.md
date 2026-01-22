# Orientações Gerais 🚨

- Utilize apenas **tipos wrapper** para criar atributos e métodos.
- Respeite **rigorosamente** os nomes de atributos e métodos definidos no exercício.
- Tome cuidado com os **argumentos especificados** no exercício:
    - Não adicione argumentos não solicitados.
    - Mantenha a **ordem definida** no enunciado.
- Verifique se **não há erros de compilação** no projeto antes de enviar.
- As classes devem seguir corretamente as **regras de encapsulamento**.

---

# 📘 Sistema de Duelo de Cartas

## 🎯 Objetivo Geral

Desenvolver um sistema de duelo de cartas, inspirado no jogo de batalha por turnos **Monstros de Duelo**, onde duelistas utilizam cartas com atributos de ataque, defesa e estrelas para invocar monstros, atacar o oponente e reduzir seus pontos de vida até a vitória.

> **OBS:**  
> O objetivo não é criar o jogo completo, mas sim um sistema que modele as principais funcionalidades e regras do duelo de cartas, de forma muito simplificada. Caso queira implementar as regras do jogo real aqui, faça isso depois de ter entregado a atividade, para que os testes não falhem.

---

# 🃏 Classe Carta

## 📌 Responsabilidade

A classe `Carta` representa uma unidade básica do jogo, sendo o elemento central de todas as ações realizadas pelos duelistas.

---

## 🧱 Atributos

A classe `Carta` deve possuir exatamente os seguintes atributos privados:

- `String nome`  
  Nome da carta.

- `Integer qtdEstrela`  
  Quantidade de estrelas da carta, utilizada para definir regras de invocação e sacrifício.

- `Integer ataque`  
  Valor de ataque da carta, utilizado durante batalhas ofensivas.

- `Integer defesa`  
  Valor de defesa da carta, utilizado durante batalhas defensivas.

---

## 🔨 Construtor

Esse construtor precisa ser público por causa dos testes, mas **não deve ser utilizado diretamente** para criar cartas no sistema. Utilize o método de fábrica para isso.

```
public Carta(String nome, Integer qtdEstrela, Integer ataque, Integer defesa)
```
---

## 🏭 Método de Fábrica

```
public static Carta criarCarta(String nome, Integer qtdEstrela, Integer ataque, Integer defesa)
```

**Este é o único meio permitido para criação válida de cartas no sistema.**

### Validações Obrigatórias:

- Validações que devem lançar **NullPointerException**:
    - nome
    - qtdEstrela
    - ataque
    - defesa


- Validações que devem lançar **IllegalArgumentException**:
  - **nome** não pode ser:
    - vazio ("")
    - composto apenas por espaços
    - texto em branco (isBlank())
  - **qtdEstrela**:
    - deve ser maior que 0
    - deve ser menor ou igual a 12
  - **ataque**:
    - não pode ser negativo
  - **defesa**:
    - não pode ser negativa

---

## 🔎 Getters e toString

A classe deve expor somente métodos de leitura, não sendo permitido alterar atributos após a criação da carta, ou seja, **não deve haver setters**.
E o toString() pode ser customizado da maneira que preferir, portanto que tenha todas as informações da Carta.

---

🗂️ Classe Fichario

📌 Responsabilidade

A classe Fichario representa um repositório organizado de cartas disponíveis, funcionando como uma coleção controlada onde cartas podem ser adicionadas, removidas e exibidas.

Ela não executa lógica de jogo, não compra cartas, não ataca, não invoca monstros.
Seu papel é armazenar e disponibilizar cartas, nada além disso.

🧱 Atributos

A classe deve possuir exatamente um atributo privado:

private List<Carta> cartas;

🔨 Construtor
public Fichario()

Comportamento esperado:

Deve inicializar a lista cartas

A lista deve começar vazia

Nenhum parâmetro deve ser exigido

public void adicionarCarta(Carta novaCarta)
Comportamento:
Adiciona a carta recebida à lista interna

Não realiza validações adicionais

A ordem de inserção deve ser preservada

adicionarCartas (varargs)
public void adicionarCartas(Carta... novasCartas)

Comportamento:

Deve adicionar todas as cartas recebidas

Deve reutilizar o método adicionarCarta

Deve respeitar a ordem dos argumentos

removerCarta
public void removerCarta(Carta carta)

Comportamento:

Remove a carta informada da lista

Caso a carta não exista, nada deve acontecer

Não deve lançar exceções

removerCartas (varargs)
public void removerCartas(Carta... novasCartas)

Comportamento:

Deve remover todas as cartas informadas

Deve reutilizar o método removerCarta

Deve funcionar corretamente mesmo se algumas cartas não existirem

exibirCartasDisponiveis
public void exibirCartasDisponiveis()

Comportamento esperado:

Deve imprimir no console:

Um título indicando que são cartas disponíveis

A lista numerada de cartas

A numeração deve começar em 1

Cada carta deve ser exibida utilizando o toString() da classe Carta

getCartas
public List<Carta> getCartas()

Comportamento:

Retorna a lista interna de cartas

A lista retornada representa o estado atual do fichário

⚔️ Classe Duelista
📌 Papel no Sistema

Duelista é uma classe base abstrata que representa qualquer entidade capaz de duelar:

Jogador humano

Computador

Qualquer futura IA, NPC ou modo especial

Ela define as regras comuns, o estado compartilhado e o contrato que os filhos devem respeitar.

🧱 Atributos Obrigatórios

Um Duelista sempre deve possuir:

protected String nome;
protected int pontosDeVida;
protected Deque<Carta> baralho;
protected List<Carta> mao;
protected List<Carta> campo;

Regras inegociáveis:

pontosDeVida inicia em 4000

baralho deve permitir remoção pelo início (removeFirst)

mao representa cartas na mão

campo representa cartas invocadas

🔨 Construtor
public Duelista(String nome)

Comportamento esperado:

Inicializa:

nome

pontosDeVida = 4000

baralho vazio

mao vazia

campo vazio

🔨 Construtor
public Duelista(String nome)

Comportamento esperado:

Inicializa:

nome

pontosDeVida = 4000

baralho vazio

mao vazia

campo vazio

🔎 Getters Permitidos

A classe pode e deve expor:

getNome()
getPontosDeVida()
getBaralho()
getMao()
getCampo()

🎴 Jogar Carta (invocação manual)
public void jogarCarta(List<Integer> posicoesCartasEscolhidas)

Regras:

Se a mão estiver vazia → retorna

A carta escolhida:

Deve ser removida da mão

Deve ir para o campo

Deve respeitar:

Regras de sacrifício

Estrelas da carta

🩸 Sacrifício
Método fundamental:
protected boolean precisaDeSacrifio(Carta carta)

Estrelas	Sacrifícios
≤ 4	0
5–6	1
≥ 7	2

📌 Regra absoluta do jogo.
Nunca deve ser duplicada em outro lugar.

🐲 Invocação
Sem sacrifício
protected void invocarCarta(Carta carta)


Remove da mão

Adiciona ao campo

Com sacrifício
protected void invocarCarta(Carta carta, int qtdSacrificios, List<Carta> sacrificios)


Remove os monstros sacrificados:

Da mão ou do campo

Remove a carta invocada da mão

Adiciona a carta invocada ao campo

⚔️ Ataque
Ataque direto
public void receberAtaque(Carta atacante)


Reduz pontosDeVida pelo ataque da carta

Nunca deixa vida negativa causar erro

Ataque entre monstros
public void receberAtaque(Carta atacante, Carta defensora, Duelista atacanteDuelista)


Casos possíveis:

Situação	Resultado
Ataque > Defesa	defensor perde monstro + perde vida
Ataque < Defesa	atacante perde vida
Ataque == Defesa	ambos perdem monstro

📌 A lógica deve ser exata e imutável.

🧑‍🎤 Classe Jogador
📌 Papel no Sistema

Jogador representa o duelista humano.

Ela:

herda todas as regras de Duelista

não decide sozinha

depende de entrada externa (IO)

🧬 Herança
public class Jogador extends Duelista

Regras obrigatórias:

Não redefine atributos

Não redefine regras de ataque ou sacrifício

Apenas orquestra decisões humanas

🔨 Construtor
public Jogador(String nome)

Deve:

Chamar super(nome)

Não inicializar nada além do que Duelista já faz

🃏 Montar Baralho
public void montarBaralho(List<Carta> cartas)

Comportamento esperado:

Recebe cartas disponíveis (do Fichario)

Permite ao jogador escolher cartas

Popula baralho

🛒 Comprar Carta
@Override
public void comprarCarta()

Responsabilidade:

Exibir opções

Chamar a lógica de compra

Nunca quebrar regra do Duelista

🎴 Jogar Carta
@Override
public void jogarCarta(List<Integer> posicoesCartasEscolhidas)

Diferença para o Computador:

Escolha vem de IO.readln

Pode errar

Pode escolher posição inválida

⚔️ Atacar
@Override
public void atacar(Duelista oponente)

Regras:

Se não houver cartas no campo → não ataca

Jogador escolhe:

Carta atacante

Carta defensora (se houver)

🤖 Classe Computador
📌 Papel no Sistema

Computador representa o duelista controlado pela máquina.

Ele:

herda integralmente as regras de Duelista

não depende de IO do usuário

toma decisões com base no estado do jogo

🧬 Herança
public class Computador extends Duelista

Consequências diretas:

Compartilha pontos de vida, campo, mão e baralho

Usa os mesmos métodos de ataque e invocação

Nunca altera regras centrais

🔨 Construtor
public Computador(String nome)

Regras:

Apenas chama super(nome)

🃏 Montar Baralho do Computador
public void montarBaralho(List<Carta> cartas, Integer tamanhoBaralhoJogador)

Regras de negócio:

Recebe o fichário completo

Embaralha as cartas

Seleciona automaticamente:

Quantidade igual ao baralho do jogador

🛒 Comprar Carta
@Override
public void comprarCarta()

Comportamento:

Sempre tenta comprar do topo

Se o baralho estiver vazio:

Apenas informa

Não lança exceção

🎴 Jogar Carta (decisão automática)
@Override
public void jogarCarta(List<Integer> posicoesCartasEscolhidas)

Estratégia básica:

Filtra cartas já utilizadas no turno

Escolhe aleatoriamente entre as válidas

Avalia necessidade de sacrifício

Invoca se possível

Caso contrário:

Reavalia

Ou pula a jogada

📌 Nunca pode entrar em loop infinito.

🎯 Jogada Especial
public Carta jogadaEspecial(Predicate<Carta> condicao)

Função estratégica:

Permite escolher cartas com base em critérios

Exemplo:

Mais estrelas

Sem sacrifício

Ataque alto

⚔️ Atacar (modo automático)
Versão padrão
@Override
public void atacar(Duelista oponente)

Escolhe carta atacante aleatoriamente

Se o oponente não tiver campo:

Ataque direto

Caso contrário:

Escolhe alvo aleatório

Versão estratégica
public void atacar(Duelista oponente, Carta alvo)

Regras:

Seleciona a carta mais forte do campo

Ataca um alvo previamente escolhido

Usada quando:

Há vantagem clara

O alvo é derrotável

🧠 Execução de Turno
public void executarTurno(Duelista oponente)

Fluxo de decisão:

Sem mão e sem campo → comprar carta

Com mão e sem campo → jogada inicial

Com campo → avaliar ataque ou jogada

🧠 Jogadas Internas
Jogada Inicial
private void executarJogadaInicial()

Critérios:

Prioriza cartas fortes

Avalia sacrifícios possíveis

Evita jogadas inviáveis

Jogada com Campo
private void executarJogadaComCampo(Duelista oponente)

Ataca se houver vantagem

Caso contrário:

Compra

Ou joga carta

🎯 Escolha de Alvo Derrotável
private Carta escolherAlvoDerrotavel(Duelista oponente)

Regra clara:

Só considera cartas que podem ser derrotadas

Prioriza a mais fraca
