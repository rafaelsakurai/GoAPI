# GoAPI
This is a Go Game API in Java which aims to provide the main logic of Go (Igo, Weiqi, Baduk) Game and any developer can use with their own visual interface (console/desktop/web).

### Example

```java
Player b = new Player("Rafael", "30k", 'B');
Player w = new Player("Sakurai", "30k", 'W');
Board board = new Board(19, 5.5, "Japanese", b, w, new Date());
board.move(Move.create(3, 3, b));
board.move(Move.create(6, 5, w));
```

## This project is still at the beginning

The follow features are implemented:

* Execute a move;
* Verify if the move will remove some stone from board;
* Get the stones that will be removed from board;
* Remove the dead stones from board;
* Get the number of liberties from a stone or a group.
* Verify if the position on board is a free;
* If try to suicide an exception will be throw;
* Get all stones or groups in Atari;
* Get a stone by your position (line, column);
* Undo last move (if there at least one movement done);
* Redo last move (if there at least one undone movement);
* The board is a Move[][] object and can be converted to char[][];
* Print the board on console;
* Find all groups on board, the group have some informartions like number of liberties or opponets;
* Merge two or more groups if a stone is placed in a adjacent position;
* Load kifu using SGF format;
* Export the board to SGF file;
* ...

> The stones are represented by move, and to start the entire board is initialized with free moves.

For tests, the board can be print in console like this 9x9 board:

| | | | | |b|b|w| |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| | | |b| |b|w|w| |
| | | | | |b|w|w|b|
| | |b|b|b|b|b|w| |
| |b|b|w|b|w|w|w| |
|b| |b|w|b|w|b|w| |
|b|b|w|w|w|w|b|b| |
|b|w|w|b|b|w|w|w| |
|b|w| |w| | | | | |

The follow features will be implemented:

* KO Rule;
* Score calculation;
* Group status (live or dead);
* Add a structure of binary tree of sgf nodes;
* Implement GTP or Go Modem Protocol to play with another program or write a go server;
* ...
