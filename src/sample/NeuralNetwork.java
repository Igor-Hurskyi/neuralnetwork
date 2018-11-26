package sample;

import javafx.scene.control.TextArea;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class NeuralNetwork {

    private static ArrayList<Neuron> neurons = new ArrayList<>();

    static void trainNeuron(String currentElement, byte[][] image, TextArea logs) {
        for (Neuron neuron : neurons) {
            if (neuron.name == currentElement) {
                neuron.learn(image, logs);
                break;
            }
        }
    }

    static void recognize(byte[][] imageToRecognize, TextArea logTextArea) {
        byte[] similarities = new byte[neurons.size()];

        for (int i = 0; i < neurons.size(); i++) {
            similarities[i] = neurons.get(i).thinkOfSimilarity(imageToRecognize);
        }

        int index = 0;
        int maxSimilarity = 0;

        for (int i = 0; i < similarities.length; i++) {
            if (similarities[i] > maxSimilarity) {
                maxSimilarity = similarities[i];
                index = i;
            }
        }

        logTextArea.appendText("I guess it's [" + neurons.get(index).name + "]. It valued with " + maxSimilarity + "%.\n");
    }

    static void loadDefaultElements() {
        Neuron e = new Neuron("E");
        Neuron t = new Neuron("T");
        Neuron v = new Neuron("V");

        try {
            e.loadDefaultMemory();
            t.loadDefaultMemory();
            v.loadDefaultMemory();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        neurons.add(e);
        neurons.add(t);
        neurons.add(v);
    }

    public static boolean addElement(Neuron newElement, TextArea logs) {
        for (Neuron n : neurons) {
            if (n.name.equals(newElement.name)) {
                if(logs != null) logs.appendText("Element [" + newElement.name + "] already exists!\n");
                return true;
            }
        }
        if(logs != null) logs.appendText("New element [" + newElement.name + "] have been added to neural network.\n");
        neurons.add(newElement);
        return false;
    }

    public static void removeElement(String currentElement) {
        for (int i = 0; i < neurons.size(); i++)
            if (neurons.get(i).name.equals(currentElement))
                neurons.remove(i);
    }

    public static StringBuilder display() {
        StringBuilder s = new StringBuilder("Neural Network current elements: < ");
        for (Neuron n : neurons) {
            s.append("[" + n.name + "]");
        }
        s.append(" >.");
        return s;
    }
}
