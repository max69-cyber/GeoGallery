# GeoGallery: план обучения и правила работы

> Этот файл — контекст для AI-агента в проекте GeoGallery. Лежит в корне и читается автоматически.
> **Что строим** (продукт, данные, контракты) — в [`SPEC.md`](SPEC.md).
> **Как учимся и в каком порядке делаем** — здесь. Требования сюда не дублируются: только ссылки на разделы SPEC.

**Статус:** Этап 0. GeoGallery стартует после завершения ReelTusky (его этапы 5–9: навигация с аргументом,
Room, тесты, Hilt, полировка).

---

## 🤖 Правила для агента (прочитай первым)

**Ты — ментор, а не исполнитель.** Я учусь Android-разработке и пишу код сам.
Твоя задача — объяснять, подсказывать и проверять, а не писать решение за меня.

### Что делать

- **Не пиши и не редактируй код в проекте**, если я явно не попросил об этом конкретное изменение
  («впиши эту строку», «покажи готовый код»). Фразы «как сделать X», «помоги с X», «не получается» —
  это просьба о подсказке, а не о готовом коде.
- **Подсказывай по уровням**, переходя к следующему только если я застрял или попросил «больше подсказки»:
  1. Направление: какая концепция или API нужны, где в документации читать.
  2. Конкретика: точные имена функций и параметров, куда в моём коде их поставить, без реализации.
  3. Скелет с пропусками (`/* ... */`) или псевдокод.
  4. Готовый код — только по прямой просьбе.
- **Предупреждай о подводных камнях заранее** («ты почти наверняка наткнёшься на…»), но дай мне наткнуться
  и разобраться самому, если это полезно для понимания.
- **Когда я прошу проверить код** — прочитай файлы сам, укажи ошибки и неидиоматичный Kotlin,
  объясни *почему*, но исправлять предоставь мне.
- **Объясняй механику, а не только «как»**: почему так устроено, какую проблему решает.
- **Давай ссылки на developer.android.com** (и kotlinlang.org), чтобы я читал первоисточник.
- **Архитектурные решения**: показывай варианты с плюсами и минусами, давай рекомендацию,
  но решение за мной.
- **Аналогии с Vue/TS/вебом** работают отлично — используй их.
- **Версии библиотек, API и цены сервисов быстро меняются**: перед советом по конкретной версии или API
  сверяйся с актуальной документацией, а не с памятью.
- **Требования живут в `SPEC.md`.** Если нужно изменить ТЗ — предложи правку и спроси, молча не переписывай.
- **Веди архив обучения**: когда я закончил этап или освоил новую тему, предложи обновить разделы
  «Что уже изучено» и «Грабли». Вноси правки только по моей просьбе.
- Отвечай по-русски.

### Исключения (тут можно делать самому, но сначала спроси)

- Настройка Gradle (version catalog, плагины, KSP, модули) — можно предложить сделать за меня с объяснением
  каждой строки. Я соглашусь или откажусь.
- Git-операции — только по моей просьбе.

### Сообщения коммитов

- Когда я собираюсь коммитить или спрашиваю, как назвать коммит, напомни формат и предложи готовое сообщение
  по фактическому изменению.
- Заголовок на английском в формате Conventional Commits — `type(scope): short description`.
  `scope` необязателен. Описание начинай с глагола, например `add`, `fix` или `update`.
- Выбирай тип по изменению. `feat` — новая возможность, `fix` — исправление бага, `build` — Gradle,
  модули и зависимости, `docs` — документация, `test` — тесты, `refactor` — перестройка кода с сохранением поведения.
- По умолчанию не добавляй ссылки на задачи Linear в сообщения коммитов.

```text
build: add domain, data, and UI modules
```

---

## 👤 Обо мне

- Фронтенд: ~1 год коммерческой разработки на Vue.js + TypeScript. ООП и базовый синтаксис объяснять не нужно.
- Kotlin Tour (beginner + intermediate) пройден.

### Что уже изучено (в учебном проекте ReelTusky)

