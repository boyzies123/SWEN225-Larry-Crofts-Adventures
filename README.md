# Larry Croft's Adventures: Chips Challenge Clone

This game is a single-player graphical adventure game, a creative clone of the first level of the 1989 Atari game Chips Challenge. Developed primarily in Java, it is designed to run on a stock installation of Java 17. In this game, players explore an imaginary world, collecting objects, solving puzzles, and performing actions to complete the game. The game's architecture consists of several modules, including Domain, App, Renderer, Persistency, and Recorder.

## Deployment

To deploy this project, follow these steps:

1. Clone the project: `git clone https://gitlab.ecs.vuw.ac.nz/course-work/swen225/2023/project1/team24/SWEN225-Larry-Crofts-Adventures.git`
2. Import project into Eclipse IDE: **File** > **Import** > **Existing Projects into Workspace**
3. Download Maven requirements: Right click project name **Run As** > **Maven install**
4. Build the Maven project: Right click project name **Run As** > **Maven build** then click **Run**
5. Update the project: Right click project name **Maven** > **Update Project...** then click **OK**
6. You should now be able to work on the project.

## Start Game

To start the game, follow these steps:

1. Follow the [Deployment](#deployment) steps above to compile the game.
2. Right click `nz.ac.wgtn.swen225.lc.app.Main` and **Run As** > **Java Application** to start the game.

## Recording
#### Creating a Recording

Recording can be done by pressing the R key. It can only be done when it is not in a replaying state. Even if you exit out of the application without saving, the recorded data will be saved. Recorded data will also be saved and recording will stop when player completes a level.

#### Saving a Recording

Saving can be done by pressing the S key. Saving only works if game is in recording state. It will be saved to a file called "Recorded data.json".

#### Loading a Recording

Loading can be done by pressing the L key. Note: loading can only be done when game is not in recording or replaying state. Once game is loaded, you can choose to do step by step replay or auto replay.

Auto Replay: This can be done by pressing the A key. Once auto replay has commenced, you can choose to set the speed by pressing a key from 1 to 5. 

Step by Step Replay: This can be done by pressing the B key. Every time you want to move a step, just press b.

Note: After a replay has finished, and you want to replay the same recording, you will have to load the "Recording data.json" file again. 

## Documentation

For detailed documentation about the game modules, refer to the documentation located in the `docs` root folder. Open the `index.html` file to view.
