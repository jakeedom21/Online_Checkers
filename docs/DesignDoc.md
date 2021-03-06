---
geometry: margin=1in
---
# PROJECT Design Documentation

# Team Information
* Team name: 2175-swen-261-02-d
* Team members
  * Joel Margolis
  * Brandon Dossantos
  * Jake Edom
  * Qadir Haqq
  * Sameen Luo

## Executive Summary

An application that allows signed in players to play checkers with other signed in players. Players use drag and drop capabilities in the browser to make moves.

### Purpose

Allow players to play checker games in their browsers.

### Glossary and Acronyms
> Provide a table of terms and acronyms.

| Term | Definition |
|------|------------|
| VO | Value Object |


## Requirements

This section describes the features of the application.

### Definition of MVP

Every player must sign-in before playing a game, and be able to sign-out when finished playing.
Two players must be able to play a game of checkers based upon the American rules.
Either player of a game may choose to resign, at any point, which ends the game.

### MVP Features

Start a Game
As a Player I want to start a game so that I can play checkers with an opponent.

Validate Moves
As a player I want to be able to drag and drop valid checker pieces so that I can make a move during a game.

Game Forfeit
As a player, I want to resign my game at any point so that I can forfeit.


### Roadmap of Enhancements

* Extend the Game View to support the ability for player to request help for the next move.

* Games can be stored and viewed later as how it was played before.

## Application Domain

This section describes the application domain.

![The WebCheckers Domain Model](domain-model-placeholder.png)

The most important entities in the domain of this project are *player*, *board*, *pieces* and the *checkers* game. 
Two *players* (red/white) initiates a game of *checkers*. They are given the same *board* on their browser with opposite rotations. 
On their *board* in the browser, each *player* has the ownership of 12 pieces according to his/her color. The *player* can drag and drop
one movable *piece* (according to American rule) each turn to make a move to take off his/her opponent's *pieces* as many as possible 
to win this *checkers* game. 

## Architecture

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

As a web application, the user interacts with the system using a browser.  The client-side
of the UI is composed of HTML pages with some minimal CSS for styling the page.  There is also
some JavaScript that has been provided to the team by the architect.

The server-side tiers include the UI Tier that is composed of UI Controllers and Views.
Controllers are built using the Spark framework and View are built using the FreeMarker framework.  The Application and Model tiers are built using plain-old Java objects (POJOs).

Details of the components within these tiers are supplied below


### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the WebCheckers application.

![The WebCheckers Web Interface Statechart](web-interface-placeholder.png)

Upon opening the home page in a browser, a user is shown the signin link. Once clicked on the signin link, the user is brought to the 
signin page. There, the user enters their username and clicks signin to submit. Once submit, the user is shown a list of
signed in opponents to choose from is there are any. If not, the wait message shows until another player signs in. 
After the user clicks on an opponent to start a game, they are brought to the game page where the board with their 
red/white pieces are shown. On the board, they can drag and drop their own pieces to make moves. 

### UI Tier
The **GetHomeRoute** renders home page that lists all main functions of the project (sign-in/sign-out button,
list of opponents to choose from). Once a user signs in, the opponent is picked and **GetGameRoute** renders
the game page with oriented checkers board with pieces for the user to drag and drop. Once game is finished
or resigned, the winner and loser are both redirected to result page through **PostResignRoute** and **GetResultRoute**
to be notified win/lose status.

#### Static models
![UI Tier Class Diagram](UI-Tier-class-diagram.png)

#### Dynamic models
![PostSignIn Sequence Diagram](UI_SignIn_sequenceDiagram.png)

Jim opens up the home page of web checkers. He sees that there are 3 other users logged in 
on the page. To play a game with them, he clicks the sign in button and gets redirected to the 
sign in page. There he sees a box to enter his username and a button to submit. He enters the 
username "SuperDuperJim" (without the quotation marks) and hits submit button. His username is verified, 
and he got redirected back to home page with a list of users to start a game with.

