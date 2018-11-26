package sample;

import java.io.File;
import java.io.PrintWriter;

public class Testing {

    public static void main(String[] args) {
        arrayTransformationTest();
        defaultElementsLoadTest();
        clearMemoryTest();
        addElementTest();
        deleteElementTest();
    }

    static void arrayTransformationTest() {
        Controller controller = new Controller();
        byte[][] array = controller.convertToSmallerArray(new byte[50][50]);
        if (array.length == 20 && array[0].length == 20) System.out.println("*\tarrayTransformationTest succeeded.");
        else System.out.println("*\tarrayTransformationTest failed.");
    }

    static void defaultElementsLoadTest() {
        NeuralNetwork.loadDefaultElements();
        if (NeuralNetwork.display().toString().equals("Neural Network current elements: < [E][T][V] >."))
            System.out.println("*\tdefaultElementsLoadTest succeeded.");
        else System.out.println("*\tdefaultElementsLoadTest failed.");
    }

    static void clearMemoryTest() {

        String memoryPath = "D:\\Programs\\javafx2\\Memory";
        File memoryFolder = new File(memoryPath);

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(memoryPath + "\\file.txt", "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer.println("Text line");
        writer.close();

        Utils.clearMemoryFolder(memoryFolder);

        int filesCount = 0;

        File[] files = memoryFolder.listFiles();

        if (files != null)
            for (File f : files) {
                filesCount++;
            }
        if (filesCount == 0) System.out.println("*\tclearMemoryTest succeeded.");
        else System.out.println("*\tclearMemoryTest failed.");
    }

    static void addElementTest() {
        NeuralNetwork.addElement(new Neuron("S"), null);
        if (NeuralNetwork.display().toString().equals("Neural Network current elements: < [E][T][V][S] >."))
            System.out.println("*\taddElementTest succeeded.");
        else System.out.println("*\taddElementTest failed.");
    }

    static void deleteElementTest() {
        NeuralNetwork.removeElement("S");
        if (NeuralNetwork.display().toString().equals("Neural Network current elements: < [E][T][V] >."))
            System.out.println("*\tdeleteElementTest succeeded.");
        else System.out.println("*\tdeleteElementTest failed.");
    }
}