- Compose basics: `@Composable`, рекомпозиция, `Modifier` (порядок важен), `Column`/`Row`/`Box`, `Arrangement`/`Alignment`.
- State: `remember`, `mutableStateOf`, `mutableStateListOf` (обычный `mutableListOf` не реактивен), state hoisting.
- `LazyColumn`, `items` (extension, нужен импорт), `key`, `LazyListState`, `animateScrollToItem`.
- Side effects: `LaunchedEffect` (ключ должен реально меняться и читаться в композиции), знакомство со `snapshotFlow`.
- `Scaffold`, `TopAppBar`, `NavigationBar`, insets: `innerPadding`, `consumeWindowInsets`, `contentWindowInsets`, edge-to-edge.
- Navigation Compose 2 с type-safe `@Serializable`-маршрутами, `toRoute`, `hasRoute`, нижние вкладки.
- Ресурсы: `R`, drawable, иконки Material Symbols (удалять `android:tint="?attr/colorControlNormal"` — без AppCompat не собирается).
- Слоты: передаётся лямбда `{ Icon(...) }`, а не вызов `Icon(...)`.
- `@Preview`, `@PreviewLightDark`, multipreview-аннотации.
- Gradle: version catalog (`libs.versions.toml`), плагины, Sync.
- Repository: `interface`, `Flow`/`StateFlow`, `MutableStateFlow.update {}`, `suspend`; узкие методы под сценарии.
- `ViewModel`, `viewModelScope`, `stateIn`, `collectAsStateWithLifecycle`, `UiState` + UDF,
  `ViewModelProvider.Factory`, класс `Application`, ручной DI (`AppContainer`); stateful/stateless экраны.
- Формы и жесты: `TextField` и состояние ввода, валидация, `ModalBottomSheet`, `SwipeToDismissBox`,
  Snackbar с «Отменить».

### Ещё в процессе (ReelTusky, этапы 5–9)

Навигация с аргументом (`SavedStateHandle`, `toRoute`), Room, тесты, Hilt, полировка.
**После завершения ReelTusky перенести итоги в список выше**, чтобы агент не объяснял это заново.

### Изучено в GeoGallery

*(пока пусто — дополнять по мере прохождения этапов)*

---

## 🏗 Принятые решения

| Область | Решение | Почему |
|---|---|---|
| Архитектура | **Clean + MVVM + UDF**: `Presentation → Domain ← Data`, один `UiState` на экран | Цель проекта — отработать слои и инверсию зависимостей |
| Модули | `:domain` (Kotlin/JVM), `:data` и `:ui` (Android libraries), `:app` (точка входа и сборка DI). Граф зависимостей — SPEC §11 | `:domain` собирается без Android SDK, а `:ui` не может импортировать классы `:data`. Реализации связываются с контрактами в `:app` |
| Интерфейсы репозиториев | В **`domain`**, реализации в `data` | Классический Clean (в ReelTusky интерфейс лежал в `data` — осознанное отличие) |
| Юзкейсы | Только там, где есть логика; простые чтения идут из репозитория напрямую | Не плодить классы-прокси |
| Модели | Dto / Entity / Domain / Ui — разделяем там, где реально различаются | Не плодить копии «на всякий случай» |
| Источник правды | Room; UI наблюдает только БД, сеть синхронизирует | Offline-first |
| Фоновая работа | WorkManager для синхронизации архива | Переживает смерть процесса, ретраи и ограничения из коробки |
| Асинхронность | Coroutines + Flow, `viewModelScope` | Стандарт |
| id | `String` UUID, генерируется на клиенте | Офлайн-создание и идемпотентная синхронизация |
| Время | `Instant` в domain, epoch millis в Room, ISO-8601 в API, форматирование только в UI | Одна точка преобразований |
| Источник фото | Только камера (CameraX) за интерфейсом `PhotoSource` | Импорт из галереи — v2 |
| Геолокация | Системный `LocationManager` за интерфейсом `LocationProvider` (SPEC §11) | Работает без Google Play Services |
| Подписи | `CaptionSource`: `AI` или `USER`; ИИ даёт только `CaptionSuggestion`, подпись сам не меняет | Ручные правки не затираются |
| ИИ | Облачный VLM через собственный бэкенд, провайдер за интерфейсом `CaptionGenerator` | Ключ не в клиенте, провайдера можно сменить |
| Аккаунты | Необязательные (email + пароль): без аккаунта всё работает локально, аккаунт даёт синхронизацию и ИИ | Offline-first и синхронизация — часть цели, но не барьер на входе |
| Владелец данных | Одно устройство — один владелец (гость или аккаунт); при входе — перенос гостевых данных, при выходе — очистка | Данные разных аккаунтов не смешиваются (SPEC §5.1) |
| Навигация | Navigation Compose 2, type-safe маршруты | Как в ReelTusky |
| DI | **Hilt** (предварительно; осваивается в ReelTusky, Этап 8) | Подтвердить на Этапе 1 |
| UI-кит | Material 3 (`androidx.compose.material3`, не `material`!) | — |
| Платформа | minSdk 26; compile/target SDK — как в шаблоне Studio | — |
| Публикация | Не планируется (репозиторий, README, демо) | Время лучше потратить на качество |

### Открытые решения: учебные и продуктовые, с привязкой к этапам

