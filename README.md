# Work Scheduler (GUI)

A desktop scheduling tool that builds efficient employee shift schedules based on business constraints. Managers enter store hours, station hours, employee availability/preferences, and staffing targets. The app then generates a feasible schedule that respects constraints while aiming for coverage and fairness.

---

## What it does
- **Collects inputs via GUI**:
  - **Store Hours** (open/close times, per day)
  - **Stations** (e.g., Register, Grill, Prep, Drive-Thru)
    - Station open hours (per day)
    - Min/Max employees per time block
    - Target/efficient staffing level per time block
  - **Employees**
    - Availability windows (per day)
    - Skill tags / qualified stations
- **Generates a schedule** that:
  - Meets station **minimum coverage** at all times
  - Does not exceed station **maximum capacity**
  - Respects each employee’s **availability & skills**
  - Honors **store/station open hours**
  - Balances hours/fairness (optionally)
- **Outputs**:
  - A **visual schedule** in the GUI (Day/Week view)

---

## Quickstart

### Prerequisites
- [Java JDK 8+](https://www.oracle.com/java/technologies/javase-downloads.html) installed  
- (Optional) [Eclipse IDE](https://www.eclipse.org/downloads/) if you want to open and edit the project  

---

## ▶️ Running the Project

### Option 1: Run in Eclipse (for developers)
1. Open Eclipse  
2. Go to: `File` → `Import...` → `Existing Projects into Workspace`  
3. Select the project folder and click **Finish**  
4. Right-click `Main.java` (inside the `src/main/` folder) → `Run As` → `Java Application`

### Option 2: Run without Eclipse (for users)

1. Open a terminal in the project directory  
2. Compile all source files into the `bin/` folder:
   ```bash
   javac -d bin src/*/*.java src/*/*/*.java
3. Run the main class:
   ```bash
   java -cp bin main.Main

---

## Folder Structure
```plaintext
WorkScheduler/
 ├── .settings/        # Eclipse settings
 ├── bin/              # Compiled classes
 │   ├── data/
 │   ├── display/
 │   ├── main/         # Contains Main.class (entry point)
 │   └── resources/
 ├── media/            # Assets (images, icons, etc.)
 │   ├── NewPage/
 │   └── taskBar/
 └── src/              # Java source code
     ├── data/
     ├── display/
     ├── main/         # Contains Main.java (entry point)
     └── resources/
```
