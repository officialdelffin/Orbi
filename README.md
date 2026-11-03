# Orbi

<p align="center">
  <img src="app/src/main/res/drawable/img_orbi.webp" alt="Orbi" width="160" />
</p>

<p align="center">
  <strong>Tempo que se adapta a você.</strong><br />
  Ciclos de foco e pausa personalizados, sem a rigidez dos cronômetros tradicionais.
</p>

<p align="center">
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-2.0-7F52FF?style=flat&logo=kotlin&logoColor=white" />
  &nbsp;&nbsp;
  <img alt="Android" src="https://img.shields.io/badge/Android-API%2024%2B-3DDC84?style=flat&logo=android&logoColor=white" />
  &nbsp;&nbsp;
  <img alt="Firebase" src="https://img.shields.io/badge/Firebase-Auth%20%2B%20Firestore-FFCA28?style=flat&logo=firebase&logoColor=black" />
  &nbsp;&nbsp;
  <img alt="Hilt" src="https://img.shields.io/badge/DI-Hilt-0B57D0?style=flat" />
</p>

---

## O que é o Orbi?

Orbi é um app Android de produtividade feito para quem quer ritmo, não pressão. Você monta **ciclos** com título, dias da semana, tempo de foco, pausa e quantas sessões repetir. Tudo sincroniza na nuvem com a sua conta Google.

Inspirado na ideia do Pomodoro, mas flexível: cada ciclo se encaixa na sua rotina, não o contrário.

## Funcionalidades

- **Onboarding** em três telas explicando o propósito do app
- **Login com Google** via Firebase Authentication
- **Criação de ciclos** em assistente de 5 passos:
  1. Título e descrição
  2. Dias da semana ativos
  3. Duração do foco
  4. Duração da pausa
  5. Número de sessões (até 20)
- **Home** com lista de ciclos salvos em tempo real
- **Detecção de rede** com dialog quando o dispositivo está offline
- **Splash** que roteia entre onboarding, login e home conforme o estado do usuário

## Stack

| Camada | Tecnologia |
| --- | --- |
| Linguagem | Kotlin 2.0 |
| UI | XML + ViewBinding + Material Design |
| Arquitetura | MVVM + Clean (domain / data / ui) |
| DI | Hilt |
| Auth | Firebase Auth + Google Sign-In |
| Banco | Cloud Firestore |
| Async | Kotlin Coroutines + Flow |
| Build | Android Gradle Plugin 8.3, KSP |

**Requisitos:** Android 7.0 (API 24)+, JDK 11, Android Studio Hedgehog ou mais recente.

## Arquitetura

```
ui/          Activities, Fragments, ViewModels, adapters
domain/      Models e contratos de repositório
data/        Implementações (Firebase Auth, Firestore)
di/          Módulos Hilt (Auth, Database)
util/        Helpers (ex.: NetworkUtils)
```

Fluxo típico: a UI observa um `StateFlow` na ViewModel; a ViewModel chama o repositório; a implementação fala com Firebase. As interfaces ficam em `domain`, então a UI não conhece Firestore nem Auth diretamente.

## Estrutura do projeto

```
Orbi/
├── app/
│   ├── src/main/java/com/example/orbi/
│   │   ├── ui/
│   │   │   ├── splash/
│   │   │   ├── onboarding/
│   │   │   ├── login/
│   │   │   ├── main/
│   │   │   └── createcycle/
│   │   ├── domain/
│   │   ├── data/
│   │   ├── di/
│   │   └── util/
│   └── src/main/res/
├── gradle/
└── README.md
```

## Como rodar

1. Clone o repositório:

```bash
git clone https://github.com/officialdelffin/Orbi.git
cd Orbi
```

2. Abra o projeto no Android Studio e deixe o Gradle sincronizar.

3. Confirme o Firebase:
   - O arquivo `app/google-services.json` precisa apontar para o projeto Firebase correto.
   - No Firebase Console, ative **Authentication > Google** e **Cloud Firestore**.
   - O `default_web_client_id` em `res/values/strings.xml` deve bater com o Web client ID do Google Cloud / Firebase.

4. Rode no emulador ou no aparelho:

```bash
./gradlew :app:installDebug
```

Ou use o botão Run do Android Studio no módulo `:app`.

## Modelo de um ciclo

```kotlin
data class Cycle(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String? = null,
    val activeDays: List<Int> = emptyList(),
    val focusDurationMinutes: Int = 25,
    val breakDurationMinutes: Int = 5,
    val repetitions: Int = 4,
    val createdAt: Long = System.currentTimeMillis()
)
```

Documentos ficam na coleção `cycles` do Firestore, filtrados pelo `userId` do usuário autenticado.

## Paleta

| Nome | Hex |
| --- | --- |
| Cream | `#F9F4E3` |
| Dark brown | `#7A6448` |
| Olive | `#B1BA8E` |
| Light olive | `#D4DDB1` |
| Sand | `#FAE3D2` |

## Autor

Feito por **Lucas (oDelffin)** — [github.com/officialdelffin](https://github.com/officialdelffin)

---

<p align="center">
  <em>Grandes conquistas exigem ritmo e clareza.</em>
</p>
