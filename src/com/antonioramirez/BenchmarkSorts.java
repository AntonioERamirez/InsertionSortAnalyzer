/*
 * Filename: BenchmarkSort.java
 * Date: 9 Feb 2020
 * Author: Antonio Ramirez
 * Purpose: Contains the main method and tracks benchmark/ warm-up data needed for output to console
 *          Modified from: https://github.com/CrutchTheClutch/CMSC-451-Projects/blob/master/Project%201/src/BenchmarkSort.java
 */
package com.antonioramirez;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

class BenchmarkSorts {
    //Creating an instance of the InsertionSort class to run the two sorts
    private InsertionSort insertionSort = new InsertionSort();
    //To randomly generate data for the arrays.
    private Random random = new Random();

    //To store the data sizes
    private int[] dataSizes;
    //Per rubric
    private final int NUMDATASETS = 50;

    //Stores the iterative counts for each of the 50 runs
    private double[] iCountData = new double[NUMDATASETS];
    //Stores the iterative times for each of the 50 runs
    private double[] iTimeData = new double[NUMDATASETS];
    //Stores the recursive counts for each of the 50 runs
    private double[] rCountData = new double[NUMDATASETS];
    //Stores the recursive times for each of the 50 runs
    private double[] rTimeData = new double[NUMDATASETS];

    //Each of the following are initialized in the constructor
    //Stores the averages of the iterative run counts
    private double[] avgICount;
    //Stores the coefficient of variation of the iterative run counts
    private double[] CVICount;
    //Stores the averages of the iterative run times
    private double[] avgITime;
    //Stores the coefficient of variation of the iterative run times
    private double[] CVITime;

    //Same as above, but recursive
    private double[] avgRCount;
    private double[] CVRCount;
    private double[] avgRTime;
    private double[] CVRTime;

    //Constructor
    private BenchmarkSorts(int[] sizes) {
        dataSizes = sizes;
        avgICount = new double[sizes.length];
        avgRCount = new double[sizes.length];
        CVICount = new double[sizes.length];
        CVRCount = new double[sizes.length];
        avgITime = new double[sizes.length];
        avgRTime = new double[sizes.length];
        CVITime = new double[sizes.length];
        CVRTime = new double[sizes.length];
    }

    //Generates the random data and runs the respective sorts, stores the data
    private void runSorts() throws UnsortedException, IOException {
        //Writers for the 2 separate files
        BufferedWriter iWriter = new BufferedWriter(new FileWriter("iterativeOutput.txt"));
        BufferedWriter rWriter = new BufferedWriter(new FileWriter("recursiveOutput.txt"));

        //For loop to iterate through the varying data sizes
        //In our case, will equal 10
        for (int i = 0; i < dataSizes.length; i++) {
            //Initializing the necessary arrays for the current data size
            int[] iterativeData = new int[dataSizes[i]];
            //Write data size to iterative output
            iWriter.write("Data Size: " + dataSizes[i] + " ");
            int[] recursiveData = new int[dataSizes[i]];
            //Write data size to recursive output
            rWriter.write("Data Size: " + dataSizes[i] + " ");
            //Populating data into the arrays
            for (int setNum = 0; setNum < NUMDATASETS; setNum++) {
                for (int j = 0; j < dataSizes[i]; j++) {
                    int rand = random.nextInt(dataSizes[i] + 1);
                    iterativeData[j] = rand;
                    recursiveData[j] = rand;
                }

                //Sort data iteratively
                insertionSort.iterativeSort(iterativeData);
                //Store count data for current run, out of 50
                iCountData[setNum] = insertionSort.getCount();
                //Store time data for current run, out of 50
                iTimeData[setNum] = insertionSort.getTime();

                //Write the iterative data pair per rubric
                iWriter.write("(Count: " + iCountData[setNum] + ", Time: " + iTimeData[setNum] + "), ");

                //Sort data recursively
                insertionSort.recursiveSort(recursiveData);
                //Store count and time data, out of 50
                rCountData[setNum] = insertionSort.getCount();
                rTimeData[setNum] = insertionSort.getTime();

                //Write the recursive data pair per rubric
                rWriter.write("(Count: " + rCountData[setNum] + ", Time: " + rTimeData[setNum] + "), ");
            }

            //Get iterative average of time and count, and CV of time and count
            //1 for each of the 10 distinct sizes, taken from the 50
            avgICount[i] = getAvg(iCountData);
            CVICount[i] = getCV(iCountData);
            avgITime[i] = getAvg(iTimeData);
            CVITime[i] = getCV(iTimeData);

            //Get recursive average of time and count, and CV of time and count
            avgRCount[i] = getAvg(rCountData);
            CVRCount[i] = getCV(rCountData);
            avgRTime[i] = getAvg(rTimeData);
            CVRTime[i] = getCV(rTimeData);

            //Start new line for new data set size
            iWriter.write("\n\n");
            rWriter.write("\n\n");
        }

        //Close writers
        iWriter.close();
        rWriter.close();
    }

