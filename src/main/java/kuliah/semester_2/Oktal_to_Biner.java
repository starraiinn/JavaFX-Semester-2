package kuliah.semester_2;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Oktal_to_Biner extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Octal to Binary Converter");

        Label label = new Label("Masukan Bilangan Oktal:");
        TextField inputField = new TextField();
        Button convertButton = new Button("Convert");
        Label resultLabel = new Label();

        convertButton.setOnAction(e -> {
            String octalStr = inputField.getText();
            if (octalStr.matches("[0-7]+")) {
                int decimal = octalToDecimal(octalStr, octalStr.length() - 1, 0);
                String binary = decimalToBinary(decimal);
                resultLabel.setText("Binary: " + binary);
            } else {
                resultLabel.setText("Invalid");
            }
        });

        VBox vbox = new VBox(10, label, inputField, convertButton, resultLabel);
        vbox.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(vbox, 300, 200));
        primaryStage.show();
    }

    // Rekursi untuk konversi oktal ke desimal
    private int octalToDecimal(String octal, int index, int power) {
        if (index < 0) return 0;
        return (octal.charAt(index) - '0') * (int) Math.pow(8, power) + octalToDecimal(octal, index - 1, power + 1);
    }

    // Rekursi untuk konversi desimal ke biner
    private String decimalToBinary(int decimal) {
        if (decimal == 0) return "0";
        return decimal > 1 ? decimalToBinary(decimal / 2) + (decimal % 2) : "1";
    }
}
