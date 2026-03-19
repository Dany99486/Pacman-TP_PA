# TinyPAc (Advanced Programming Project - ISEC)

## Project Summary
TinyPAc is a Java-based Pac-Man-style game developed as a final project for the Advanced Programming course (ISEC). It combines a clean model-view architecture, a finite-state machine (FSM) for game control, and a JavaFX graphical interface for interactive play.

The repository contains two milestones:
- **Meta1:** core engine and text-based gameplay (console loop, input-driven FSM, basic map parsing)
- **Meta2:** complete JavaFX GUI, game manager logic, ghost behavior, level loading, and final game polish

---

## What You Can Do
- Play a Pac-Man-like game with animated Pacman and ghosts
- Use multiple levels loaded from external map files
- Track score, lives, and high-score/top-5 data
- Start/pause/resume game states via a finite state machine
- Switch between menu and gameplay screens in JavaFX

---

## Repository Structure
```
Meta1/
  PA_TP/
    src/tp/isec/pa/tinypac/ ...
    maps/
      level00.txt ... level20.txt
Meta2/
  PA_TP/
    src/tp/isec/pa/tinypac/
      TinyPAcMain.java
      gameengine/
      model/
      ui/gui/
      ui/text/
    maps/
      level00.txt ... level20.txt

```

- `Meta1` contains the initial text-based engine and FSM design.
- `Meta2` contains the final JavaFX GUI implementation and completed gameplay.
- `/maps/` contains the ASCII map files used by the game engine.

---

## Architecture
### Core Components
- **Game Engine:** tick-based update loop with separate engine and state evolution interfaces
- **FSM:** menu, prepare/game start, pause, haunt, game-over states
- **Model:** Pac-Man, ghosts, maze elements, and collisions
- **UI:** JavaFX screens (main menu, game board, score/lives display) plus legacy text UI

### Level Loading
Map files (`levelXX.txt`) use ASCII tile definitions to represent walls, pellets, power-ups, spawn points, and empty spaces. The manager loads and evolves game state by row/column positions.

---

## Running the Game (Recommended)
### Option A: IntelliJ IDEA
1. Open `Meta2/PA_TP.iml` or import folder as a Java project.
2. Configure Java 17+ and JavaFX SDK in project settings.
3. Run `tp.isec.pa.tinypac.TinyPAcMain`.

### Option B: Command Line (Java + JavaFX)
1. Ensure Java 17+ and JavaFX are installed.
2. Use a command similar to:
   ```bash
   java --module-path <path-to-javafx-lib> --add-modules javafx.controls,javafx.fxml -cp out/production/PA_TP tp.isec.pa.tinypac.TinyPAcMain
   ```
3. Adjust classpath and module path according to your build output.

---

## Controls
- **AWSD keys**: move Pac-Man
- **Enter / Start**: begin the game from menu
- **P / Pause**: pause/resume

---

## Notes for Developers
- The game logic is primarily in `Meta2/src/tp/isec/pa/tinypac/model` and FSM states in `.../fsm`.
- UI view layout is in `Meta2/src/tp/isec/pa/tinypac/ui/gui`.
- Map design can be extended by editing `maps/levelXX.txt`.

---

## Project Deliverables
- **Meta1 deliverable:** playable text mode engine with FSM and level parsing.
- **Meta2 deliverable:** fully integrated JavaFX game with final gameplay

