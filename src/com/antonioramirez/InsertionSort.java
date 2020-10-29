/*
 * Filename: .java
 * Date: 9 Feb 2020
 * Author: Antonio Ramirez
 * Purpose: Contains both iterative and recursive approaches to the Insertion sort. Methods taken from:
 *          https://www.geeksforgeeks.org/recursive-insertion-sort/
 *          https://www.geeksforgeeks.org/insertion-sort/
 */
package com.antonioramirez;

public class InsertionSort implements SortInterface{
    private int count;
    private long time;

    @Override
    public void recursiveSort(int[] list) throws UnsortedException {
        //Resetting/ initializing count and time variables
        count = 0;
        time = 0;

        //Starting our timer
        long startTime = System.nanoTime();

        //Calling helper method to sort the array recursively
        recursive(list, list.length-1);

        //Ending our timer
        long endTime = System.nanoTime();
        //Total time taken to sort the array
        time = (endTime - startTime);

        //Ensure the array was properly sorted
        if (isNotSorted(list)) {
            throw new UnsortedException();
        }
    }

    //Taken from: https://www.geeksforgeeks.org/recursive-insertion-sort/
    // Recursive function to iterative an array using
    // insertion iterative
    private void recursive(int[] arr, int n)
    {
        // Base case
        if (n <= 1)
            return;

        // Sort first n-1 elements
        recursive( arr, n-1 );

        // Insert last element at its correct position
        // in sorted array.
        int last = arr[n-1];
        int j = n-2;

        /* Move elements of arr[0..i-1], that are
          greater than key, to one position ahead
          of their current position */
        while (j >= 0 && arr[j] > last)
        {
            arr[j+1] = arr[j];
            count++;
            j--;
        }
        arr[j+1] = last;
    }

    @Override
    public void iterativeSort(int[] list) throws UnsortedException {
        //Resetting/ initializing count and time variables
        count = 0;
        time = 0;

        //Starting our timer
        long startTime = System.nanoTime();

        //Calling helper method to sort the array iteratively
        iterative(list);

        //Ending our timer
        long endTime = System.nanoTime();
        //Total time taken to sort the array
        time = (endTime - startTime);

        //Ensure the array was properly sorted
        if (isNotSorted(list)) {
            throw new UnsortedException();
        }
    }

    //Taken from: https://www.geeksforgeeks.org/insertion-sort/
    /*Function to iterative array using insertion iterative*/
    private void iterative(int[] arr)
    {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                count++;
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    //Helper method that ensures the array has been properly sorted, returns a boolean value for the if statement
    private boolean isNotSorted (int[] list) {
        for (int i = 0; i < list.length - 1; i++) {
            if (list[i] <= list[i+1]) {
                return false;
            }
        }
        return true;
    }

    //Per rubric
    @Override
    public int getCount() {
        return count;
    }

    //Per rubric
    @Override
    public long getTime() {
        return time;
    }
}
