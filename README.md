# Overview
A text-based, console dungeon traversing game that allows a player to move, explore and interact with randomly generated dungeon rooms. The player receives printed informative and immersive prompts automatically or on command,
regarding the rooms they visit and be able to manage in-game items such as spells, tools and food that they will find along the way. There is a limit for tool and spell capacity so the player needs to make decisions 
about what to have at hand. 

Powerpoints of the player reaching zero will determine losing the game, and reaching the treasure on the final floor of three will determine winning. Negative room scenarios such as traps or mad scientists will 
potentially reduce powerpoints while food may increase them. 

## Technologies Used
- IntelliJ IDEA 2023.2.8
- Oracle OpenJDK version 11.0.21
- No external dependancies

### Usage
- Import project into IntelliJ 
- Build the project in IntelliJ
- Start program from Game class file

##### Playing
As available from in-game help display upon starting or by using prompt "help":
====================== Playing the game ======================
- The objective of the game is to successfully navigate the dungeon to the treasure room
- You may lose powerpoints through room encounters, reaching less than 1 powerpoint will result in a loss
- There are many in-game items that may help you along your way! How you use them is up to you
- You have a bag for items, a spellbook holder and a basic toolbelt. You may open your inventory at any time
====================== Commands ======================
- To perform actions in game, enter commands in the format of an action word followed by the specific thing to perform that action on
- Action examples: Walk, Take, Drop, Use, Eat, Drink, Cast, Examine, Look
- Action argument examples: North, Loaf of bread, Spanner, Cake, Potion, Freeze spell, Hammer, Around
- You may quit at any time with \"quit\" command
====================== Navigation ======================
-Navigating is based on top-down perspective, i.e up and north are equivalent
- There are multiple levels to the dungeon, finding the exit will progress you to the next one until one contains the treasure
- You will need to find your way through rooms that may have doors or walls around them
- The grand wizard that sent you on your quest supplied you with a magic map that inscribes upon it where you've been and where you are
- You may view your map at any time with map command

##### Author
github.com/MuhJava
