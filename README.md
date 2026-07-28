<p align="center">
  <img src="src/main/resources/images/logo.png" alt="ScholarMatch logo" width="120"/>
</p>

# ScholarMatch

<p align="center">
  <img src="https://img.shields.io/badge/java-21-blue?style=flat-square&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/build-Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white" alt="Maven"/>
  <img src="https://img.shields.io/badge/architecture-Clean%20Architecture-8A2BE2?style=flat-square" alt="Clean Architecture"/>
  <img src="https://img.shields.io/badge/tests-484%20passing-brightgreen?style=flat-square&logo=junit5&logoColor=white" alt="Tests"/>
  <img src="https://img.shields.io/badge/license-MIT-green?style=flat-square" alt="License"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/UI-Swing%20%7C%20FlatLaf-4B8BBE?style=flat-square" alt="UI"/>
  <img src="https://img.shields.io/badge/API-Semantic%20Scholar-1857B6?style=flat-square" alt="Semantic Scholar API"/>
  <img src="https://img.shields.io/badge/coverage-100%25%20branches-F8952D?style=flat-square&logo=jacoco&logoColor=white" alt="JaCoCo"/>
</p>

*A cross-platform Java desktop app that helps students, researchers, and academics discover collaborators, post and apply to research opportunities, and message their matches — an "Academic Matchmaking and Collaboration Network."*

ScholarMatch pairs a recommendation feed (ranked by shared research interests) with a lightweight job board for research postings and a private chat for confirmed matches. Around that core are:

* Profile building with paper lookup autofill (Semantic Scholar)
* Mutual-match connect/dislike, with a dedicated matches list and messaging
* Research postings: create, browse, apply, accept/decline applicants
* Account settings with verified email changes and password changes

---

## Table of Contents

