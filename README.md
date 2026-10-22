# Doma Assist

Aplicativo para **Wear OS** desenvolvido como trabalho prático da disciplina **DGT2816 — Interação com Sensores de Smartphones e Wearables**, da Estácio.

O Doma Assist foi desenvolvido a partir de uma proposta de acessibilidade para a empresa fictícia Doma, com o objetivo de auxiliar funcionários com necessidades especiais por meio de avisos sonoros, orientações por voz e alertas de segurança.

A interface prioriza interações simples e recursos de áudio, reduzindo a dependência de informações exclusivamente visuais.

**Desenvolvedor:** Daniel Victor Reis

## Funcionalidades

| Funcionalidade | Descrição |
|---|---|
| Ouvir aviso | Reproduz uma mensagem de boas-vindas em português por meio de síntese de voz. |
| Alerta de segurança | Reproduz um aviso de emergência por voz. |
| Ajuda | Reproduz uma mensagem curta de apoio pelo botão lateral da interface. |
| Verificar áudio | Consulta a disponibilidade de alto-falantes e dispositivos Bluetooth. |
| Tocar áudio | Reproduz um arquivo de áudio armazenado no aplicativo. |
| Conectar fone | Abre as configurações de Bluetooth do relógio para pareamento de fones. |
| Monitoramento de áudio | Atualiza o status da interface quando dispositivos de áudio são conectados ou desconectados, conforme os eventos detectados pelo sistema. |

## Tecnologias utilizadas

- **Kotlin** — linguagem de programação.
- **Jetpack Compose** — construção da interface declarativa.
- **Material 3 para Wear OS** — componentes visuais adaptados a relógios inteligentes.
- **Android Studio** — ambiente de desenvolvimento.
- **TextToSpeech** — síntese de voz.
- **MediaPlayer** — reprodução de arquivos de áudio.
- **AudioManager** — consulta às saídas de áudio disponíveis.
- **AudioDeviceCallback** — monitoramento de alterações nos dispositivos de áudio.

## Estrutura do projeto

```text
app/src/main/
├── AndroidManifest.xml
├── java/com/doma/assist/presentation/
│   ├── MainActivity.kt
│   └── AudioHelper.kt
└── res/
    └── raw/
        └── aviso.wav

docs/
└── Documentacao_Doma_Assist.pdf
```

### Arquivos principais

- `MainActivity.kt`: implementa a tela principal e as interações dos botões.
- `AudioHelper.kt`: concentra as funcionalidades de consulta e monitoramento das saídas de áudio.
- `aviso.wav`: arquivo de áudio utilizado na funcionalidade de reprodução sonora.
- `Documentacao_Doma_Assist.pdf`: documentação complementar do projeto.

## Como executar

### Pré-requisitos

- Android Studio instalado.
- SDK do Android compatível com a configuração do projeto.
- Emulador Wear OS configurado no Device Manager.
- Arquivo `aviso.wav` presente no diretório `app/src/main/res/raw/`.

### Passo a passo

1. Clone este repositório:

   ```bash
   git clone https://github.com/nielreis07/DomaAssist.git
   ```

2. Abra o projeto clonado no Android Studio.

3. Aguarde a sincronização do Gradle e a resolução das dependências.

4. No Android Studio, acesse **Gerenciador de Dispositivos (Device Manager)** e inicie um emulador compatível com Wear OS.

5. Selecione a configuração de execução do módulo `app`.

6. Clique em **Executar (Run)** para instalar e iniciar o aplicativo no emulador.

7. Caso o aplicativo não abra automaticamente, acesse a lista de aplicativos do relógio e procure por **Doma Assist**.

> **Importante:** o arquivo `aviso.wav` deve estar presente no caminho `app/src/main/res/raw/`, pois o código utiliza o recurso `R.raw.aviso`.

## Testes e limitações do emulador

O projeto foi executado em um emulador Wear OS para verificar o funcionamento da interface e das interações disponíveis.

Durante os testes, foram observadas as seguintes limitações:

- O emulador não disponibiliza um ambiente Bluetooth equivalente ao de um relógio físico para testar o pareamento real de fones.
- A tela de configurações Bluetooth pode ser aberta, mas não foi possível concluir o pareamento com um dispositivo físico.
- O comportamento de conexão e desconexão de dispositivos de áudio depende dos eventos disponibilizados pelo sistema e precisa ser validado em um relógio real para confirmar o funcionamento com fones Bluetooth.

Assim, a implementação de monitoramento de áudio está presente no projeto, mas o teste completo com dispositivos Bluetooth físicos permanece pendente.

## Documentação

A documentação complementar, com a explicação do projeto e as capturas de tela, está disponível em:

[`docs/Documentacao_Doma_Assist.pdf`](docs/Documentacao_Doma_Assist.pdf)

## Objetivo acadêmico

Este projeto foi desenvolvido para aplicar conceitos de desenvolvimento para dispositivos vestíveis, construção de interfaces com Jetpack Compose, acessibilidade por áudio e interação com os recursos de hardware disponibilizados pelo Android.

---

Desenvol
