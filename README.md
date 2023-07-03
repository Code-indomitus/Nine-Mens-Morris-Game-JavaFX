# PROJECT NINE MEN"S MORRIS GAME

We have developed the Nine Men's Morris game using **java** along with the **javafx** GUI framework. We have used Maven to handle all our projects dependencies. All development was carried out using the Intellij IDEA IDE.
## Sprints
ALl the project work has been broken down into 4 sprints. All the material and deliverables regarding the specific sprints can be found in the folders labelled Sprint 1 to Sprint 4.
_______________________________________________________
### Advanced Requirements
We are a team of 4 so we have chosen the following 2 advanced requirements from the available advanced requirements in the assignment brief to design and implement:

(a) Considering that visitors to the student talent exhibition may not necessarily be familiar with 9MM, a **tutorial** mode needs to be added to the game. Additionally, when playing a match, there should be an option for each player to toggle **“hints”** that show all of the legal moves the player may make as their next move. 

(c) A single player may play against the **computer**, where the computer will randomly play a move among all of the currently valid moves for the computer, or any other set of heuristics of your choice.
_______________________________________________________
### Game - Video Demonstrations

**Sample Basic Gameplay:** [Video Link](https://youtu.be/DNDBY1z8Ys0)

Do control the headphone volume as the game has sound effects.
______________________________________________________
### Instructions to run our Nine Men's Morris game
**Required OS:** Since we have provided a jar file it can be run on any computer device with a satisfactory JVM version. However, there have been some difficulties running the jar file on devices with the MacOS which is still being figured out.

1) Download the JAR executable file from this [link](https://git.infotech.monash.edu/fit3077-s1-2023/MA_Friday2pm_Team12/project/-/blob/main/NineMenMorris_T12.jar). The jar file (NineMenMorris_T12.jar) is available in the root folder of this repository.
2) A jdk (Java Development Kit) version of 17.0.2 or higher is required to run this application. 
3) Once the JAR executable file is downloaded simply open it in open it in your device folder and double click the JAR file to run it. The game application should run.
4) If the game application does not run it is very likely that the device it is being run on does not meet the **jdk 17.0.2** or higher requirement.
5) To satisfy the jdk requirement and run the game application successfully please download and install the satisfactory jdk version from this [link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html). Select download for the **Java SE Development Kit 17.0.7** which is the latest version of 17.
6) After downloading and installing the required jdk, the game application should be able to run by double clicking on the JAR executable file.
7) Other than the JAR and the jdk there is nothing else needed to be downloaded or installed.


Link for executable JAR file: [Nine men's morris executable jar](https://git.infotech.monash.edu/fit3077-s1-2023/MA_Friday2pm_Team12/project/-/blob/main/NineMenMorris_T12.jar)

_______________________________________________________
### Nine Men's Morris Game Rules
Listed below are the rules that are relevant to the Nine Men's Morris game that we have developed.

- The game has two phases the *placing phase* and the *moving phase*.
- There are two players in total and each player has 9 *pieces* to place on the *board*.
- The game starts with the placing phase where each player takes a turn to place their token on an *open intersection* on the board.
- Once all the pieces are placed, the game moves into the *moving phase*. Each player can move their pieces turn by turn to only *adjacent intersections* on the board.
- A *mill* is formed when 3 of the same coloured pieces are lined vertically or horizontally. When a player forms a mill they are allowed to *remove* one of their opponents piece from the board and that piece will never come into play again.
- Keep in mind that only pieces that are *not part of a mill* can be removed from the board unless the case where all the pieces are part of a mill and removing any one of them is fine.
- When any of the players are down to only 3 pieces on the board, they can move their pieces to any vacant intersection on the board also called *flying*.
- The *winner* is declared when a player is able to bring their opponent down to 2 pieces or is able to suffocate the pieces of their opponent such that their opponent is unable to move their pieces anywhere.
- There is *no draw condition* in the game unless both players don't want to continue playing the game and mutually decide to consider the game a draw.
_______________________________________________________
### Acknowledgements

#### JavaFX
Some of the javafx and game board development concepts we have used in the game were inspired by [link](https://www.youtube.com/watch?v=6S6km5duBrM&t=1236s). However, our design and implementation differs drastically in many ways based on our own ideas, style of design, implementation choices and game logic.

#### Visual Art

Game Board assets acquired from: [Link 1](https://www.vecteezy.com/members/chamkrajang), [Link 2](https://www.vecteezy.com/members/anomaria?license_type=free&page=2)

Game Background: [Link](https://www.freepik.com/)

#### Sounds
The following are the sources of the **game sounds** we have used in our application.

Below sounds have been acquired from [zapsplat.com](https://www.zapsplat.com/sound-effect-categories/):
- error_move.wav
- piece_sound.wav
- winner_sound.wav
- remove_sound.wav

Below sounds have been acquired from [samplefocus.com](https://samplefocus.com/collections/mortal-kombat-announcer):
- fatality.wav
- finish_him.wav
- flawless_victory.wav
- excellent.wav
- impressive.wav
- outstanding.wav
- well_done.wav


### Project Contributors
Shyam Kamalesh Borkar
Carson Lai
Richardo Husni
Victor Lua