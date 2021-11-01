import java.util.*;
/**
 * Maverick's General Store
 **/
public class general_store
{
    public static void main(String[] args) {
        System.out.println("Welcome to Maverick's General Store!!!\nPlease Enter Amount of Distinct Groceries!");
        Scanner intScan = new Scanner(System.in);
        Scanner strScan = new Scanner(System.in);
        Scanner fltScan = new Scanner(System.in);
        int numGroceries = intScan.nextInt();

        if(numGroceries <= 0) {
            System.out.println("Invalid. Try again... Resetting Machine.");
            intScan.close();
            strScan.close();
            main(args); //Calls the main method recursively.
            return;
        }

        int [] amtEachGrocery = new int[numGroceries];
        String[] nmEachGrocery = new String[numGroceries];
        float[] priceEachGrocery = new float[numGroceries];
        float[] totPerGrocery = new float[numGroceries]; // amt * price

        //Obtain Inputs
        for(int i = 0; i < numGroceries; i++) {
            System.out.println("*******************");
            System.out.println("*  Shopping Cart  *");
            System.out.println("*******************");
            System.out.printf("Enter item #%d: ", i+1);
            nmEachGrocery[i] = strScan.nextLine();
            System.out.printf("How much of %s would you like to add to your cart?\n", nmEachGrocery[i]);
            int tempAmt = intScan.nextInt();
            while(tempAmt <= 0) {
                System.out.println("***********");
                System.out.println("* Invalid *");
                System.out.println("***********");
                System.out.printf("How much of %s would you like to add to your cart?\n", nmEachGrocery[i]);
                tempAmt = intScan.nextInt();
            }

            amtEachGrocery[i] = tempAmt;

            System.out.printf("How much do/does '%s' cost?\n", nmEachGrocery[i]);
            float tempPrice = fltScan.nextFloat();
            while(tempPrice < 0) {
                System.out.println("***********");
                System.out.println("* Invalid *");
                System.out.println("***********");
                System.out.printf("How much do/does '%s' cost?\n", nmEachGrocery[i]);
                tempPrice = fltScan.nextFloat();
            }

            priceEachGrocery[i] = tempPrice;

            totPerGrocery[i] = tempAmt * tempPrice;
        }

        //Print Everything
        printMyShoppingCart(nmEachGrocery, amtEachGrocery, priceEachGrocery, totPerGrocery);

        boolean isFinishedWithSelfCheckout = false;
        while(!isFinishedWithSelfCheckout) {
            System.out.println("\n**********************");
            System.out.println("* Self Checkout Menu *");
            System.out.println("**********************\n");
            System.out.println("1. Remove Discount Amount\n2. Apply Discount Amount\n3. Remove Discount From Single Item\n4. Apply Discount to Single Item\n5. Print Receipt\n6. Exit");
            System.out.println("Please respond with one of the options listed above:");
            int selection = intScan.nextInt();
            isFinishedWithSelfCheckout = selection == 6;
            if(isFinishedWithSelfCheckout) {
                System.out.println("Here is your ending receipt");
                printMyShoppingCart(nmEachGrocery, amtEachGrocery, priceEachGrocery, totPerGrocery);
                float sum = 0;
                for(float i : totPerGrocery) sum += i;
                System.out.printf("\nTotal: $%.2f\nThank You For Shopping at Maverick's General Store!\n", sum);
                break;
            }
            else if(selection == 1 || selection == 3) {
                System.out.println("By how much would you like to remove the discount?");
                float diff = fltScan.nextFloat();
                if(selection == 3) {
                    System.out.println("What index (1-based for convenience) would you like to change?");
                    int index = intScan.nextInt() - 1;
                    changeOnePrice(amtEachGrocery, priceEachGrocery, totPerGrocery, diff, index, true);
                } else {
                    changeAllPrices(amtEachGrocery, priceEachGrocery, totPerGrocery, diff, true);
                }
            }
            else if(selection == 2 || selection == 4) {
                System.out.println("By how much would you like the discount to be?");
                float diff = fltScan.nextFloat();
                if(selection == 4) {
                    System.out.println("What index (1-based for convenience) would you like to change?");
                    int index = intScan.nextInt() - 1;
                    changeOnePrice(amtEachGrocery, priceEachGrocery, totPerGrocery, diff, index, false);
                } else {
                    changeAllPrices(amtEachGrocery, priceEachGrocery, totPerGrocery, diff, false);
                }
            } else if(selection == 5) {
                printMyShoppingCart(nmEachGrocery, amtEachGrocery, priceEachGrocery, totPerGrocery);

            }
            else {
                System.out.println("***********");
                System.out.println("* Invalid *");
                System.out.println("***********");
            }
        }
    }

    private static void printMyShoppingCart(String[] items, int[] amts, float[] prices, float[] totals) {
        if(items.length == amts.length && amts.length == prices.length && prices.length == totals.length) {
            System.out.println("|    Item    |   Amount  |  Price |   Total   |");
            for(int i = 0; i < items.length; i++) {
                System.out.printf("| %10s | %9d | $%5.2f | $%8.2f |\n", items[i],amts[i],prices[i],totals[i]);
            }
            return;
        }
        System.out.println("Invalid Inputs.");
    }

    private static void changeAllPrices(int[] amts, float[] prices, float[] totals, float diff, boolean isAdding) {
        if(!isAdding)
            diff = -diff;
        for(int i = 0; i < prices.length; i++) {
            prices[i] += diff;
            totals[i] = prices[i] * amts[i];
        }
    }

    private static void changeOnePrice(int[] amts, float[] prices, float[] totals, float diff, int index, boolean isAdding) {
        if(index >= 0 && index < amts.length) {
            if(!isAdding)
                diff = -diff;
            prices[index] += diff;
            totals[index] = prices[index] * amts[index];
        }
    }
}
