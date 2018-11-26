package sample;

import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileNotFoundException;

public class Neuron {

    public String name;
    public byte[][] weight = new byte[20][20];
    public int countTraining = 0;

    public Neuron(String name) {
        this.name = name;
    }

    public byte thinkOfSimilarity(byte[][] data) {
        if (countTraining == 0) {
            System.out.println("Этот нейрон пока не обучен.");
        }

        float totalFields = 0;
        float similarity = 0;

        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++)
                if (data[i][j] >= 5 || weight[i][j] >= 5) {
                    totalFields++;
                    if ((data[i][j] >= 5 && weight[i][j] >= 5) && Math.abs(data[i][j] - weight[i][j]) <= 50) {
                        similarity++;
                    }
                }

        totalFields = Math.abs(totalFields);

        byte result = (byte) ((similarity / totalFields) * 100);

//        System.out.println("Похожесть, общее количество: " + (int)similarity + "," + (int)totalFields);
//        System.out.println("Cходство c элементом [" + name + "]: " + result + "%");

        return result;
    }

    public boolean learn(byte[][] data, TextArea logs) {
        if (countTraining == 0)
            weight = data;

        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++)
                weight[i][j] = (byte) ((weight[i][j] * countTraining + data[i][j]) / (countTraining + 1));
        countTraining++;

        logs.appendText("Neuron [" + name + "] learned a new image! Total images for him: " + countTraining + ".\n");

        Utils.arrayToFile(weight, new File("D:\\Programs\\javafx2\\Default Memory\\T.txt"));

        //TODO: relocate to separate another function
        File neuronMemoryFile = new File("Memory\\" + name.toUpperCase() + ".txt");
        neuronMemoryFile.delete();

        Utils.arrayToFile(weight, neuronMemoryFile);

        return true;
    }

    public void printWeight() {
        System.out.println("Printing current weight! -->");
        for (int i = 0; i < weight.length; i++) {
            for (int j = 0; j < weight[i].length; j++) {
                System.out.print(weight[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void loadDefaultMemory() throws FileNotFoundException {
        weight = Utils.fileToArray(name, weight);
        countTraining++;
        System.out.println("Default memory loaded.");
    }
}
