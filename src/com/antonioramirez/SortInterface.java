package com.antonioramirez;/*
 * Filename: SortInterface.java
 * Date: 9 Feb 2020
 * Author: Antonio Ramirez
 * Purpose: Interface to be implemented by the InsertionSort class per the rubric
 */

interface SortInterface {
    void recursiveSort(int[] list) throws UnsortedException;
    void iterativeSort(int[] list) throws UnsortedException;
    int getCount();
    long getTime();
}
