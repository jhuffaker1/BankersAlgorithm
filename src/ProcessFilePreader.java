import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class ProcessFileReader {
    File myFile;
    Scanner fileScanner;


    public ProcessFileReader(File myFile) throws FileNotFoundException {
        this.myFile = myFile;
        fileScanner = new Scanner(myFile);
    }


    public ArrayList<int[]> readInProcesses() {
        String currentLine = "";
        ArrayList<int[]> processArray = new ArrayList<int[]>();
        while (fileScanner.hasNext() && !currentLine.contains("allocations")) {
            currentLine = fileScanner.nextLine();
            if (!currentLine.contains("allocations")) {
                String[] splitInput = currentLine.split(" ");
                int[] intArray = new int[splitInput.length];
                for (int i = 0; i < splitInput.length; i++) {
                    intArray[i] = Integer.parseInt(splitInput[i]);
                }
                processArray.add(intArray);
            }
        }
        return processArray;
    }


    public ArrayList<int[]> readInAllocations() {
        int counter = 0;
        String currentLine = "";
        ArrayList<int[]> allocationsArray = new ArrayList<int[]>();
        while (fileScanner.hasNext() && !currentLine.contains("resources")) {
            currentLine = fileScanner.nextLine();
            if (!currentLine.contains("resources")) {
                String[] splitInput = currentLine.split(" ");
                int[] intArray = new int[splitInput.length];
                for (int i = 0; i < splitInput.length; i++) {
                    intArray[i] = Integer.parseInt(splitInput[i]);
                }
                allocationsArray.add(intArray);
                counter += 1;
            }
        }
        return allocationsArray;
    }


    public int[] readInResources() {
        String currentLine = "";
        currentLine = fileScanner.nextLine();
        String[] splitInput = currentLine.split(" ");
        int[] resources = new int[splitInput.length];
        for (int i = 0; i < splitInput.length; i++) {
            resources[i] = Integer.parseInt(splitInput[i]);
        }
        return resources;
    }
}
