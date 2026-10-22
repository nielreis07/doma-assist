Doma Assist

Aplicativo para Wear OS feito como trabalho prático da disciplina DGT2816 - Interação com sensores de smartphones e wearables.

A ideia vem do enunciado: a empresa "Doma" quer um app de relógio que ajude funcionários com necessidades especiais, usando áudio para avisar, orientar e alertar. Por isso o app foi pensado em cima de voz e som, e não de telas cheias de texto.

Aluno: Daniel Victor Reis

O que o app faz

A tela principal tem um texto de status e alguns botões:

Botão	O que acontece
Ouvir aviso	O relógio fala uma mensagem de boas-vindas em português
Alerta de segurança	O relógio fala um aviso de emergência
Ajuda (botão da borda)	Fala uma frase curta de apoio
Verificar áudio	Mostra se o relógio tem alto-falante e/ou Bluetooth disponível
Tocar áudio	Toca um arquivo de som que está dentro do app
Conectar fone	Abre as configurações de Bluetooth do relógio para parear um fone

Além disso, o app percebe sozinho quando um fone Bluetooth é conectado ou desconectado e avisa no texto de status.

Tecnologias
Kotlin
Jetpack Compose para Wear OS (Material 3)
Android Studio com emulador Wear OS (Doma API 37.0)
TextToSpeech para a voz
MediaPlayer para tocar o arquivo de áudio
AudioManager e AudioDeviceCallback para detectar as saídas de áudio
Arquivos principais
app/src/main/
├── AndroidManifest.xml
├── java/com/doma/assist/presentation/
│   ├── MainActivity.kt      tela e botões
│   └── AudioHelper.kt       consulta e monitora as saídas de áudio
└── res/raw/
    └── aviso.wav            som usado no botão "Tocar áudio"
Como rodar
Abrir o projeto no Android Studio.
Conferir se o arquivo aviso.wav está em app/src/main/res/raw/. Sem ele o projeto não compila, porque o código usa R.raw.aviso.
Criar (ou usar) um emulador Wear OS pelo Device Manager.
Rodar o módulo app.
Se o app não abrir sozinho, clicar no botão 1 da barra do emulador para abrir a lista de apps e procurar "Doma Assist".
Observações sobre os testes

O emulador do Wear OS não tem Bluetooth de verdade. Ao tocar em "Conectar fone", a tela de pareamento abre normalmente, mas fica procurando dispositivos e não encontra nenhum. Por isso a parte que detecta a conexão do fone (registerAudioDeviceCallback) está implementada seguindo o roteiro, mas não dá para ver ela disparando no emulador. Isso só seria possível com um relógio real e um fone.

As capturas de tela estão somente na documentação em PDF.

Documentação

O PDF com a explicação completa está em docs/Documentacao_Doma_Assist.pdf.
