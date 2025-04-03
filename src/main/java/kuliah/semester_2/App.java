package kuliah.semester_2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
    private static final int SIZE = 20;
    private static final int GRID_SIZE = 20;
    private int[][] grid = new int[GRID_SIZE][GRID_SIZE];
    private Canvas canvas = new Canvas(GRID_SIZE * SIZE, GRID_SIZE * SIZE);
    private boolean startSet = false, endSet = false;
    private int startX, startY, endX, endY;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        canvas.setOnMouseClicked(this::handleMouseClick);
        root.getChildren().add(canvas);
        drawGrid();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pathfinding DFS & BFS");
        primaryStage.show();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                findPathDFS(startX, startY, new boolean[GRID_SIZE][GRID_SIZE]);
                drawGrid();
            } catch (Exception ignored) {}
        }).start();
    }

    private void handleMouseClick(MouseEvent event) {
        int x = (int) event.getX() / SIZE;
        int y = (int) event.getY() / SIZE;

        if (!startSet) {
            startX = x;
            startY = y;
            grid[y][x] = 2;
            startSet = true;
        } else if (!endSet) {
            endX = x;
            endY = y;
            grid[y][x] = 3;
            endSet = true;
        } else {
            grid[y][x] = 1; // Rintangan
        }
        drawGrid();
    }

    private boolean findPathDFS(int x, int y, boolean[][] visited) {
        if (x < 0 || y < 0 || x >= GRID_SIZE || y >= GRID_SIZE || visited[y][x] || grid[y][x] == 1) return false;
        visited[y][x] = true;
        if (x == endX && y == endY) return true;
        if (findPathDFS(x + 1, y, visited) || findPathDFS(x, y + 1, visited) || findPathDFS(x - 1, y, visited) || findPathDFS(x, y - 1, visited)) {
            grid[y][x] = 4;
            return true;
        }
        return false;
    }

    private void drawGrid() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                if (grid[y][x] == 1) gc.setFill(Color.BLACK);
                else if (grid[y][x] == 2) gc.setFill(Color.GREEN);
                else if (grid[y][x] == 3) gc.setFill(Color.RED);
                else if (grid[y][x] == 4) gc.setFill(Color.BLUE);
                else gc.setFill(Color.LIGHTGRAY);
                gc.fillRect(x * SIZE, y * SIZE, SIZE, SIZE);
                gc.setStroke(Color.GRAY);
                gc.strokeRect(x * SIZE, y * SIZE, SIZE, SIZE);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
