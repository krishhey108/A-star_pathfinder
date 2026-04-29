# A-Star Pathfinder 🤖

A Java Swing application that visualizes the **A\* (A-Star) pathfinding algorithm** on an interactive 15×15 grid. Users can place a robot, battery, walls, and roads, then watch the robot find and trace the shortest path to the battery in real time using the Manhattan heuristic.

> Developed during a Computer Science & AI internship at **BITS Hyderabad** under the guidance of Dr. Aritra Mukherjee.

---

## ✨ Features

- 🧱 Place **walls (bricks)**, **roads**, a **robot**, and a **battery** on a 15×15 grid
- 🔍 Runs the **A\* algorithm** with Manhattan distance heuristic to find the shortest path
- 🎬 **Animated path tracing** — the robot visually moves step-by-step at 400ms intervals
- 💾 **Save Grid** and **Load Grid** functionality using Java serialization (`.grid` files)
- ⚠️ **Error handling** — prevents placing duplicate robots or batteries with clear dialog messages
- 🖥️ Clean GUI built with Java Swing (`JFrame`, `JButton`, `JComboBox`, `JOptionPane`, `JFileChooser`)

---

## 🖼️ Grid Icons

| Icon | Meaning | Grid Value |
|------|---------|------------|
| 🤖 Robot | Starting position | `2` |
| 🔋 Battery | Target / destination | `3` |
| 🧱 Brick | Wall / obstacle | `1` |
| 🛣️ Road | Open traversable cell | `0` |
| 🟩 Trav | Traced path tile | — |

---

## 🚀 How to Run

**Prerequisites:** Java JDK 8 or higher

```bash
# Clone the repository
git clone https://github.com/Krishhey/A-star_pathfinder.git
cd A-star_pathfinder

# Compile all Java files
javac *.java

# Run the application
java Main
```

Make sure the image assets (`brick.png`, `road.png`, `robot.png`, `battery.png`, `trav.png`) are in the **same directory** as the compiled `.class` files.

---

## 🧠 How It Works

The A\* algorithm is implemented in `RobotGrid.java` using a `PriorityQueue` sorted by `f = g + h`, where:

- **g** = actual cost from the start (robot) to the current cell
- **h** = Manhattan heuristic estimate to the target (battery): `|x1 - x2| + |y1 - y2|`
- **f** = total estimated cost

The algorithm only traverses cells with grid values `0` (road) or `3` (battery), treating bricks (`1`) as impassable obstacles.

---

## 📁 File Structure

```
A-star_pathfinder/
├── Main.java          # Entry point; holds global state (grid array, robot/battery positions)
├── RobotGrid.java     # Core JFrame UI + A* algorithm implementation + Save/Load logic
├── MyListener.java    # ActionListener for grid cell buttons (handles placement logic)
├── GridWrap.java      # Serializable wrapper for saving/loading grid state
├── robot.png          # Robot icon
├── battery.png        # Battery icon
├── brick.png          # Wall icon
├── road.png           # Road icon
└── trav.png           # Traversed path icon
```

---

## 💡 Usage

1. **Launch** the application — a 15×15 blank grid will appear.
2. **Select a mode** from the dropdown: `Brick`, `Robot`, or `Battery`.
3. **Click cells** to place elements. Click a placed element again to remove it.
4. Press **Find Path** to run A\* and animate the robot's journey.
5. Use **Save Grid** to export your layout as a `.grid` file, and **Load Grid** to restore it.

---

## 🏛️ Internship Context

This project was built during a summer research internship at the **Birla Institute of Technology & Science (BITS), Hyderabad**. The internship covered Java GUI programming (Swing, Threads, Action Listeners) and practical AI algorithm implementation, culminating in this pathfinding visualizer.

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
