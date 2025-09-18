# Sistema de Manutenção Industrial

## Descrição do Projeto
Este projeto é um protótipo funcional de um **sistema de manutenção industrial**, desenvolvido em **Java** utilizando **JDBC** para manipulação de banco de dados MySQL. O sistema permite gerenciar máquinas, técnicos, peças de reposição e ordens de manutenção, simulando um ambiente corporativo real.

O sistema foi desenvolvido para atender às seguintes funcionalidades:
- Cadastro de máquinas e setores
- Cadastro de técnicos e suas especialidades
- Cadastro de peças de reposição com controle de estoque
- Criação de ordens de manutenção
- Associação de peças às ordens
- Execução de manutenção com atualização de status da máquina e da ordem

---

## Tecnologias Utilizadas
- **Java 21**  
- **JDBC** (Java Database Connectivity)  
- **MySQL 8.0**  
- **IDE:** IntelliJ IDEA  
- Dependência do Maven: `mysql-connector-j:8.0.33`

---

## Estrutura do Banco de Dados

```sql
-- Tabela de Máquinas
CREATE TABLE Maquina (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    setor VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL -- OPERACIONAL / EM_MANUTENCAO
);

-- Tabela de Técnicos
CREATE TABLE Tecnico (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    especialidade VARCHAR(50)
);

-- Tabela de Peças
CREATE TABLE Peca (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    estoque DOUBLE NOT NULL
);

-- Tabela de Ordens de Manutenção
CREATE TABLE OrdemManutencao (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idMaquina INT NOT NULL,
    idTecnico INT NOT NULL,
    dataSolicitacao DATE NOT NULL,
    status VARCHAR(20) NOT NULL, -- PENDENTE / EXECUTADA / CANCELADA
    FOREIGN KEY (idMaquina) REFERENCES Maquina(id),
    FOREIGN KEY (idTecnico) REFERENCES Tecnico(id)
);

-- Tabela de Peças utilizadas em cada ordem
CREATE TABLE OrdemPeca (
    idOrdem INT NOT NULL,
    idPeca INT NOT NULL,
    quantidade DOUBLE NOT NULL,
    PRIMARY KEY (idOrdem, idPeca),
    FOREIGN KEY (idOrdem) REFERENCES OrdemManutencao(id),
    FOREIGN KEY (idPeca) REFERENCES Peca(id)
);
```
---

## Funcionalidades do Sistema

### 1 Cadastrar Máquina
- Solicita **nome** e **setor** da máquina  
- Valida **duplicidade** no mesmo setor  
- Status inicial: **OPERACIONAL**

### 2️ Cadastrar Técnico
- Solicita **nome** e **especialidade**  
- Evita cadastro **duplicado**

### 3️ Cadastrar Peça
- Solicita **nome** e **quantidade inicial**  
- Valida **duplicidade** e **estoque ≥ 0**

### 4️ Criar Ordem de Manutenção
- Seleciona máquina disponível (**status OPERACIONAL**)  
- Seleciona técnico  
- Status inicial da ordem: **PENDENTE**  
- Atualiza status da máquina para **EM_MANUTENCAO**

### 5️ Associar Peças à Ordem
- Seleciona ordem **pendente**  
- Lista **peças disponíveis**  
- Permite associar **várias peças**  
- Atualiza **estoque** das peças automaticamente

### 6️ Executar Manutenção
- Verifica **estoque de todas as peças** da ordem  
- Atualiza estoque e status da ordem para **EXECUTADA**  
- Atualiza status da máquina para **OPERACIONAL**

### 0️ Sair
- Encerra a aplicação

---

## 📋 Regras de Negócio

- Não é permitido cadastrar **máquinas ou peças duplicadas**  
- Ordens só podem ser criadas para máquinas **OPERACIONAIS**  
- Ordem só pode receber peças se estiver **PENDENTE**  
- Quantidade **negativa** de peças não é permitida  
- Todas as peças devem ter **estoque suficiente** para executar a manutenção
