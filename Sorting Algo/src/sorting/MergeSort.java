package sorting;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args){
        int[] arr = {4,5,2,1,6,7,9,3,8};
        int len = arr.length;
        mergeSort(arr,0,len-1);
        System.out.println(Arrays.toString(arr));
    }
    private static void mergeSort(int[] arr,int left,int right){
        if(left < right){

            int mid = left + ((right-left)/2);
            mergeSort(arr,left,mid);
            mergeSort(arr,mid+1,right);
            binarySort(arr,left,mid , right);
        }
    }
    static void merge(int arr[], int p, int q, int r) {

        // Create L ← A[p..q] and M ← A[q+1..r]
        int n1 = q - p + 1;
        int n2 = r - q;

        int L[] = new int[n1];
        int M[] = new int[n2];

        for (int i = 0; i < n1; i++)
            L[i] = arr[p + i];
        for (int j = 0; j < n2; j++)
            M[j] = arr[q + 1 + j];

        // Maintain current index of sub-arrays and main array
        int i, j, k;
        i = 0;
        j = 0;
        k = p;

        // Until we reach either end of either L or M, pick larger among
        // elements L and M and place them in the correct position at A[p..r]
        while (i < n1 && j < n2) {
            if (L[i] <= M[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = M[j];
                j++;
            }
            k++;
        }

        // When we run out of elements in either L or M,
        // pick up the remaining elements and put in A[p..r]
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = M[j];
            j++;
            k++;
        }
    }
    private static void binarySort(int[] arr,int left,int mid , int right){

        int l1 = mid-left+1;
        int l2 = right-mid;
        int leftArr[] = new int[l1];
        int rightArr[] = new int[l2];
        for(int i = 0; i < l1 ; i++){
            leftArr[i] = arr[left+i];
        }
        for(int i = 0; i < l2 ; i++){
            rightArr[i] = arr[mid+1+i];
        }
        int i ,j,k;
        i=0;
        j=0;
        k=left;
        while(i < l1 && j < l2){
            if(leftArr[i] <= rightArr[j]){
                arr[k] = leftArr[i];
                i++;
            }else{
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }
        while(i < l1 ){
            arr[k] = leftArr[i];
            i++;
            k++;
        }
        while(j < l2 ){
            arr[k] = rightArr[j];
            j++;
            k++;
        }

    }
}
