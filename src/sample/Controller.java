package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;

import java.io.File;
import java.net.URL;
import java.util.*;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnLearn;

    @FXML
    private Canvas canvasDrawElement;

    @FXML
    private Button btnRecognize;

    @FXML
    private ChoiceBox choiceBoxElements;

    @FXML
    private TextField textFieldForNewElement;

    @FXML
    private Button btnAddNewElement;

    @FXML
    private TextArea logTextArea;

    @FXML
    private Button btnClearLog;

    @FXML
    private Button btnDeleteElement;

    @FXML
    void initialize() {

        onLoad();

        //Нажатие на кнопку обучения нейрона
        btnLearn.setOnAction(event -> {
            String element = choiceBoxElements.getValue().toString();
            byte[][] image = getCanvasByteArray();
            NeuralNetwork.trainNeuron(element, image, logTextArea);
        });

        //Нажатие на кнопку распознавания изображения
        btnRecognize.setOnAction(event -> {
            byte[][] imageToRecognize = getCanvasByteArray();
            NeuralNetwork.recognize(imageToRecognize, logTextArea);
        });

        //Описание действия рисования
        canvasDrawElement.setOnMouseDragged(event -> {
            GraphicsContext gc = canvasDrawElement.getGraphicsContext2D();
            gc.setLineWidth(7);
            gc.lineTo(event.getX(), event.getY());
            gc.stroke();
            gc.beginPath();
        });

        //Кнопка очищения изображения
        btnClear.setOnAction(event -> {
            canvasDrawElement.getGraphicsContext2D().clearRect(0, 0, canvasDrawElement.getWidth(), canvasDrawElement.getHeight());
            logTextArea.appendText("Field cleared!\n");
        });

        //Нажатие на кнопку добавления элемента
        btnAddNewElement.setOnAction(event -> {
            String newElement = textFieldForNewElement.getCharacters().toString().toUpperCase();
            boolean exists = NeuralNetwork.addElement(new Neuron(newElement), logTextArea);
            if(!exists) choiceBoxElements.getItems().add(newElement);
            textFieldForNewElement.clear();
        });

        //Нажатие на кнопку очищения логов
        btnClearLog.setOnAction(event -> logTextArea.clear());

        //Нажатие на кнопку удаления элемента
        btnDeleteElement.setOnAction(event -> {
            Object currentElement = choiceBoxElements.getValue();
            NeuralNetwork.removeElement(currentElement.toString());
            choiceBoxElements.getItems().remove(currentElement);
            logTextArea.appendText("Element [" + currentElement.toString() + "] have been deleted!\n");
            logTextArea.appendText(NeuralNetwork.display().toString() + "\n");
        });
    }

    private void onLoad() {
        Utils.clearMemoryFolder(new File("D:\\Programs\\javafx2\\Memory"));

        NeuralNetwork.loadDefaultElements();

        choiceBoxElements.getItems().add("E");
        choiceBoxElements.getItems().add("T");
        choiceBoxElements.getItems().add("V");
    }

    byte[][] getCanvasByteArray() {
        int w = (int) canvasDrawElement.getWidth();
        int h = (int) canvasDrawElement.getHeight();

        byte[][] myarray = new byte[h][w];

        WritableImage snap = canvasDrawElement.snapshot(null, null);
        for (int i = 0; i < myarray.length; i++)
            for (int j = 0; j < myarray[i].length; j++)
                myarray[i][j] = (byte) (snap.getPixelReader().getArgb(j, i) == -1 ? 0 : 1);

        byte[][] smallerArray = convertToSmallerArray(myarray);

        return smallerArray;
    }

    public byte[][] convertToSmallerArray(byte[][] myarray) {
        if (myarray.length % 10 != 0) return null;
        byte[][] smallerArray = new byte[20][20];

        for (int i = 0; i < myarray.length; i++)
            for (int j = 0; j < myarray[0].length; j++) {
                smallerArray[i / 5][j / 5] += myarray[i][j] * 4;
            }

        return smallerArray;
    }

    private void display2DArray(byte[][] myarray) {
        for (int i = 0; i < myarray.length; i++) {
            for (int j = 0; j < myarray[i].length; j++)
                System.out.print(myarray[i][j] + " ");
            System.out.println();
        }
    }

}

