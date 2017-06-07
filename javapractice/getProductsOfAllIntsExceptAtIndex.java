// Write a function getProductsOfAllIntsExceptAtIndex() that takes an array of integers and returns an array of the products.
// For example, given:
//   [1, 7, 3, 4]
// your function would return:
//   [84, 12, 28, 21]
// by calculating:
//   [7 * 3 * 4,  1 * 3 * 4,  1 * 7 * 4,  1 * 7 * 3]

  Solution:

  import java.util.Arrays;

  public class FindIntArrayProduct {

  	public static void main(String[] args) {

  		int[] array = { 1, 7, 3, 4};

  		usingO_n_Algorithm(array);

  	}

  	//This method is using O(n) time
  	private static void usingO_n_Algorithm(int array[]){

  		int[] newArray = new int[array.length];

  		int product = 1;

  		for (int i=0; i < array.length; i++) {
  	    	product = product * array[i];
  	    }

  	    for (int i=0; i < array.length; i++) {
  	    	newArray[i] = product / array[i];
  			System.out.print(newArray[i] + " ");
  	    }
  	}

  }
