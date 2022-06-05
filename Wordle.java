import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Wordle {

    public static ArrayList<String> wordListArr = new ArrayList<String>();
    // private static ArrayList<String> testArr = new ArrayList<String>();
    // private static int rows = 6;
    // private static int cols = 5;
    private static char board[][] = new char[6][5];
    private static char hints[][] = new char[6][5];

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        String guess = "", wordList = "wordList2.txt";
        wordListArr = readFile(wordList, wordListArr);
        String solutionWord = randomWord(wordListArr);
        createBoard(board);
        artTitle();

        System.out.println("Enter your first guess to start: ");
            try {
                for (int i = 0; i < board.length; i++) {
                    guess = kb.next().toLowerCase();
                    while (binarySearch(wordListArr, guess) == false) {
                        System.out.println(guess.toUpperCase() + " is not a valid word. Please try again.");
                        System.out.println();
                        guess = kb.next().toLowerCase();
                        System.out.println(binarySearch(wordListArr, guess));
                    }
                    parseGuess(guess, board, i);
                    checkGuess(guess, solutionWord, hints, i);
                    if (checkWin(hints, i) == true) {
                        displayBoard(board, i);
                        System.out.println("That is correct. You win!");
                        System.out.println();
                        break;
                    } else if (i == 5 && checkWin(hints, 5) == false) {
                        displayBoard(board, i);
                        System.out.println("Sorry, that was your last guess. You lose!");
                        System.out.println("The correct word was: " + solutionWord.toUpperCase());
                        System.out.println();
                        break;
                    } else
                        displayBoard(board, i);
                    System.out.println();
                    System.out.println("Enter another guess: ");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Please make sure you're entering a 5-letter word.");
            }
            kb.close();
    }

    public static ArrayList<String> readFile(String file, ArrayList<String> wordListArr) {
        try {
            BufferedReader inFile = new BufferedReader(new FileReader(file));
            String line = inFile.readLine();

            while ((line != null)) {
                wordListArr.add(line);
                line = inFile.readLine();
            }
            inFile.close();
        } catch (IOException e) {
        }

        return wordListArr;
    }

    public static void displayList(ArrayList<String> wordList) {
        for (int i = 0; i < wordList.size(); i++)
            System.out.println(wordList.get(i));
    }

    public static String randomWord(ArrayList<String> wordList) {
        Random rand = new Random();
        String randomWord = wordList.get(rand.nextInt(wordList.size()));
        return randomWord;
    }

    public static void displayBoard(char board[][], int row) {

        for (int i = 0; i < row + 1; i++) {
            System.out.println("+---+---+---+---+---+");
            System.out.print("|");
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(" " + board[i][j] + " |");
            }
            System.out.println();
            System.out.println("+---+---+---+---+---+");
            System.out.print(" ");
            for (int k = 0; k < hints[0].length; k++) {
                System.out.print(" " + hints[i][k] + "  ");
            }
            System.out.println();
        }
    }

    public static void createBoard(char board[][]) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public static void parseGuess(String guess, char board[][], int row) {
        for (int i = 0; i < guess.length(); i++) {
            board[row][i] = guess.charAt(i);
        }
    }

    public static char[][] checkGuess(String guess, String answer, char[][] hints, int row) {
        for (int i = 0; i < guess.length(); i++) {
            for (int j = 0; j < answer.length(); j++) {
                if (guess.charAt(i) == answer.charAt(i)) {
                    hints[row][i] = 'Y';
                    break;
                } else if (guess.charAt(i) == answer.charAt(j)) {
                    hints[row][i] = '!';
                    break;
                } else
                    hints[row][i] = 'N';
            }
        }
        return hints;
    }

    public static boolean checkWin(char[][] hints, int row) {
        if (hints[row][0] == 'Y' && hints[row][1] == 'Y' && hints[row][2] == 'Y'
                && hints[row][3] == 'Y' && hints[row][4] == 'Y')
            return true;
        else
            return false;
    }

    public static boolean binarySearch(ArrayList<String> wordList, String guess) {
        int firstIndex = 0;
        int lastIndex = wordList.size() - 1;

        while (firstIndex <= lastIndex) {
            int midPoint = (firstIndex + lastIndex) / 2;

            if (wordList.get(midPoint).compareTo(guess) == 0) {
                return true;
            } else if (wordList.get(midPoint).compareTo(guess) < 0) {
                firstIndex = midPoint + 1;
            } else if (wordList.get(midPoint).compareTo(guess) > 0) {
                lastIndex = midPoint - 1;
            }
        }
        return false;
    }

    public static void artTitle(){
        System.out.println("                     _________________________________________                         ");
        System.out.println("   ___              /  ____________________________________   |                        ");
        System.out.println("   \\  \\            /  / ________    ________    _______    |  |        ________                            ");
        System.out.println("    \\  \\    /\\    /  / |   __   |  |   _    |  |   _   \\   |  |       |   _____|              ");
        System.out.println("     \\  \\  /  \\  /  /  |  |  |  |  |  |_|   |  |  | \\   \\  |  |       |  |___                       ");
        System.out.println("      \\  \\/    \\/  /   |  |  |  |  |   _   _|  |  |  |   | |  |       |   ___|               ");
        System.out.println("       \\    /\\    /    |  |__|  |  |  | \\  \\   |  |_/   /  |  |_____  |  |_____                            ");
        System.out.println("        \\__/  \\__/     |________|  |__|  \\__\\  |_______/   |________| |________|                                              "); 
        System.out.println("        Six tries until victory. Six tries until failure. Can you guess the word?");
        System.out.println();
    }
}
