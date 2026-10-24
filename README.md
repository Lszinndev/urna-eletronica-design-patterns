# 🗳️ Simulador de Urna Eletrônica e Sistema de Apuração de Votos

Aplicação em **Java puro** que simula uma seção eleitoral pelo terminal: cadastro de candidatos, abertura da seção com Zerésima, liberação de eleitores, votação (nominal, branco ou nulo), encerramento com Boletim de Urna e apuração com 1º/2º turno e desempate por idade.

O projeto aplica **6 Design Patterns GoF**, 2 de cada categoria.

## 🧩 Padrões de Projeto

| Padrão | Categoria | Problema de negócio resolvido |
| ------ | --------- | ----------------------------- |
| **Builder** | Criacional | Monta o `Candidato` passo a passo (nome, número, partido e idade), validando os dados antes de criar o objeto e evitando construtores gigantes. |
| **Singleton** | Criacional | Garante uma única instância de `CofreDeVotos`, centralizando candidatos, votos nominais, brancos e nulos da seção. |
| **Adapter** | Estrutural | Converte a resposta numérica (`1`/`0`) do `SistemaLegadoTSE` para o `boolean` esperado pela interface `ValidadorEleitor` da urna. |
| **Facade** | Estrutural | `TerminalMesarioFacade` coordena tela, áudio e impressora com um único comando para abrir a seção (Zerésima) ou encerrá-la (Boletim de Urna). |
| **State** | Comportamental | Controla o ciclo da urna (`Bloqueada`, `Liberada para Voto`, `Encerrada`), impedindo votos sem liberação do mesário ou após o encerramento. |
| **Strategy** | Comportamental | Permite escolher em tempo de execução a regra de apuração: `MaioriaSimplesStrategy` ou `SegundoTurnoAbsolutoStrategy` (50% + 1, 2º turno e desempate por idade). |

## 📂 Estrutura (4 camadas)

```text
src/main/java/br/edu/urna/
├── domain/      Candidato, UrnaContexto
├── patterns/
│   ├── creational/   builder/CandidatoBuilder, singleton/CofreDeVotos
│   ├── structural/   adapter/(ValidadorEleitor, SistemaLegadoTSE, ValidadorEleitorAdapter), facade/TerminalMesarioFacade
│   └── behavioral/   state/(EstadoUrna + 3 estados), strategy/(RegraApuracaoStrategy + 2 regras)
├── service/     EleicaoService, AudioUrnaService, TelaUrnaService, ImpressoraService
└── main/        Main (menu interativo com Scanner)
```

## ⚖️ Regras de negócio

- **Voto em branco:** digitar `0`.
- **Voto nominal:** digitar o número de um candidato cadastrado.
- **Voto nulo:** digitar qualquer número que não pertence a nenhum candidato.
- **Votos válidos** = total de votos − (brancos + nulos). Brancos e nulos não entram na apuração.
- **Título irregular:** o sistema legado do TSE considera irregular todo título terminado em `0`.
- **Um voto por eleitor:** o mesmo título não pode ser liberado duas vezes.
- **1º turno:** é eleito quem atingir `votos válidos / 2 + 1`.
- **2º turno:** se ninguém atingir a meta, os dois mais votados disputam o 2º turno. Em empate exato, vence o **candidato mais idoso**.

## 🖥️ Menu

```text
[1] Cadastrar Candidato (Builder)
[2] Abrir Seção / Emitir Zerésima (Facade)
[3] Validar Eleitor e Liberar Urna (Adapter + State)
[4] Cabine de Votação (State + Singleton)
[5] Encerrar Votação (Facade + State)
[6] Apurar Resultados (Strategy + Singleton)
[0] Sair
```

Cada padrão imprime um log no terminal quando é acionado, por exemplo `[BUILDER]`, `[SINGLETON]`, `[ADAPTER]`, `[FACADE]`, `[STATE]` e `[STRATEGY]`.

## ▶️ Como executar

Requisito: Java 17 ou superior.

```bash
javac -encoding UTF-8 -d out $(find src/main/java -name "*.java")
java -cp out br.edu.urna.main.Main
```

Ou abra o projeto na IDE (IntelliJ/Eclipse/VS Code) e execute a classe `br.edu.urna.main.Main`.

### Roteiro rápido de teste

1. Cadastre 2 ou 3 candidatos (opção 1).
2. Abra a seção (opção 2) e veja a Zerésima.
3. Libere um eleitor (opção 3) e vote (opção 4). Repita algumas vezes, testando branco (`0`), nulo (ex.: `99`) e um título terminado em `0`.
4. Encerre a votação (opção 5) e veja o Boletim de Urna.
5. Apure (opção 6) escolhendo a regra. Na regra de 2º turno, informe votos iguais para ver o desempate por idade.
