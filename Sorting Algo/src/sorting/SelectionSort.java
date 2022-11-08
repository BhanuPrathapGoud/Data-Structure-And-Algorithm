package sorting;

import java.util.Arrays;

public class SelectionSort {
    public static void main(String[] args){
        int[] arr = {4,5,2,1,6,7,9,3,8};
        int len = arr.length;
        for(int i =0 ; i < len-1 ; i++){
            int min = i;
            for(int j = i+1 ; j < len ; j++){
                if(arr[j] < arr[min]){
                    min = j;
                }
            }
            swap(arr , min, i);
        }
        System.out.println(Arrays.toString(arr));
    }
    private static void swap(int[] arr , int i , int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}