Продуктовые вопросы полностью — в SPEC §14; здесь те, что влияют на порядок работы, плюс учебные решения по архитектуре.

| Вопрос | Решить на этапе |
|---|---|
| Бэкенд: NestJS или BaaS | перед Этапом 7 |
| SDK карты: Yandex MapKit / MapLibre / Google Maps | перед Этапом 6 |
| HTTP-клиент: Retrofit или Ktor | Этап 7 |
| Провайдер ИИ (доступность и оплата из РФ) | перед Этапом 9 |
| Проверить стартовый радиус места и порог погрешности (SPEC §5.2–5.3) | Этап 4 |
| Модель ошибок между слоями: `Result` / sealed `DomainError` / исключения | Этап 1 |
| Одноразовые события во ViewModel (навигация, snackbar): поле в `UiState` или `Channel` | Этап 2.5 |
| Где живёт маппинг Domain → Ui: во ViewModel или отдельный маппер | Этап 2.5 |

### Сквозные правила (не отдельный этап, а часть каждого)

- **Тесты пишутся вместе с кодом**, а не «потом»: юзкейс → unit-тест на JVM, ViewModel → тест с fake-репозиторием,
  DAO → instrumented-тест. Без теста пункт этапа не считается закрытым.
- **Сначала потребитель, потом интерфейс** (outside-in): интерфейс репозитория/сервиса появляется, когда его требует
  конкретный юзкейс или экран, а не заранее «по SPEC».
- **Сначала fake, потом реальная реализация**, если реальная блокируется внешним (бэкенд, провайдер ИИ, SDK карты):
  UI и юзкейсы доводятся на fake, реальная реализация подменяется одной строкой в DI-модуле.
- **Архитектурное ревью** на контрольных точках (после Этапов 2.5, 4 и 8): агент проверяет направления зависимостей,
  границы слоёв, где переключаются потоки и не утекли ли Entity/Dto за пределы `:data`.

### Целевая структура

```
GeoGallery/
├── SPEC.md
├── AGENTS.md
├── app/                                  ← :app — точка входа и сборка DI
│   └── src/main/java/com/rayxaus/geogallery/
│       ├── MainActivity.kt               ← вызывает GeoGalleryApp() из setContent
│       ├── GeoGalleryApplication.kt      ← @HiltAndroidApp
│       └── di/
├── ui/                                   ← :ui — presentation и навигация
│   └── src/main/java/com/rayxaus/geogallery/ui/
│       ├── GeoGalleryApp.kt              ← корневой composable с темой и NavHost
│       ├── navigation/Routes.kt
│       ├── PreviewData.kt                ← моки только для превью
│       ├── theme/
│       ├── components/                   ← переиспользуемое
│       ├── places/                       ← список мест (Этап 2.5), альтернативный вид к карте
│       ├── map/                          ← MapScreen, MapContent, MapViewModel, MapUiState
│       ├── camera/
│       ├── place/
│       ├── photos/                       ← все фото, включая «Без места»
│       ├── photo/
│       ├── auth/
│       └── settings/
├── domain/                               ← :domain — Kotlin/JVM, БЕЗ Android
│   └── src/main/kotlin/com/rayxaus/geogallery/domain/
│       ├── model/                        ← Photo, Place, Caption, CaptionSuggestion, GeoPoint, User, …
│       ├── repository/                   ← PhotoRepository, PlaceRepository, AuthRepository, SettingsRepository
│       ├── service/                      ← CaptionGenerator, LocationProvider, PhotoSource, SyncScheduler, Clock, IdGenerator
│       └── usecase/                      ← CapturePhoto, ResolvePlaceForLocation, …
└── data/                                 ← :data — Android library
    └── src/main/java/com/rayxaus/geogallery/data/
        ├── local/                        ← Room: entities, DAO, database
        ├── remote/                       ← API, Dto, interceptors
        ├── repository/                   ← *RepositoryImpl
        ├── location/                     ← LocationProviderImpl
        ├── ai/                           ← RemoteCaptionGenerator
        ├── sync/                         ← воркеры WorkManager, SyncSchedulerImpl
        └── mapper/
```

Каркас начинается с `:app` из шаблона Studio. Модули `:domain`, `:data` и `:ui` создаются на этапе 1.

### Конвенция превью

| Уровень | Что | Аннотация |
|---|---|---|
| Компонент (`ui/components/`) | каждое значимое состояние — отдельной функцией | `@PreviewLightDark` |
| | одно «стресс»-состояние (самый длинный текст) | `@PreviewFontScale` |
| Экран — только stateless `…Content` | каждое состояние экрана (пусто / мало / много / загрузка / ошибка) | `@PreviewLightDark` |
| | одно основное состояние (контент) | `@PreviewScreenSizes` |

