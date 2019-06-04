Project B-Tatsu


Introduction:
This game is a fighting game(think Street Fighter or Mortal Kombat) in which the primary objective is not to deplete your opponent’s life, but to take over territory by pushing your opponent’s character back with attacks. All the space behind your character is considered yours. The goal is to have the most space behind you at the end of 60 seconds. Alternatively, hitting your opponent with a move that has a “knockout” property in the corner will win you the round instantly. The game will be mostly inspired by the gameplay of Super Street Fighter 2 Turbo, with inspiration drawn from other fighting games such as Guilty Gear and Street Fighter 3: Third Strike.


Instructions:
The game is controlled with 4 directional buttons and 3 attacking buttons: A(Light), B(Medium), C(Heavy). Optionally, you can use a 4th button to jump instead of up if you’re comfortable with that coming from games like Smash Bros. 
1P: (Default controls)
    W - Jump
    A - Move left
    S - Crouch
    D - Move right
    T - A(Light) attack
    Y - B(Medium) attack
    U - C(Heavy) attack
    G - Optional jump
2P: (Default controls)
    L - Jump
    , - Left
    . - Crouch
    / - Right
    Left Arrow - A(Light) Attack
    Down Arrow - B(Medium) Attack
    Right Arrow - C(Heavy) Attack
    Up Arrow - Optional Jump


Features:
Must-Haves:
* Two “characters”(they might just end up being boxes) on the screen that can interact with each other by attacking and blocking
* A round timer that counts down from 60, giving the round to the person with the most territory once it hits 0
* 3 standing normal moves, 3 crouching normal moves, 3 jumping normals moves, and various command normal moves and special moves per selectable character
* Game must run at 60 fps
* Meter system that builds up meter when a normal attack makes contact with an opponent, when a special is used, when being hit, and when instant blocking(if implemented). Meter is spent to use supers and stronger EX versions of specials.


Want-to-Haves:
* Multiple selectable characters with differing attacks, sizes, and movement
* An instant blocking mechanic, where if you time a block 5 frames before an attack connects, it reduces the blockstun of an attack and gains additional attack
* A juggle system, where certain moves will put the opponent in a state where they are in the air but can still be hit
* A pause menu where you can remap controls and access command lists
* Combo counter, that tracks whether the opponent was able to escape a combo or not


Stretch Goals:
* Online play(using GGPO if we get around to it)
* Background art
* Character art
* Music
* Training mode


Class Design
* Main: self explanatory main method
* DrawingSurface: surface on which stuff is drawn and 
* Hitbox: Characters check if these intersect to interact with other characters
* Projectile: Extends Hitbox, creates Hitboxes that can move around the screen independent of a character
* Player: Contains a Character object, calls methods to influence the Character based on which keys are inputted.
* Character: contains all the information that is attributed to a character such as Hitboxes, state, and absolute position, inherits Sprite
* Blue: represents the character “Blue”, contains Blue’s moves, inherits Character
* Sprite: inherits Rectangle2D.Double, basic movement and draw functions 
* Sounds: has methods to produce sound effects for Hadouken, Shoryuken, and Tatsu
* Enums
   * BlockHeight: determines which way a character is blocking/which way a character needs to block a hit
   * ControlState: determines how the character can be controlled and which actions they may take
   * HitboxState: determines whether a hitbox is capable of damaging an opponent or not
* Checkers(CheckDP, CheckDD, CheckQCF, CheckQCB)
* Command interpreters, determine whether you can execute a special move based on your directional inputs


Credit:
Design Ideas and Concepts - jkim222
Character Movement, Attacks and Hit Detection - Shroopy
Projectiles - Shroopy
Enums - Shroopy
HUD (Score, Timer, Meter, Combo Counter) - jkim222
Special Attack Interpreter - Both
Screen Scrolling and Position - jkim222
ReadME - Both
UML and Javadocs - jkim222 
Special Attack Interpreter Model - Rich Rauenzahn
Advice to Use Enumerations - Rich Rauenzahn
General Program Design Advice - Rich Rauenzahn
Image Display and Sound - processing.org
Hadouken Sound Effect - www.sounds-resource.com
Shoryuken and Tatsu Sound Effect - www.youtube.com  
Very Heavy Inspiration - Super Street Fighter 2 Turbo by Capcom
B Tatsu - UNDER NIGHT IN-BIRTH Exe:Late[st] by Arc System Works
APCS Teacher - John Shelby
Source Control - Git (git-scm.com)
Git Source Repository - GitHub (github.com)
Programming language used - Java (www.java.com)
IDE used - Eclipse (www.eclipse.org) 
Computing Architecture - John von Neumann
Computation - Alan Turing
Fighting Games - Street Fighter 2 (we don’t talk about SF1)
(see me in Yie-Ar Kung Fu though you won’t)