    //Adds all results and divides by size to get an average
    private double getAvg(double[] data) {
        double sum = 0;
        for (double aData : data) {
            sum += aData;
        }
        return sum / data.length;
    }

    //Calculates the Standard Deviation of the data
    //More info: https://www.mathsisfun.com/data/standard-deviation-formulas.html
    private double getStandardDeviation(double[] data) {
        double sum = 0;
        for (double item : data) {
            sum += (item - getAvg(data)) * (item - getAvg(data));
        }
        return Math.sqrt(sum / (data.length - 1));
    }

    //Calculates the coefficient of variation (CV)
    //More info: https://en.wikipedia.org/wiki/Coefficient_of_variation
    private double getCV(double[] data) {
        return ((getStandardDeviation(data)) / getAvg(data)) * 100;
    }

    //Prints results to the console
    private void outputData(){
        //Standard header per rubric
        System.out.format("%213s", "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
        System.out.format("%13s %100s %100s", "\t  |", "\t  Iterative", "\t  Recursive");
        System.out.println();
        System.out.format("%13s %200s", "\t  |", "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
        System.out.format("%13s %25s %25s %25s %25s %25s %25s %25s %25s", "\tSize  |", "Avg Count", "Coef Count", "Avg Time", "Coef Time", "Avg Count", "Coef Count", "Avg Time", "Coef Time");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        //Iterates through the data and outputs
        for (int i = 0; i < dataSizes.length; i++) {
            System.out.format("%14s %25s %25s %25s %25s %25s %25s %25s %26s", dataSizes[i], avgICount[i], CVICount[i], avgITime[i], CVITime[i], avgRCount[i], CVRCount[i], avgRTime[i], CVRTime[i]);
            System.out.println();
        }
    }

    public static void main(String[] args) throws UnsortedException, IOException {
        //Varying data sizes, per rubric
	    int[] sizes = new int[] {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};

	    //For-loop to warm-up JVM
        //More info: https://www.baeldung.com/java-jvm-warmup
        //           https://stackoverflow.com/questions/36198278/why-does-the-jvm-require-warmup
        for (int i = 0; i <= 250; i++) {
            //Start timer
            long startTime = System.nanoTime();
            //Instance created to initialize arrays
            BenchmarkSorts jvmWarmup = new BenchmarkSorts(sizes);
            //Running iterative and recursive sorts
            jvmWarmup.runSorts();
            //End timer
            long endTime = System.nanoTime();
            //Calculate total time
            long warmupTime = (endTime - startTime);
            //Output results
            System.out.println("JVM Warmup: " + i + "    \tTime: " + warmupTime);
        }

        //New instance, new arrays initialized for benchmark
        BenchmarkSorts benchmark = new BenchmarkSorts(sizes);
        //Running sorts
        benchmark.runSorts();
        //Printing to console
        benchmark.outputData();
    }
}