- Имена: `<Компонент><Состояние>Preview` (`PhotoThumbnailPendingPreview`, `MapContentEmptyPreview`), все превью `private`.
- Состояния списков: `Empty` / `SingleItem` / `ManyItems`; основное состояние — без суффикса (`MapContentPreview`).
- Моки-списки: `singlePhotoMock`, `manyPhotosMock`, `placesMock`.
- Всегда обёртка `GeoGalleryTheme { }`; компонент без своего фона — дополнительно в `Surface`.
- Данные — только именованные моки из `ui/PreviewData.kt`; колбэки — пустые лямбды с именованными аргументами.
- Stateful `…Screen` (с ViewModel) превью не делаем.
- Карта и камера в превью не отображаются (нативные View) — превьюим окружение (панели, подписи, пустые состояния).
- Своих multipreview-аннотаций пока нет. `@PreviewDynamicColors` не используем; RTL/локали — на Этапе 11.

---

## ✅ Чек-лист

Каждый этап: **цель → что изучить → что сделать → критерий готовности**.
Действуют «Сквозные правила» из раздела «Принятые решения»: тесты вместе с кодом, outside-in, fake → реальная реализация.
Коммит после каждого этапа (маленькие логичные коммиты — это портфолио).
Требования к фичам — в `SPEC.md`, здесь только учебная часть.

### Этап 0. Проект и репозиторий
**Цель:** рабочий каркас и чистая история с первого коммита.
- [x] Новый проект: *Empty Activity* (Compose), пакет `com.rayxaus.geogallery`, minSdk 26.
- [x] `git init`, `origin` на GitHub настроен.
- [x] В `.gitignore` добавлены `.kotlin` и `/.idea`.
- [ ] Первый коммит: шаблон проекта (`Initial commit`).
- [ ] Второй коммит: `SPEC.md`, `AGENTS.md`, `README.md`.
- [ ] Обновить устаревшие версии в `libs.versions.toml` (Studio подсветит).
- **Готово, когда:** приложение запускается, репозиторий на GitHub, в истории два аккуратных коммита.

### Этап 1. Модули и Domain
**Цель:** собрать четыре модуля и закрепить границы между доменом, UI и хранением на уровне Gradle.
- **Изучить:** Gradle multi-module, JVM-модуль (`kotlin("jvm")`), зависимости между модулями
  (`implementation(project(":domain"))`), зачем `domain` отдельным модулем; `@JvmInline value class` для id;
  `java.time.Instant` (доступен на minSdk 26); чистые функции и их тестирование;
  моделирование ошибок в domain (`Result`, sealed-иерархия ошибок, когда исключение уместнее);
  какие зависимости допустимы в `:domain` (`kotlinx-coroutines-core` ради `Flow`, `javax.inject` ради `@Inject constructor`).
- [ ] Модули `:domain`, `:data`, `:ui`, `:app` по графу из SPEC §11 (*можно попросить агента настроить Gradle*).
- [ ] Модели: `Photo`, `Place`, `GeoPoint`, enum'ы (SPEC §6) — только то, что нужно подбору места и первому экрану;
  `Caption`, `CaptionSuggestion` — когда до них дойдёт дело.
- [ ] Чистая логика подбора места по расстоянию (формула расстояния между координатами) + unit-тесты.
- [ ] `Clock` и `IdGenerator` (SPEC §11) + их fake-версии для тестов — чтобы `createdAt` и UUID в тестах были предсказуемыми.
- [ ] Решить модель ошибок между слоями (см. «Открытые решения»).
- [ ] Подтвердить выбор DI (Hilt).
- Интерфейсы репозиториев и сервисов здесь **не** пишем все сразу — они появляются по мере надобности (outside-in).
- **Готово, когда:** собираются четыре модуля, у `:domain` нет Android-зависимостей, `:data` недоступен из `:ui`,
  а тесты подбора места проходят на JVM.
- 📖 https://developer.android.com/topic/modularization
- 📖 https://developer.android.com/topic/architecture/domain-layer

### Этап 2. Локальные данные: Room и репозитории
**Цель:** БД как источник правды, UI наблюдает только её.
- **Изучить:** связи в Room (`Place` 1:N `Photo`, `@Relation`, внешние ключи, индексы), маппинг Entity ↔ Domain,
  `Flow` из DAO, миграции и `exportSchema`, хранение файлов в app-specific хранилище (`filesDir`);
  main-safety: кто переключает поток (`withContext(Dispatchers.IO)` в data-слое) и зачем инжектить `CoroutineDispatcher`;
  Hilt в многомодульном проекте: где лежат `@Module` с `@Binds` (в `:data` или в `:app`), `@InstallIn`, `@Singleton`.
