package sorting;

import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args){
        int[] arr = {4,5,2,1,6,7,9,3,8};
        int len = arr.length;
        for(int i =0 ; i < len-1 ; i++){
            for(int j = 0; j < len-1-i ; j++){
                if(arr[j] > arr[j+1]){
                    swap(arr ,  j+1 ,  j);
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
    private static void swap(int[] arr , int i , int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}