* [Quick Facts](#quick-facts)
* [Team & Use-Case Owners](#team--use-case-owners)
* [Features](#features)
* [Architecture](#architecture)
* [Getting Started](#getting-started)
  * [Online Mode (default)](#online-mode-default)
  * [Offline Mode](#offline-mode)
* [Usage](#usage)
* [Testing & Code Quality](#testing--code-quality)
* [Documentation](#documentation)
* [Accessibility](#accessibility)
* [Contribution Guide](#contribution-guide)
* [License](#license)

---

## Quick Facts

| Item             | Details                                                                                     |
| ---------------- | --------------------------------------------------------------------------------------------- |
| **Domain**       | Academic matchmaking and research collaboration                                               |
| **Users**        | Students, graduate researchers, postdocs, faculty, and industry researchers                   |
| **Tech stack**   | Java 21 · Maven · Swing (FlatLaf) · Jackson · JUnit 5 · Mockito                                |
| **Architecture** | Clean Architecture (entity & use case & interface adapter & frameworks/drivers)                |
| **Backend**      | Spring Boot REST API + Postgres, deployed separately on Railway (client talks to it over HTTP) |
| **Modules**      | Auth · Profile · Recommend/Match · Messaging · Postings & Applications · Account Settings      |
| **OS**           | Windows · macOS · Linux (desktop Java)                                                        |

**Codebase structure at a glance**

```text
app/                        start-up bootstrap & dependency wiring (AppBuilder, ScholarMatchApp)
entity/                     core domain models (User, Posting, Message, Publication…)
usecase/                    interactors, boundaries, and DTOs — one package per user story
interface_adapter/          controllers, presenters, view models
frameworks/
  data_access_object/       real HTTP gateways (server/) and the in-memory offline repository
  gui/                      Swing views, panels, navigation, theming
```

---

## Team & Use-Case Owners

| Member  | GitHub          | Primary Use Cases                                                                 |
| ------- | --------------- | ----------------------------------------------------------------------------------- |
| Guancheng Chen | `@Guancheng-Chen` | Recommend (ranked list, verification badges) · Load Matches · Messaging: send message, load conversation |
| Marian Lin     | `@Marian7101`     | Connect · Dislike · Skip · Postings: create, browse, apply, close |
| Zhijie Yuan    | `@KazeHubuki` / `@ShiinaMahiruOvO` | Profile (view/update) · Paper lookup (Semantic Scholar) · Applications: accept, decline, my applications |
| Alan Xue       | `@alan746`        | Register · Login/logout · Email verification · Account settings: change email, change password, delete account |

---

## Features

**Ranked Recommendations & Matching**
- **Interest-Ranked Feed:** Browse a recommendation feed of other researchers, ranked by shared research interests, with an academic-email verification badge on each card.
- **Mutual-Match Flow:** Connect or skip candidates; a match only unlocks messaging once both sides connect, with a dedicated matches list to track who you're paired with.

**Profile & Paper Lookup**
- **Rich Academic Profile:** Build a profile with institution, academic level, research field, funding status, collaboration preferences, education, and publications.
- **Semantic Scholar Autofill:** Search for yourself on Semantic Scholar and pull in your h-index, citation count, and publication list instead of typing them by hand.

**Research Postings & Applications**
- **Create & Browse Postings:** Post a research opportunity with a title, description, research field, collaboration type, and team capacity, then browse active postings from other researchers.
- **Apply & Review Applicants:** Apply to a posting with a message, and as a poster, accept or decline applicants and track your own application history — postings close automatically once full.

**Private Messaging**
- **Match-Only Conversations:** Message only the people you've mutually matched with, keeping conversations scoped to real connections rather than open to anyone.
- **Persistent Conversation History:** Revisit past messages with a match at any time from the chat view.

**Account & Security**
- **Verified Email Changes:** Change your account email through a code-verification flow rather than a plain, unchecked update.
- **Full Account Lifecycle:** Register, log in/out, change your password, and delete your account (and its associated data) whenever you choose.

---

## Architecture

ScholarMatch follows **Clean Architecture**. Every feature is wired through the same four layers, and `AppBuilder` assembles them at startup in a fixed order (session → repositories → view models → presenters → interactors → controllers):

1. **Entities** (`entity/`) — plain domain objects with no framework dependencies.
2. **Use Cases** (`usecase/`) — one package per user story, each with an interactor, input/output boundary, and a narrow `DataAccessInterface` describing only the persistence it needs.
3. **Interface Adapters** (`interface_adapter/`) — controllers translate GUI events into use-case input; presenters translate use-case output into view-model state.
4. **Frameworks & Drivers** (`frameworks/`) — Swing views under `gui/`, and the two interchangeable data-access implementations under `data_access_object/`:
   * `server/` — real HTTP gateways that call the ScholarMatch backend (see [`docs/api/BACKEND_SERVER_API.md`](docs/api/BACKEND_SERVER_API.md)).
   * `localMockServer/` — the `Local*Repository` classes (auth, profile, matching, messaging, postings, account settings), an in-memory fake used for offline/demo mode.

`AppBuilder` decides once at startup, per feature, whether a use case talks to the real server or to the in-memory offline repository — see [Getting Started](#getting-started) below.

---

## Getting Started

### Requirements

* **Java 21** (JDK)
* **Maven 3.9+**
* OS: **Windows / macOS / Linux**

### Clone & Build

```bash
git clone https://github.com/Guancheng-Chen/ScholarMatch.git
cd ScholarMatch

mvn clean verify   # compiles, runs checkstyle, runs the test suite
```

### Online Mode (default)

Just running the app with no configuration uses **online mode**: the client talks directly to our own hosted ScholarMatch server, with registration / email-change verification codes delivered to your **real inbox** via the server's Resend integration.

```bash
mvn exec:java
```

If the server is unreachable, the client automatically falls back to offline mode so a demo isn't derailed by the server being down.

### Offline Mode

Offline mode runs the entire app against **in-memory fake repositories** (the `Local*Repository` classes) — no server, no database, no real emails sent. It's pre-seeded with a few demo scholars (Ada Lovelace, Alan Turing, Grace Hopper, Demo Student) so Recommend/Connect/Matches work immediately, and connecting with any seeded user always reports an instant mutual match.

```bash
OFFLINE_MODE=true mvn exec:java
```

**Email verification in offline mode is received locally, not by email.** Both the registration code and the "change my email" code are printed straight to the console instead of being sent through Resend:

```text
[Offline demo] Verification code for jane.doe@example.com: 482913
```

Just copy the code from the terminal into the app's verification field — no real mailbox involved. This makes offline mode fully self-contained: you can register a brand-new account, verify it, and use every feature with no internet connection at all.

| Mode        | Trigger                                              | Data                                   | Verification code delivery                    |
| ----------- | ----------------------------------------------------- | --------------------------------------- | ------------------------------------------------ |
| **Online**  | Default; server reachable                              | Real REST API + Postgres (Railway)      | Real email, via the server's Resend integration   |
| **Offline** | `OFFLINE_MODE=true`, *or* automatically if the server's health check fails | In-memory (the `Local*Repository` classes), resets every run | Printed to the console (`[Offline demo] ...`)     |

### Environment Variables

| Variable       | Required | Default                                                    | Purpose                                                        |
| -------------- | -------- | ----------------------------------------------------------- | ------------------------------------------------------------- |
| `SERVER_URL`   | No       | `https://scholarmatch-server-production.up.railway.app`     | Base URL of the ScholarMatch REST API                          |
| `OFFLINE_MODE` | No       | unset                                                        | Set to `true` to force offline mode regardless of server health |

See [`docs/api/BACKEND_SERVER_API.md`](docs/api/BACKEND_SERVER_API.md) for the full endpoint reference and client-side wiring details.

---

## Usage

1. **Register or log in.** New accounts need a verification code first — request one, then check either your inbox (online) or the terminal (offline) for the code.
2. **Build your profile.** Add your institution, research interests, and papers — use the built-in paper lookup to autofill publications from Semantic Scholar.
3. **Recommend.** Review candidate profile cards; connect or pass. A mutual connect is an instant match.
4. **Matches & Messaging.** View your matches list and open a chat with anyone you've matched with.
5. **Opportunities.** Browse open postings, apply with a short message, or create your own posting and manage incoming applications (accept/decline).
6. **Account Settings.** Change your password, or change your email (requires a fresh verification code sent to the new address).

---

## Testing & Code Quality

```bash
mvn test                # run the JUnit 5 test suite
mvn verify               # tests + Checkstyle + JaCoCo coverage report
```

* **Checkstyle** runs on `validate` using `mystyle.xml` and fails the build on violations.
* **JaCoCo** produces a coverage report under `target/site/jacoco/` after `mvn test`/`verify`.

---

## Documentation

* [`docs/api/BACKEND_SERVER_API.md`](docs/api/BACKEND_SERVER_API.md) — full REST API reference and client wiring notes
* [`docs/api/SEMANTIC_SCHOLAR_API.md`](docs/api/SEMANTIC_SCHOLAR_API.md) — third-party paper lookup API
* [`docs/uml/`](docs/uml) — Clean Architecture class diagrams and sequence diagrams, one per use case
* [`docs/user_story/`](docs/user_story) — user stories behind each use case
* [`docs/sketch-views/`](docs/sketch-views) — early UI sketches
* [`docs/ui-ux/`](docs/ui-ux) — UI/UX design assets (reserved, not yet populated)
* [`docs/format-conventions/`](docs/format-conventions) — commit, branch, PR, and issue templates used on this project

---

## Accessibility

ScholarMatch follows the **social model of disability**: one UI for everyone, no separate "accessible mode." See the full [Accessibility Report](docs/accessibility_report.md) for a principle-by-principle status (equitable use, flexibility, error tolerance, etc.) and the prioritized next steps — currently the weakest areas are text scaling/dark theme and a complete keyboard-only path.

---

## Contribution Guide

* Branch naming: `issue-<issue number>-<name>-<CA layer/test>-<use case name>` (see [`docs/format-conventions/branch_naming_convention_eg.md`](docs/format-conventions/branch_naming_convention_eg.md))
* Commit format: `<type>(<scope>): <Description>`, where `<type>` is one of `feat`, `fix`, `refactor`, `docs`, `test` (see [`docs/format-conventions/commit_format_convention_eg.md`](docs/format-conventions/commit_format_convention_eg.md))
* Pull requests use the template in [`docs/format-conventions/pull_request_summary_format_convention_eg.md`](docs/format-conventions/pull_request_summary_format_convention_eg.md)
* Run `mvn verify` locally before pushing — the same checks (tests + Checkstyle) gate merges

---

## License

Licensed under the **MIT License**. See [LICENSE](LICENSE) for details.