![GetGameRoute Sequence Diagram](UI_GameRoute_sequenceDiagram.png)

Also Jim, whose username is "NoChillJim" is on the home page after signed in. He picks a player 
named "SuperLameJim" to start a game with, so he clicks on the "Start a game with SuperLameJim" button. 
User SuperLameJim is not in a game with anyone at this point so both NoChillJim and SuperLameJim gets 
redirected to game page. The view NoChillJim gets is with 12 red pieces at the bottom, and the view SuperLameJim
gets is with 12 white pieces at the bottom. They are given the same board.

### Application Tier
The application tier of the project handles all the move, validation, and player storage. The application diagram above
shows how the different components of the application tier communicate between each other. 

#### Static models
![Application Tier Class Diagram](Application-Tier-class-diagram.png)

#### Dynamic models
![Application Tier Sequence Diagram](Application-Tier-Sequence-Diagram.png)

Add dynamic models here and then they describe the dynamic  behavior over time of different objrcts and how they wor togehrer.
### Model Tier
The model tier works to emulate the various pieces that make up a game of checkers including a Game object which
emulates the game overall holding the board and players and determining a winner once one of the win conditions has
been achieved. Moving to the Board object which works as the board of the game responsible for holding spaces and moving
pieces around when needed. A Space is the object which fills up the board object and holds pieces in place their places
as well. Next is the piece object which acts as the pieces on a checkers board which which have their own color and
location, additionally know if they are a king piece or not. Next is the Player object which holds the total amount
of pieces left, as well as keep track if they are in a game for the server's sake. Finally are the Move and Message
objects, which act as helper functions with Move it helps place moves into a readable notation and Message works to send
string info to wherever it is needed.

#### Static models
![Model Tier Class Diagram](Model_tier_class_diagram.png)

#### Dynamic models
![Model Tier Board Sequence Diagram](Model_tier_Board_sequence_diagram.png)

![Model Tier Game Sequence Diagram](Model_tier_Game_sequence_diagram.png)


## Test Coverage
![Project Test Coverage Chart](test_coverage.png)


## Future Refactoring and Improvements
One major improvement would be the final debugging of multi-jump as that was unable to be fully completed by the team.
Other improvements could be fully going through the code to ensure that everything that can be in the Constants file is
in there and is being used in all applicable places, ensuring consistancy within the code. We could use more through
unit tests as while there were enough to sufficiently cover the code more would definitely improve the quality of the
code especially in the application tier which out of the 3 tiers has the lowest amount of coverage. Additionally given
more time we would have implemented player help more so how we originally wanted, displaying a little board that
highlights the pieces that can move and highlighting the places that those pieces can move too.

### Code Metrics 

![Complexity Metrics on Methods](complexity_method.png)

![Complexity Metrics on Classes](complexity_classes.png)

The more cyclomatic-complex methods are located in MoveValidation class, Move class, and GetGameRoute class.

For MoveValidation and Move class, the number of switch cases (if statements, for and while loop, and/or conditions) 
are necessarily larger because there are a lot of different formations of the board that must be considered, and there 
are a lot of rules that goes into checking if a move is valid. Modularising the rules into different parent classes 
would increase the cohesion but could potentially increase the coupling because moves have to be tested against the rules
eventually. 

For GetGameRoutes's render method, it has a lot of cases because the busy opponent error handling and 
win/lose/resign redirection are all in the same method. A better way would be to refactor busy opponent error handling 
and redirection into different classes, and separate different redirect cases into smaller methods also. This would 
increase WMC (weighted methods per class) but it would still be under the threshold.  

No warnings found in the other metrics categories (Chidamber-Kemerer metrics, Complexity metrics, 
Javadoc coverage metrics, Lines of code metrics, and Martin package metrics).

### Design Principles

Discuss the quality of your design along with recommending future refactoring and other design improvements based on 
your metric analysis and review of the design against the object-oriented design principles covered in class.