- [ ] Интерфейсы `PhotoRepository`, `PlaceRepository` в `:domain` — ровно с теми методами, что нужны сейчас.
- [ ] `PhotoEntity`, `PlaceEntity`, DAO, `GeoGalleryDatabase`, мапперы Entity ↔ Domain.
- [ ] `PhotoRepositoryImpl`, `PlaceRepositoryImpl` в `:data`, подключение через Hilt.
- [ ] Fake-репозитории для тестов.
- **Готово, когда:** тестовые места и фото сохраняются и наблюдаются через Flow; DAO покрыт instrumented-тестом.

### Этап 2.5. Первый экран: вертикальный срез
**Цель:** собрать сквозной путь `Screen → ViewModel → (UseCase) → Repository → Room` целиком и получить
шаблон MVVM-экрана, который потом повторяется везде.
- **Изучить:** `@HiltViewModel` + `hiltViewModel()`, `UiState` (sealed: загрузка / пусто / контент / ошибка),
  маппинг Domain → Ui-модель и форматирование во ViewModel/маппере, одноразовые события (навигация, snackbar):
  почему не `StateFlow<Event>` и какие есть варианты; тестирование ViewModel (`runTest`, `Dispatchers.setMain`,
  fake-репозиторий, опционально Turbine).
- [ ] Экран «Список мест» (SPEC §4, Places; после Этапа 6 — альтернативный вид к карте): stateful `…Screen` + stateless `…Content`.
  Название по умолчанию собирается в UI из `createdAt` (SPEC §5.3) — хороший первый пример маппинга Domain → Ui.
- [ ] Отладочная кнопка/сидер с тестовыми местами, чтобы было что показать без камеры и GPS.
- [ ] Решить одноразовые события и место маппинга Domain → Ui (см. «Открытые решения»).
- [ ] Тест ViewModel с fake-репозиторием; превью всех состояний `…Content`.
- [ ] 🔍 Архитектурное ревью с агентом.
- **Готово, когда:** данные из Room видны на экране, переживают поворот, ViewModel покрыт тестом,
  `:ui` получает доменные модели через контракты из `:domain`.
- 📖 https://developer.android.com/topic/architecture/ui-layer
- 📖 https://developer.android.com/topic/architecture/ui-layer/events
- 📖 https://developer.android.com/training/dependency-injection/hilt-multi-module
- 📖 https://developer.android.com/training/data-storage/room
- 📖 https://developer.android.com/training/data-storage/room/relationships
- 📖 https://developer.android.com/training/data-storage/app-specific

### Этап 3. Разрешения и камера (CameraX)
**Цель:** получить снимок в файл и корректно пережить все сценарии с разрешениями.
- **Изучить:** runtime-разрешения (`CAMERA`), `rationale`, отказ и «больше не спрашивать» → переход в настройки;
  CameraX (`Preview`, `ImageCapture`, `ProcessCameraProvider`, привязка к lifecycle), интеграция с Compose,
  ориентация и EXIF-поворот сохранённого файла.
- [ ] Онбординг ведёт на карту. Запрашивать доступ к камере при её открытии, к геолокации — по «Моё местоположение» или при первом снимке. Для отказа в доступе к камере предусмотреть экран с переходом в настройки.
- [ ] `PhotoSource` в `:domain` (появляется сейчас, потому что его требует съёмка), реализация на CameraX.
- [ ] Экран камеры: превью и снимок 4:3 (3:4 в портрете) на всех устройствах, кнопка съёмки, файл в app-specific хранилище.
- [ ] Ошибки камеры и разрешений отражены в `UiState` по модели ошибок из Этапа 1.
- **Готово, когда:** снимок сохраняется и открывается, поворот экрана не ломает камеру, отказ в разрешении не роняет приложение;
  ViewModel камеры покрыт тестом с fake `PhotoSource`.
- 📖 https://developer.android.com/media/camera/camerax
- 📖 https://developer.android.com/training/permissions/requesting

### Этап 4. Геолокация и места
**Цель:** привязать снимок к месту, когда геолокация подходит для автоподбора (SPEC §5.2–5.3).
- **Изучить:** способы получить локацию (Fused vs `LocationManager`, зависимость от Google Play Services),
  разрешения на геолокацию, точность и таймаут, GPS выключен, задание координат в эмуляторе.
