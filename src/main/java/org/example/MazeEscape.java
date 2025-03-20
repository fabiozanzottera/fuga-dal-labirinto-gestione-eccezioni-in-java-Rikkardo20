import java.util.Scanner;

//movimenti fuori dai limiti
class OutOfBoundsException extends Exception {
    public OutOfBoundsException(String message) {
        super(message);
    }
}

//collisione con muri
class WallCollisionException extends Exception {
    public WallCollisionException(String message) {
        super(message);
    }
}

public class MazeEscape {
    private static final char[][] LABIRINTO = {
        { 'P', '.', '#', '.', '.' },
        { '#', '.', '#', '.', '#' },
        { '.', '.', '.', '#', '.' },
        { '#', '#', '.', '.', '.' },
        { '#', '.', '#', '#', 'E' }
    };

    // Coordinate iniziali del giocatore
    private static int playerX = 0;
    private static int playerY = 0;
    private static int moves = 0;  // Contatore delle mosse

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean escaped = false;

        System.out.println("Benvenuto in Maze Escape! Trova l'uscita ('E').");

        while (!escaped) {
            printMaze();
            System.out.print("Muoviti (W = su, A = sinistra, S = giù, D = destra): ");
            char move = scanner.next().toUpperCase().charAt(0);

            try {
                movePlayer(move);
                moves++;

                // Controlla se il giocatore ha raggiunto l'uscita
                if (playerX == 4 && playerY == 4) {
                    escaped = true;
                    System.out.println("\nComplimenti! Hai trovato l'uscita in " + moves + " mosse!");
                }
            } catch (OutOfBoundsException | WallCollisionException e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }


    private static void movePlayer(char direction) throws OutOfBoundsException, WallCollisionException {
        int newX = playerX;
        int newY = playerY;

        // Aggiorna le coordinate in base alla direzione
        switch (direction) {
            case 'W': newX--; 
            break;
            case 'A': newY--; 
            break;
            case 'S': newX++; 
            break;
            case 'D': newY++; 
            break;
            default:
                System.out.println("Comando non valido! Usa W, A, S, D.");
                return;
        }

        // Controlla se il movimento è fuori dai limiti
        if (newX < 0 || newX >= 5 || newY < 0 || newY >= 5) {
            throw new OutOfBoundsException("Sei fuori dai limiti del labirinto!");
        }

        // Controlla se il movimento colpisce un muro
        if (LABIRINTO[newX][newY] == '#') {
            throw new WallCollisionException("Hai colpito un muro! Scegli un'altra direzione.");
        }

        // nuova posizione per la matrice
        LABIRINTO[playerX][playerY] = '.';
        playerX = newX;
        playerY = newY;
        LABIRINTO[playerX][playerY] = 'P'; 
    }  
    private static void printMaze() {
        System.out.println("\nLabirinto attuale:");
        for (char[] row : LABIRINTO) {
            for (char cell : row) {
                System.out.print(cell + " "); 
                }
            System.out.println();
        }
    }
}
