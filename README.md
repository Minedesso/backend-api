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

| Command | Beschreibung | Permission | Aliases |
|-------|--------------|------------|---------|
| `/example` | Beispiel Command | `example.use` | `/ex` |
| `/reload` | Reloadet das Plugin | `example.reload` | – |

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

| Command | Permission |
|-------|------------|
| `/example` | `example.use` |
| `/reload` | `example.reload` |
| `/ban` | `example.admin.ban` |

---

## 📡 Listeners (EventHandler)

### Registrierte Listener

| Event | Klasse | Beschreibung |
|-----|--------|--------------|
| `PlayerJoinEvent` | `PlayerJoinListener` | Initialisiert Spielerdaten |
| `PlayerQuitEvent` | `PlayerQuitListener` | Speichert Spielerdaten |
| `BlockBreakEvent` | `BlockBreakListener` | Custom Drop-Logik |

---

## 📝 Sonstiges
- Konfigurationsdateien: `/plugins/Example/config.yml`
- Logs: `/logs/latest.log`
- Support: Discord / GitHub Issues
