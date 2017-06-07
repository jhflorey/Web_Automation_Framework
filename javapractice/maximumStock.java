public class BuyAndSell {
  public static int maxProfit(int[] stockPrices) {
    int totalProfit = 0;
    for(int i=1; i<stockPrices.length; i++){
      int currentProfit = stockPrices[i] - stockPrices[i-1];
      if(currentProfit > 0){
        totalProfit += currentProfit;
      }
    }
    return totalProfit;
  }
  public static void main(String args[]){
    int[] p = {10, 7, 5, 8, 11, 9};
    System.out.println("maximum profit that could be obtained is: "  + maxProfit(p));
  }
}
