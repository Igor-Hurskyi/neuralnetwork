package sample;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static void arrayToFile(byte[][] array, File file) {

        String line;

        try (FileWriter fr = new FileWriter(file, true)) {

            for (int i = 0; i < array.length; i++) {
                line = Arrays.toString(array[i]);
                line = line.substring(1, line.length() - 1);

                fr.write(line + "," + System.getProperty("line.separator"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[][] fileToArray(String elementName, byte[][] memory) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("D:\\Programs\\javafx2\\Default Memory\\" + elementName + ".txt"));

        Pattern pattern = Pattern.compile("\\d{1,3}");
        Matcher matcher;

        int j = 0;

        for (int i = 0; i < memory.length; i++) {
            String line = scanner.nextLine();
            matcher = pattern.matcher(line);

            while (matcher.find()) {
                memory[i][j++] = Byte.parseByte(matcher.group());
            }
            j = 0;
        }

        return memory;
    }

    public static void clearMemoryFolder(File memoryFolder) {
        File[] files = memoryFolder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    clearMemoryFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        System.out.println("Memory folder cleared.");
    }


}