- [x] Выбрать способ получения локации. Решение — `LocationManager` (SPEC §11).
- [ ] `LocationProvider` (интерфейс в `domain`, реализация в `data`).
- [ ] Юзкейсы `CapturePhoto` и `ResolvePlaceForLocation` (SPEC §5.2, §5.3, §7); сценарий «без места».
- [ ] Действие «Уточнить геолокацию» после сохранения снимка с координатами, не прошедшими проверку точности (SPEC §5.2).
- [ ] Переименование места, в том числе сразу из Snackbar «Создано <место>» на экране камеры.
- [ ] 🔍 Архитектурное ревью с агентом (юзкейсы, ошибки, потоки).
- **Готово, когда:** снимок привязывается по правилам SPEC §5.2–5.3, а при неизвестной или недостаточной точности можно повторить замер.
  Привязка и сценарии «Без места» покрыты unit-тестами с fake `LocationProvider`.
- 📖 https://developer.android.com/develop/sensors-and-location/location

### Этап 5. Место и фото: просмотр и ручные подписи
**Цель:** полный сценарий просмотра и редактирования без сети и без ИИ.
- **Изучить:** `LazyVerticalGrid`, `HorizontalPager`, состояние редактируемого текста
  (`rememberSaveable`, `SavedStateHandle`), удаление с отменой (tombstone-подход);
  загрузка изображений (Coil) и downsampling миниатюр.
- [ ] Модели `Caption`, `CaptionSource` в `:domain`.
- [ ] Экран места (переход из списка мест): сетка миниатюр, переименование.
- [ ] Экран фото: просмотр, свайп между фото, редактирование подписи (`CaptionSource.USER`), удаление.
- [ ] Экран «Все фото» (SPEC §4, Photos): сетка по дате, фото без места с пометкой, фильтр «Только без места».
- [ ] `DeletePhoto` + правило пустого места (SPEC §5.3, §7).
- **Готово, когда:** весь локальный сценарий работает без сети, подпись переживает поворот и перезапуск;
  ViewModel экранов места и фото покрыты тестами.
- 📖 https://developer.android.com/develop/ui/compose/lists
- 📖 https://developer.android.com/develop/ui/compose/layouts/pager
- 📖 https://coil-kt.github.io/coil/

### Этап 6. Карта и метки
**Цель:** места видны на карте с превью и кластерами.
- **Изучить:** выбранный SDK карты (ключи, жизненный цикл `MapView` в Compose, кастомные маркеры, кластеризация,
  события нажатия, камера карты).
- [ ] Выбрать SDK карты.
- [ ] Экран карты: метки мест с превью и счётчиком, кластеры, переход в место (экран из Этапа 5).
- [ ] Список мест из Этапа 2.5 остаётся альтернативным видом.
- **Готово, когда:** на карте видны все места, кластеризация работает при отдалении, 1000 фиктивных точек не тормозят;
  `MapViewModel` переиспользует тот же источник данных, что и список (карта — только другой UI).

### Этап 7. Сеть и аккаунты
**Цель:** необязательный аккаунт поверх работающего гостевого режима и надёжный сетевой слой.
- **Изучить:** Retrofit или Ktor + `kotlinx.serialization`, перехватчики (заголовок авторизации, refresh токена),
  безопасное хранение токенов (DataStore + шифрование через Keystore), обработка ошибок сети через `Result`/sealed-типы,
  состояние сессии как `Flow` (гость / вошёл / токен протух), транзакционная очистка БД и файлов.
- [ ] `AuthRepository` в `:domain` + `FakeAuthRepository`; экраны входа и регистрации из Settings — сначала на fake.
- [ ] `SignIn`/`SignUp` с диалогом переноса гостевых данных; `SignOut` с предупреждением и очисткой (SPEC §5.1).
- [ ] Выбрать бэкенд и HTTP-клиент; поднять минимальный бэкенд (отдельным проектом).
- [ ] Реальный `AuthRepositoryImpl`, подмена fake в DI-модуле; маппинг сетевых ошибок в модель ошибок domain.
- [ ] Удаление аккаунта.
- **Готово, когда:** без аккаунта всё работает как раньше; вход с гостевыми данными переносит их, выход очищает архив;
  токен обновляется сам, сессия переживает перезапуск; переход с fake на реальную реализацию не потребовал правок
  в `:ui`; юзкейсы входа/выхода и ViewModel входа покрыты тестами.
- 📖 https://kotlinlang.org/docs/serialization.html
- 📖 https://developer.android.com/topic/libraries/architecture/datastore

### Этап 8. Синхронизация (offline-first)
**Цель:** данные уходят на сервер без участия пользователя и без дублей.
- **Изучить:** WorkManager (`CoroutineWorker`, ограничения сети, backoff, `enqueueUniqueWork`), идемпотентность,
  статусы синхронизации, загрузка файлов multipart, tombstone-удаление, курсорный pull (`/sync?since=`).
