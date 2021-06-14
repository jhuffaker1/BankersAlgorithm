import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        ArrayList<Process> processes = new ArrayList<Process>();
        int[] resources;
        int[] available;
        ArrayList<ArrayList<Integer>> listOfPaths = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> pathsToRemove = new ArrayList<Integer>();
        ArrayList<String> listOfOutputs = new ArrayList<String>();


        try {
            /*ask the user for the path to the file then assign that file location to the ProcessFileReader that
            can correctly parse the application defined file format.
             */
            System.out.println("What is the path to the file that you want to input?  ");
            File myFile = new File(myScanner.nextLine());
            ProcessFileReader myReader = new ProcessFileReader(myFile);


            //Read in all process data then assign it to a list of processes.
            ArrayList<int[]> listOfProcesses = myReader.readInProcesses();
            for (int i = 0; i < listOfProcesses.size(); i++) {
                int[] processData = listOfProcesses.get(i);
                processes.add(new Process(processData));
            }


            //Read in all of the current allocation data and assign it to their respective process objects.
            ArrayList<int[]> listOfAllocations = myReader.readInAllocations();
            for (int i = 0; i < listOfAllocations.size(); i++) {
                int[] allocationData = listOfAllocations.get(i);
                processes.get(i).setAllocation(allocationData);
            }


            /*Read in all cpu resource data and then calculate how much of each resource is available by
            by subtracting out the sum of all resources currently allotted to each process.
             */
            resources = myReader.readInResources();
            available = resources;
            for (int i = 0; i < processes.size(); i++) {
                for (int j = 0; j < processes.size(); j++) {
                    available[j] = available[j] - processes.get(i).getAllocation()[j];
                }
            }

            // test for which processes can currently run with the available resources.
            for (int i = 0; i < processes.size(); i++) {
                if (processes.get(i).canRun(available)) {
                    ArrayList<Integer> path = new ArrayList<Integer>();
                    listOfPaths.add(path);
                    listOfPaths.get(listOfPaths.size()-1).add(i);
                }
            }

            int numberOfPaths = listOfPaths.size();
            for (int myCounter = 0; myCounter < numberOfPaths; myCounter++) {
                for (int j = 0; j < listOfPaths.size(); j++) {
                    int[] currentAvailability = available;
                    ArrayList<Boolean> canRunChecker = new ArrayList<Boolean>();
                    for (int k = 0; k < listOfPaths.get(j).size(); k++) {
                        for (int l = 0; l < currentAvailability.length; l++) {
                            currentAvailability[l] = currentAvailability[l] + processes.get((listOfPaths.get(j).get(k))).getNeed()[l];
                        }
                    }
                    for (int t = 0; t < processes.size(); t++) {
                        canRunChecker.add(processes.get(t).canRun(currentAvailability));
                    }
                    if (canRunChecker.contains(false)) {
                        for (int index = 0; index < canRunChecker.size(); index++) {
                            ArrayList<Integer> path = new ArrayList<Integer>();
                            if (canRunChecker.get(index) == true && !listOfPaths.get(j).contains(index)) {
                                listOfPaths.add(path);
                                for (int z = 0; z < listOfPaths.get(j).size(); z++) {
                                    listOfPaths.get(listOfPaths.size() - 1).add(listOfPaths.get(j).get(z));
                                }
                                listOfPaths.get(j).add(index);
                                if (listOfPaths.contains(index)) {
                                    listOfPaths.remove(index);
                                }
                            }
                        }
                    } else {
                        String needToPrint = "";
                        for (int k = 0; k < listOfPaths.get(j).size(); k++) {
                            needToPrint = needToPrint + " " + listOfPaths.get(j).get(k);
                        }
                        listOfPaths.remove(j);
                        j -= 1;
                        listOfOutputs.add(needToPrint);
                    }
                }
            }
            for (int i = 0; i < listOfOutputs.size(); i++) {
                boolean needToRemove = false;
                for (int j = i + 1; j < listOfOutputs.size(); j++) {
                    if (listOfOutputs.get(j).contains(listOfOutputs.get(i))) {
                        needToRemove = true;
                    }
                }
                if (needToRemove) {
                    listOfOutputs.remove(i);
                    i--;
                }
            }
            System.out.println("The solutions below show the required steps for a given path. If there are still");
            System.out.println("processes left then they can be executed safely in any order. ");
            for (int i= 0; i < listOfOutputs.size(); i++) {
                System.out.println(listOfOutputs.get(i));
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file was not found. ");
        } catch (NoSuchElementException e2) {
            System.out.println("The file was not properly formatted. ");
        }
    }
}