#### Single Responsibility

The first and most important SOLID principle is Single Responsibility (SRP).

SRP suggests "there should never be more than one reason for a class to change."
             
Here I provide an example within our design where we could fault to adhering to SRP.
The Game class is too congested with methods that add responsibility to a game object. 
For example, the game class focuses on managing objects and has methods to check the 
state of the game. Through the addition of the Player Replay enhancement, there has been 
additions to the game class that skew its single responsibility and add responsibility to 
modify, commit, and get moves. A new class that manages all game queue actions should be implemented
to adhere to SRP. 


    public class Game {

        public Game(Integer id, Player p1, Player p2);
    
        public Player getPlayer1(); 
    
        public Player getPlayer2 (); 
    
        public Board getBoard(Player p);
    
        public String getPlayerTurn();
    
        public void finishMove(); 
    
        public boolean isGameWon();
    
        public String getWinner(); 
    
        public void setForfeit(String playername);
    
        public boolean didPlayerResign(); 

        public int getId();
    
        public void queueMove(Move move);

        public Move getNextMove(); 
    
        public void commitMove(Move m); 
    
        public void movePiece(Move m);
    } 

#### Bloated Controller

We can make an object a Controller in this case if the object represents a 
use case, handling a sequence of operations. The benefit of doing so is to, 
reuse the controller class, maintain the state of the use case, and control
the sequence of the activities. Though a controller can easily become bloated 
if the class is overloaded with too many responsibilities. In the GetOldGamesRoute
below, the route is responsible for initializing the old game object and performing
modifications and checks. The controller could instead be delegating these tasks
to another class, that could be the intermediary between the route and the game objects.

A solution would be to create a class that is responsible for querying an old game
performing actions against it. In this case, it would be simple to fix since there are
not many objects affected by the code smell, but for future development, the team 
seeks to be more efficient and write more readable code. 

    public class GetOldGamesRoute implements Route {
        public GetOldGamesRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
  
        public Object handle(Request request, Response response) {
            Session currentSession = request.session();
            String playerName = currentSession.attribute(Constants.PLAYER_NAME);
            Player player = playerLobby.getPlayerByUsername(playerName);
    
            Map<String, Object> vm = new HashMap<>();
            int namesLen = player.getOldGames().size();
            String[] names = new String[namesLen];
    
            for (int i = 0; i < namesLen; i++) {
                Game game = player.getOldGames().get(i);
                game.copyReplayIntoQueue();
                game.resetBoard();
                String player1Name = game.getPlayer1().getPlayerName();
                String player2Name = game.getPlayer2().getPlayerName();
                names[i] = playerName.equals(player1Name) ?  player2Name : player1Name;
            }
    
            vm.put("oldOpponentGames", names);
            vm.put("playerName", playerName);
    
            return templateEngine.render(new ModelAndView(vm, "oldGames.ftl"));
        }
    }

#### Polymorphism

In order to handle related but varyian elements based on element type,
polymorphism guides us in deciding which object is responsible for 
handling those varying elements. In the case of web checkers, a piece 
can only be of two entities, a regular piece or a king piece. The way
the team has implemented such distinctions is through a boolean value 'isKing'.
The Piece class holds the boolean value along with functions that will
set and check the value. To fully adhere to polymorphism, the team 
can divide the possible entities into sub classes using inheritance. The parent
class would be 'Piece' and the child class would be 'King'. The King object
will inherit common behavior to a piece, but override movement functions
to perform like a 'King'. 


    public class Piece implements Serializable {
    
        public Piece(int row, int col, String color) {
            this.row = row;
            this.col = col;
            this.color = color;
            this.isKing = false;
        }
    ...
        /**
         * Sets the Piece as a King
         */
        public void setKing() {
            isKing = true;
        }
    
        /**
         * Returns whether the piece is a king or not
         * @return boolean
         */
        public boolean isKing() {
            return isKing;
        }
    ...

    }

