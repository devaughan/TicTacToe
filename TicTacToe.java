import processing.core.PApplet;

public class TicTacToe extends PApplet {

    Module[][] grid;

    int numberOfMoves;
    int difficulty = 2;

    boolean playerMoved;
    boolean gameOver;
    boolean gameStart;
    
    String gameOverText;

    Button easyButton;
    Button medButton;
    Button hardButton;

    boolean overEasyButton;
    boolean overMedButton;
    boolean overHardButton;

    float easyColor;
    float medColor;
    float hardColor;

    float buttonColor = 200;
    float highlightColor = 160;

    public void settings() {
        size(400, 400);
        intializeSettings();
    }

    public void draw() {
        background(200);
        println(overEasyButton + " " + overMedButton + " " + overHardButton);

        if (gameStart) {
            line(width / 3, 0, width / 3, height);
            line(width * 2 / 3, 0, width * 2 / 3, height);
            line(0, height / 3, width, height / 3);
            line(0, height * 2 / 3, width, height * 2 / 3);
        }

        fill(0, 0, 0);
        if (mousePressed) {
            ellipse(mouseX, mouseY, 20, 20);            
        }

        textSize(144);
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                text(grid[row][col].stringValue, width * col / 3 + 25, height * row / 3 + 100);
            }
        }

        textSize(10);
        text(numberOfMoves, 0, 10);

        if (!gameStart) {
            fill(200);
            rect(width / 2 - width / 4, height / 2 - height / 6, width / 2, height / 3);

            fill(0);
            textSize(15);
            text("Choose difficulty", width / 2 - 60, height / 2 - height / 8);
            text("Easy", easyButton.x - 40, easyButton.y + easyButton.height - 1);
            text("Med", medButton.x - 40, medButton.y + medButton.height - 1);
            text("Hard", hardButton.x - 40, hardButton.y + hardButton.height - 1);

            fill(easyColor);
            rect(easyButton.x, easyButton.y, easyButton.width, easyButton.height);
            fill(medColor);
            rect(medButton.x, medButton.y, medButton.width, medButton.height);
            fill(hardColor);
            rect(hardButton.x, hardButton.y, hardButton.width, hardButton.height);

            update(mouseX, mouseY);
        }
        if (!gameOver && numberOfMoves < 9) {
            if (playerMoved) {
                comp();
            }
        }
        else {
            fill(200);
            rect(width / 2 - width / 4, height / 2 - height / 6, width / 2, height / 3);
            fill(0);
            textSize(30);
            text(gameOverText, width / 2 - 20, height / 2 - 15);
        }
    }

    public void intializeSettings() {
        grid = new Module[3][3];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                grid[row][col] = new Module();
            }
        }

        easyButton = new Button(width / 2 + 10, height / 2 - 23);
        medButton = new Button(width / 2 + 10, height / 2 - 6);
        hardButton = new Button(width / 2 + 10, height / 2 + 11);

        easyColor = buttonColor;
        medColor = buttonColor;
        hardColor = buttonColor;

        overEasyButton = false;
        overMedButton = false;
        overHardButton = false;

        numberOfMoves = 0;
        playerMoved = false;
        gameStart = false;
        gameOver = false;
        gameOverText = "Tie";
    }

    public void update(int x, int y) {
        if (easyButton.overButton()) {
            overEasyButton = true;
            overMedButton = false;
            overHardButton = false;
        }
        else if (medButton.overButton()) {
            overEasyButton = false;
            overMedButton = true;
            overHardButton = false;            
        }
        else if (hardButton.overButton()) {
            overEasyButton = false;
            overMedButton = false;
            overHardButton = true;
        }
        else {
            overEasyButton = false;
            overMedButton = false;
            overHardButton = false;
        }
    }

    public void comp() {
        if (difficulty == 0) {
            compEasy();
        }
        else if (difficulty == 1) {
            compMed();
        }
        else {
            compHard();
        }
        playerMoved = false;
    }

    public void compEasy() {
        fillModule(randomMove(), "o");
    }

    public void compMed() {
        fillModule(checkWin(), "o");
    }

    public void compHard() {
        if (numberOfMoves == 1) {
            if (grid[1][1].stringValue.equals("x")) {
                fillModule(randomMove(), "o");
            }
            else {
                fillModule(1, 1, "o");
            }
        }
        else {
            fillModule(checkWin(), "o");
        }
    }

    Coordinates checkWin() {
        Coordinates coords = checkWin("o");
        if (coords != null) {
            return coords;
        }
        coords = checkWin("x");
        if (coords != null) {
            return coords;
        }
        else {
            return randomMove();
        }
    }

    Coordinates checkWin(String letter) {
        Coordinates coords = new Coordinates(0, 0);
        if (grid[coords.row][coords.col].isEmpty) {
            if (grid[0][1].stringValue.equals(letter) && grid[0][2].stringValue.equals(letter)) {
                return coords;
            }
            if (grid[1][0].stringValue.equals(letter) && grid[2][0].stringValue.equals(letter)) {
                return coords;
            }
            if (grid[1][1].stringValue.equals(letter) && grid[2][2].stringValue.equals(letter)) {
                return coords;
            }
        }
        coords = new Coordinates(0, 1);
        if (grid[coords.row][coords.col].isEmpty) {
            if (grid[0][0].stringValue.equals(letter) && grid[0][2].stringValue.equals(letter)) {
                return coords;
            }
            if (grid[1][1].stringValue.equals(letter) && grid[2][1].stringValue.equals(letter)) {
                return coords;
            }   
        }
        coords = new Coordinates(0, 2);
        if (grid[coords.row][coords.col].isEmpty) {
            if (grid[0][0].stringValue.equals(letter) && grid[0][1].stringValue.equals(letter)) {
                return coords;
            }
            if (grid[1][1].stringValue.equals(letter) && grid[2][0].stringValue.equals(letter)) {
                return coords;
            }
            if (grid[1][2].stringValue.equals(letter) && grid[2][2].stringValue.equals(letter)) {
                return coords;
            }   
        }
        coords = new Coordinates(1, 0);
        if (grid[coords.row][coords.col].isEmpty) {
            if (grid[0][0].stringValue.equals(letter) && grid[2][0].stringValue.equals(letter)) {
                return coords;
            }
            if (grid[1][1].stringValue.equals(letter) && grid[1][2].stringValue.equals(letter)) {
                return coords;
            }
        }
        coords = new Coordinates(1, 1);
        if (grid[coords.row][coords.col].isEmpty) {
            if (grid[0][0].stringValue.equals(letter) && grid[2][2].stringValue.equals(letter)) {
                return coords;
            }
            if (grid[0][1].stringValue.equals(letter) && grid[2][1].stringValue.equals(letter)) {
                return coords;
            }
            if (grid[0][2].stringValue.equals(letter) && grid[2][0].stringValue.equals(letter)) {
                return coords;
            }
            if (grid[1][0].stringValue.equals(letter) && grid[1][2].stringValue.equals(letter)) {
                return coords;
            }
        }
        coords = new Coordinates(1, 2);
        if (grid[coords.row][coords.col].isEmpty) {
            if (grid[1][0].stringValue.equals(letter) && grid[1][1].stringValue.equals(letter)) {
                return coords;
            }
            if (grid[0][2].stringValue.equals(letter) && grid[2][2].stringValue.equals(letter)) {
                return coords;
            }   
        }
        coords = new Coordinates(2, 0);
        if (grid[coords.row][coords.col].isEmpty) {
            if (grid[0][0].stringValue.equals(letter) && grid[1][0].stringValue.equals(letter)) {
                return coords;
            }
            if (grid[0][2].stringValue.equals(letter) && grid[1][1].stringValue.equals(letter)) {
                return coords;
            }
            if (grid[2][1].stringValue.equals(letter) && grid[2][2].stringValue.equals(letter)) {
                return coords;
            }    
        }
        coords = new Coordinates(2, 1);
        if (grid[coords.row][coords.col].isEmpty) {
            if (grid[0][1].stringValue.equals(letter) && grid[1][1].stringValue.equals(letter)) {
                return coords;
            }
            if (grid[2][0].stringValue.equals(letter) && grid[2][2].stringValue.equals(letter)) {
                return coords;
            }
        }
        coords = new Coordinates(2, 2);
        if (grid[coords.row][coords.col].isEmpty) {
            if (grid[0][0].stringValue.equals(letter) && grid[1][1].stringValue.equals(letter)) {
                return coords;
            }
            if (grid[0][2].stringValue.equals(letter) && grid[1][2].stringValue.equals(letter)) {
                return coords;
            }
            if (grid[2][0].stringValue.equals(letter) && grid[2][1].stringValue.equals(letter)) {
                return coords;
            }   
        }
        return null;
    }    

    public Coordinates randomDiagonalMove() {
        Coordinates[] openDiagonals = new Coordinates[4];
        double n = Math.random();
        int index = 0;
        if (grid[0][0].isEmpty) {
            openDiagonals[index] = new Coordinates(0, 0);
            index++;
        }
        if (grid[0][2].isEmpty) {
            openDiagonals[index] = new Coordinates(0, 2);
            index++;
        }
        if (grid[2][0].isEmpty) {
            openDiagonals[index] = new Coordinates(2, 0);
            index++;
        }
        if (grid[2][2].isEmpty) {
            openDiagonals[index] = new Coordinates(2, 2);
            index++;
        }
        if (index == 0) {
            return randomMove();
        }
        else {
            return openDiagonals[(int)(n * index)];
        }
    }

    public Coordinates randomMove() {
        Coordinates[] openSpaces = new Coordinates[9];
        double n = Math.random();
        int index = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col].isEmpty) {
                    openSpaces[index] = new Coordinates(row, col);
                    index++;
                }
            }
        }
        return openSpaces[(int)(n * index)];
    }

    public void mousePressed() {
        if (!gameStart) {
            if (overEasyButton) {
                easyColor = highlightColor;
                medColor = buttonColor;
                hardColor = buttonColor;
            }
            else if (overMedButton) {
                easyColor = buttonColor;
                medColor = highlightColor;
                hardColor = buttonColor;
            }
            else if (overHardButton) {
                easyColor = buttonColor;
                medColor = buttonColor;
                hardColor = highlightColor;
            }
            else {
                easyColor = buttonColor;
                medColor = buttonColor;
                hardColor = buttonColor;
            }
        }
    }

    public void mouseReleased() {
        if (!gameStart) {
            if (overEasyButton) {
                difficulty = 0;
                gameStart = true;
            }
            else if (overMedButton) {
                difficulty = 1;
                gameStart = true;
            }
            else if (overHardButton) {
                difficulty = 2;
                gameStart = true;
            }
            else {
                easyColor = buttonColor;
                medColor = buttonColor;
                hardColor = buttonColor;
            }
        }
        else if (!gameOver) {
            if (mouseY < height / 3) {
                if (mouseX < width / 3) {
                    fillModule(0, 0, "x");
                }
                else if (mouseX < width * 2 / 3) {
                    fillModule(0, 1, "x");
                }
                else {
                    fillModule(0, 2, "x");
                }
            }
            else if (mouseY < height * 2 / 3) {
                if (mouseX < width / 3) {
                    fillModule(1, 0, "x");
                }
                else if (mouseX < width * 2 / 3) {
                    fillModule(1, 1, "x");
                }
                else {
                    fillModule(1, 2, "x");
                }
            }
            else {
                if (mouseX < width / 3) {
                    fillModule(2, 0, "x");
                }
                else if (mouseX < width * 2 / 3) {
                    fillModule(2, 1, "x");
                }
                else {
                    fillModule(2, 2, "x");
                }
            }
        }
    }

    public void checkGameOver() {
        // diagonals
        if (grid[0][0].stringValue.equals(grid[1][1].stringValue) && grid[1][1].stringValue.equals(grid[2][2].stringValue)) {
            if (grid[0][0].stringValue.equals("x")) {
                gameOver = true;
                gameOverText = "You won!";
            }
            else if (grid[0][0].stringValue.equals("o")) {
                gameOver = true;
                gameOverText = "comp won";
            }
        }

        if (grid[0][2].stringValue.equals(grid[1][1].stringValue) && grid[1][1].stringValue.equals(grid[2][0].stringValue)) {
            if (grid[0][2].stringValue.equals("x")) {
                gameOver = true;
                gameOverText = "You won!";
            }
            else if (grid[0][2].stringValue.equals("o")) {
                gameOver = true;
                gameOverText = "comp won";
            }
        }
        
        // horizontal
        for (int row = 0; row < 3; row++) {
            if (grid[row][0].stringValue.equals(grid[row][1].stringValue) && grid[row][1].stringValue.equals(grid[row][2].stringValue)) {
                if (grid[row][0].stringValue.equals("x")) {
                    gameOver = true;
                    gameOverText = "You won!";
                }
                else if (grid[row][0].stringValue.equals("o")) {
                    gameOver = true;
                    gameOverText = "comp won";
                }
            }
        }

        // vertical
        for (int col = 0; col < 3; col++) {
            if (grid[0][col].stringValue.equals(grid[1][col].stringValue) && grid[0][col].stringValue.equals(grid[2][col].stringValue)) {
                if (grid[0][col].stringValue.equals("x")) {
                    gameOver = true;
                    gameOverText = "You won!";
                }
                else if (grid[0][col].stringValue.equals("o")) {
                    gameOver = true;
                    gameOverText = "comp won";
                }
            }
        }
    }

    public class Module {
        String stringValue = "";
        boolean isEmpty = true;
    }

    public class Coordinates {
        int row;
        int col;

        public Coordinates(int r, int c) {
            row = r;
            col = c;
        }
    }

    public class Button {
        float x;
        float y;
        float width = 12;
        float height = 12;

        public Button(float a, float b) {
            x = a;
            y = b;
        }

        boolean overButton() {
            if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
                    return true;
            } 
            else {
                return false;
            }
        }
    }

    public void fillModule(Coordinates coords, String c) {
        fillModule(coords.row, coords.col, c);
    }

    public void fillModule(int row, int col, String c) {
        if (grid[row][col].isEmpty) {
            grid[row][col].stringValue = c;
            grid[row][col].isEmpty = false;
            playerMoved = true;
            numberOfMoves++;
            checkGameOver();
        }
    }

    public static void main(String[] args) {
        String[] appletArgs = new String[] {"TicTacToe"};
        TicTacToe tictactoe = new TicTacToe();
        PApplet.runSketch(appletArgs, tictactoe);
    }
}