- [ ] `SyncStatus` и tombstone у фото и мест, `SyncScheduler`, воркеры; синхронизация только при наличии аккаунта.
- [ ] Индикаторы статуса и сводка в настройках (в гостевом режиме скрыты).
- [ ] 🔍 Архитектурное ревью с агентом (data-слой целиком: локальный и удалённый источники, синхронизация).
- **Готово, когда:** снимок в авиарежиме позже уходит на сервер сам; обрыв сети посреди загрузки не создаёт дублей;
  логика применения изменений с сервера и статусов покрыта unit-тестами.
- 📖 https://developer.android.com/develop/background-work/background-tasks/persistent
- 📖 https://developer.android.com/topic/architecture/data-layer/offline-first

### Этап 9. ИИ-подписи
**Цель:** предложение подписи по явному запросу пользователя с проверкой согласия (SPEC §9).
- **Изучить:** подготовка изображения (downscale, JPEG-сжатие, удаление EXIF), хэш для кэша,
  хранение и отзыв согласия, отмена корутин, лимиты и состояния запроса (загрузка / получено / ошибка).
- [ ] `CaptionSuggestion` и `CaptionGenerator` (интерфейс) в `:domain` + `FakeCaptionGenerator` (задержка, иногда ошибка).
- [ ] `CaptionError` в `:domain` (SPEC §6). Состояние активного запроса в `UiState` (SPEC §9).
- [ ] Юзкейсы `RequestCaptionSuggestion` и `ResolveCaptionSuggestion` (SPEC §6, §7, §9) + unit-тесты правил подписей.
- [ ] Sheet при первой попытке (нет аккаунта → «Войти»; нет согласия → согласие) и UI предложения:
  принять / править / отклонить / повторить — на fake.
- [ ] Выбрать провайдера ИИ; эндпоинт `POST /captions` на бэкенде; `RemoteCaptionGenerator`, подмена fake в DI.
- **Готово, когда:** предложение приходит после ручного запроса на экране фото. Тесты покрывают проверку согласия,
  сохранение ручной подписи и ошибку сети с ручным повтором (SPEC §9).

### Этап 10. Добивка тестов
**Цель:** закрыть пробелы. Основные тесты к этому моменту уже написаны на своих этапах (см. «Сквозные правила»).
- **Изучить:** тесты воркеров WorkManager (`TestListenableWorkerBuilder`), Compose UI-тесты
  (`createComposeRule`, семантика), отчёт о покрытии.
- [ ] Пройтись по юзкейсам и ViewModel: у всех ли есть тесты, покрыты ли ошибки, а не только «счастливый путь».
- [ ] (Опционально) тест воркера; один Compose UI-тест ключевого сценария.
- **Готово, когда:** `./gradlew test` зелёный, правила подписей и подбор места покрыты тестами, у каждого ViewModel есть тест.
- 📖 https://developer.android.com/kotlin/coroutines/test
- 📖 https://developer.android.com/develop/ui/compose/testing

### Этап 11. Полировка
**Цель:** проект, который приятно открыть и показать.
- [ ] Все строки в `strings.xml`, русская и английская локали.
- [ ] Состояния и ошибки из SPEC §13 реализованы и проверены.
- [ ] Иконка приложения; тёмная тема.
- [ ] Release-сборка с включённой оптимизацией (см. `build.gradle.kts`) проверена руками.
- [ ] README: описание, скриншоты/GIF, схема архитектуры, стек, ссылка на SPEC; демо-видео.
- **Готово, когда:** README и демо позволяют понять проект за пять минут, release-сборка работает.

### Дальше (после MVP)
- [ ] Импорт из галереи (MediaStore, EXIF-координаты, ручная привязка).
- [ ] ИИ-группировка фото, поиск по смыслу.
- [ ] On-device теги (ML Kit) как офлайн-режим.
- [ ] Вынос `domain` в Kotlin Multiplatform.

---

## ⚠️ Грабли, на которые уже наступал (напоминать, если повторяю)

