# Backend API – README

## Beschreibung

Die Backend API stellt die zentrale Schnittstelle zur Verwaltung, Validierung und Auswertung aller serverrelevanten
Daten dar. Dazu zählen unter anderem Spieler-, Welten- oder Spielmodi-Daten.

Hierfür werden verschiedene Endpunkte bereitgestellt, die von Plugins aufgerufen werden können. Die zurückgegebenen
Daten lassen sich von den jeweiligen Plugins flexibel und individuell weiterverarbeiten.

Um die Übersichtlichkeit und Wartbarkeit des Projekts sicherzustellen, sind die Implementierungen nach Fachlichkeiten
(Modulen) getrennt. Dieses Konzept wird im Folgenden näher erläutert.

## Projektstruktur

Der grundlegende Packagepfad lautet: `com.minedesso.backendapi`

Für jede Fachlichkeit wird darunter ein eigenes Package angelegt. Eine Fachlichkeit (Modul) bündelt dabei die gesamte
Geschäfts- und Schnittstellenlogik für einen bestimmten Datentyp, z. B. `MinecraftPlayer`.

Für die Fachlichkeit `MinecraftPlayer` ergibt sich folgender Packagepfad: `com.minedesso.backendapi.minecraftplayer`

### Schichtenarchitektur

Jede Fachlichkeit ist in drei Schichten unterteilt, für die jeweils eigene Packages existieren:

- **web**  
  Enthält die REST-Controller und stellt die API-Endpunkte bereit.

- **domain**  
  Beinhaltet die Geschäftslogik, Services sowie DTOs.  
  Diese Schicht bildet das Zentrum der Fachlichkeit.

- **persistence**  
  Enthält Datenbank-Entitäten und Repositories für den Zugriff auf persistente Daten.

Optional können weitere Schichten ergänzt werden, beispielsweise für den Zugriff auf externe Schnittstellen oder
Message-Broker wie RabbitMQ.

Dabei gilt stets:

- Die **Domäne steht im Mittelpunkt** und ist die einzige Schicht, die andere Adapter (z. B. Web oder Persistence)
  ansteuert.
- Adapter dürfen **nicht untereinander kommunizieren**.
- Eine Ausnahme bildet der **Web-Adapter** (REST-Controller):  
  Dieser darf die Domäne über definierte Ports bzw. Use Cases aufrufen.

## Endpunkte

Hier werden alle Endpunkte, nach Fachlichkeit getrennt, aufgeführt. Eine genauere API-Beschreibung bietet
SwaggerUI / OpenAPI (http://localhost:8080/api/swagger-ui/index.html)

### Minecraft Player

| HTTP-Methode | Pfad                         | RequestBody                | ResponseBody            |
|--------------|------------------------------|----------------------------|-------------------------|
| POST         | /api/minecraft-player        | MinecraftPlayerSaveCommand | Void                    |
| GET          | /api/minecraft-player/all    | -                          | List of MinecraftPlayer |
| GET          | /api/minecraft-player/{uuid} | -                          | MinecraftPlayer         |
| DELETE       | /api/minecraft-player/{uuid} | -                          | Void                    |
| POST         | /api/warp                    | WarpSaveCommand            | Void                    |
| GET          | /api/warp/all                | -                          | List of Warps           |
| GET          | /api/warp/{name}             | -                          | Warp                    |
| DELETE       | /api/warp/{name}             | -                          | Void                    |

# 📦 Minecraft Projekt – README

## 📖 Übersicht

Kurze Beschreibung des Projekts:

- Zweck des Plugins/Systems
- Zielgruppe (z. B. Server-Typ)
- Unterstützte Minecraft-Version(en)

---

## 🧑‍💻 Coding Conventions

### Allgemein

- Java-Version: `Java 21 oder 25`
- Code-Sprache: `Java`
- Keine unnötigen Kommentare – Code soll selbsterklärend sein

### Best Practices

- Early Returns statt verschachtelter `if`-Blöcke
- Asynchrone Tasks **nur** bei systemübergreifender Kommunikation
- CleanCode (KISS, DRY, etc.)

---

## 🏷️ Naming Conventions

### Klassen

- Suffixe:
    - XCommand
    - XListener
    - XService
    - XScheduler
    - XAdapter, XController, XRepository, XDto, XEntity
    - etc.
- `PascalCase`
- Aussagekräftige Namen
  **Beispiel:** `PlayerJoinListener`, `DatabaseService`

### Methoden

- `camelCase`
- Verb-basiert  
  **Beispiel:** `loadPlayerData()`, `registerCommands()`

### Variablen

- `camelCase`
- Keine Abkürzungen außer allgemein bekannte  
  **Beispiel:** `player`, `pluginConfig`

### Konstanten

- `UPPER_SNAKE_CASE`  
  **Beispiel:** `DEFAULT_TIMEOUT`

### Packages

- Kleinbuchstaben  
  **Beispiel:** `de.servername.plugin.listener`

---

## 🏗️ Architektur

### Plugin (Minecraft)

- Web (trigger)
- Domain
- API

### Backend-API (Hexagonale Architektur)

- Maven Module

### Frontend

- React Navive

---

## ⚙️ Technologien

- Minecraft API: `Spigot 21`
- Java: `21 oder 25`
- Build Tool: `Maven`
- Datenbank: `PostgreSQL`
- Backend: `Spring Boot`
- Frontend: `Angular`
- JSON Library: `Jackson`
- Boilerpate: `Lombok`

---

## 🔄 Basic Workflows

### Entwicklungsworkflow

1. Feature-Branch erstellen
    - Naming: (#TicketNr)-ticket-name
3. PR erstellen
4. Implementierung
5. Tests durchführen
6. Code Review
7. Merge in `main`

### PR-Workflow

1. Branch fetchen
2. Build prüfen
3. Plugin lädt auf Server
4. Funktion prüfen
5. CleanCode prüfen
6. Rebasen auf `main`
7. Mergen lassen vom Entwickler

### Release-Workflow

1. TODO

---

## ⌨️ Commands

| Command    | Beschreibung        | Permission       | Aliases |
|------------|---------------------|------------------|---------|
| `/example` | Beispiel Command    | `example.use`    | `/ex`   |
| `/reload`  | Reloadet das Plugin | `example.reload` | –       |

---

## 🔐 Permission-Baum

```
example.*
 ├─ example.use
 ├─ example.reload
 └─ example.admin
      ├─ example.admin.kick
      └─ example.admin.ban
```

### Permissions pro Command

| Command    | Permission          |
|------------|---------------------|
| `/example` | `example.use`       |
| `/reload`  | `example.reload`    |
| `/ban`     | `example.admin.ban` |

---

## 📡 Listeners (EventHandler)

### Registrierte Listener

| Event             | Klasse               | Beschreibung               |
|-------------------|----------------------|----------------------------|
| `PlayerJoinEvent` | `PlayerJoinListener` | Initialisiert Spielerdaten |
| `PlayerQuitEvent` | `PlayerQuitListener` | Speichert Spielerdaten     |
| `BlockBreakEvent` | `BlockBreakListener` | Custom Drop-Logik          |

---

## 📝 Sonstiges

- Konfigurationsdateien: `/plugins/Example/config.yml`
- Logs: `/logs/latest.log`
- Support: Discord / GitHub Issues