- `mutableListOf` в `remember` не вызывает рекомпозицию → `mutableStateListOf` или неизменяемый список в `mutableStateOf`.
- `remember` теряется при повороте экрана и при уходе с экрана в навигации → ViewModel.
- `LaunchedEffect(list)` с одним и тем же объектом списка не перезапускается → ключ должен меняться (`list.size`).
- Extension-функции (`items`, `composable`, `toRoute`, `hasRoute`) не видны без импорта.
- Слоты принимают лямбду: `icon = { Icon(...) }`, а не `icon = Icon(...)`.
- Вложенные `Scaffold` → двойные insets → `consumeWindowInsets`.
- Корень каждого экрана — `Scaffold` (иначе нет фона темы и insets).
- Старый строковый стиль навигации (`.route`, `"details/{id}"`) — не использовать.
- Имена своих классов не должны совпадать с компонентами Material (`NavigationBarItem` и т.п.).
- Имена пакетов — строчными (`tasklist`, не `taskList`).
- `ModalBottomSheet` без анимации закрытия → `sheetState` создать снаружи и передать в `sheetState = …`,
  закрывать через `hide()` + `invokeOnCompletion { onDismiss() }`.
- `TextFieldState()` / `FocusRequester()` без `remember` → новый объект на каждой рекомпозиции.
- `SnackbarHostState.showSnackbar` с `actionLabel` по умолчанию `Indefinite` → задавать `duration` явно.
- `LaunchedEffect` внутри ветки `when`/`if` отменяется при смене ветки → ставить на верхний уровень.
- Состояние элемента `LazyColumn` в `rememberSaveable` возвращается вместе с тем же `key`
  (восстановленная задача пришла «смахнутой» и удалилась снова) → для свайпа `remember`.
- Composable-геттеры (`SwipeToDismissBoxDefaults.positionalThreshold`) нельзя читать внутри `remember { }` → прочитать до.

*(Новые грабли GeoGallery дописывать ниже.)*

### 🚧 Ожидаемые подводные камни (ещё не встречались — агент предупреждает заранее)

- Разрешения: состояние нужно перепроверять при возврате на экран (пользователь мог изменить его в настройках);
  после повторных отказов системный диалог больше не появляется.
- CameraX: привязка к lifecycle, поворот и ориентация сохранённого файла (EXIF); камера эмулятора — виртуальная сцена.
- Локация: «последняя известная» может быть `null` или устаревшей; в эмуляторе координаты задаются вручную;
  у устройств без Google Play Services Fused-провайдера нет.
- `Bundle` и `rememberSaveable` не для фото и больших объектов (лимит размера транзакции).
- Оригиналы фото в сетке и на метках → нехватка памяти; нужны миниатюры и downsampling.
- `MapView` в Compose: жизненный цикл (`onResume`/`onPause`/`onDestroy`), ключи SDK не коммитить.
- WorkManager: без уникальных имён работ и идемпотентности получаются дубли; отлаживать удобно через Background Task Inspector.
- Время: `Instant` в domain, часовой пояс и формат — только в UI.
- `:domain` собирается без Android SDK. Код, которому нужны Android-типы, размещать в соответствующем Android-модуле.
- Одноразовые события (навигация, snackbar) в `StateFlow` → повторно срабатывают после поворота экрана
  или теряются; решение — осознанный выбор из «Открытых решений», а не случайность.
- Room/Retrofit-вызовы на главном потоке или `withContext(Dispatchers.IO)` во ViewModel → поток переключает
  data-слой (main-safety); захардкоженный `Dispatchers.IO` мешает тестам → инжектить диспетчер.
- Entity и DTO преобразуются в доменные модели на границе `:data`. В `:ui` доступны контракты и модели `:domain`.
- Исключения из data-слоя долетают до UI сырыми (`IOException`, `SQLiteException`) → переводить в модель ошибок domain.
- ViewModel-тесты без `Dispatchers.setMain(...)` падают с «Module with the Main dispatcher is missing».
- Hilt в многомодульном проекте: `@Module` из `:data` не виден, если `:app` не зависит от `:data`;
  забытый `@InstallIn` → ошибка компиляции, понятная не сразу.

---

## 📚 Полезные ссылки

- Архитектура: https://developer.android.com/topic/architecture
- Рекомендации: https://developer.android.com/topic/architecture/recommendations
- Domain-слой: https://developer.android.com/topic/architecture/domain-layer
- Offline-first: https://developer.android.com/topic/architecture/data-layer/offline-first
- Модуляризация: https://developer.android.com/topic/modularization
- State в Compose: https://developer.android.com/develop/ui/compose/state
- Side effects: https://developer.android.com/develop/ui/compose/side-effects
- Lists: https://developer.android.com/develop/ui/compose/lists
- Navigation: https://developer.android.com/develop/ui/compose/navigation
- CameraX: https://developer.android.com/media/camera/camerax
- Геолокация: https://developer.android.com/develop/sensors-and-location/location
- WorkManager: https://developer.android.com/develop/background-work/background-tasks/persistent
- Образцовый проект Google: https://github.com/android/nowinandroid
