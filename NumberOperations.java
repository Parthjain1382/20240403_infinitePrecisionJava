import java.util.ArrayList;
import java.util.Arrays;

public class NumberOperations {

    public static void main(String[] args) {
        // Test numbers
        ArrayList<Integer> num1Array = new ArrayList<>(Arrays.asList(2, 4));
        ArrayList<Integer> num2Array = new ArrayList<>(Arrays.asList(2, 4, 0));

        // Print the numbers
        System.out.println("The number1 is " + num1Array);
        System.out.println("The number2 is " + num2Array);

        try {
            // Testing the numbers
            if (testingNumber(num1Array) && testingNumber(num2Array) && num1Array.size() > 0 && num2Array.size() > 0) {
                // Summation, subtraction, multiplication and division
                System.out.println("The sum of the two numbers is ");
                System.out.println(addition(num1Array, num2Array));
                System.out.println("The subtraction of the two numbers is num2-num1 is ");
                System.out.println(subtraction(num1Array, num2Array));
                System.out.println("The multiplied array is");
                System.out.println(multiplying(num1Array, num2Array));
                System.out.println("The quotient is");
                dividing(num1Array, num2Array);
            } else {
                throw new IllegalArgumentException("Testing case failed or length is smaller than 0");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Function for addition of two arrays
    public static ArrayList<Integer> addition(ArrayList<Integer> num1Array, ArrayList<Integer> num2Array) {
        ArrayList<Integer> sumArray = new ArrayList<>();
        int carry = 0;
        int maxLength = Math.max(num1Array.size(), num2Array.size());

        while (num1Array.size() < maxLength) {
            num1Array.add(0, 0);
        }
        while (num2Array.size() < maxLength) {
            num2Array.add(0, 0);
        }

        for (int i = num1Array.size() - 1; i >= 0; i--) {
            int sum = num1Array.get(i) + num2Array.get(i) + carry;
            sumArray.add(0, sum % 10);
            carry = sum >= 10 ? 1 : 0;
        }

        if (carry > 0) {
            sumArray.add(0, carry);
        }
        return sumArray;
    }

    // Function for subtraction of two arrays
    public static ArrayList<Integer> subtraction(ArrayList<Integer> num1Array, ArrayList<Integer> num2Array) {
        ArrayList<Integer> subArray = new ArrayList<>();

        if (num1Array.get(0) > num2Array.get(0)) {
            subArray = minusDigit(num1Array, num2Array);
            subArray.add(0, '-');
        } else {
            subArray = minusDigit(num2Array, num1Array);
        }
        return subArray;
    }

    // Function for multiplication of two arrays
    public static ArrayList<Integer> multiplying(ArrayList<Integer> num1Array, ArrayList<Integer> num2Array) {
        ArrayList<Integer> mulArray = new ArrayList<>();

        for (int i = num2Array.size() - 1; i >= 0; i--) {
            int carry = 0;
            ArrayList<Integer> tempResult = new ArrayList<>(Arrays.asList(new Integer[num2Array.size() - 1 - i]));
            Arrays.fill(tempResult.toArray(), 0);

            for (int j = num1Array.size() - 1; j >= 0; j--) {
                int product = num1Array.get(j) * num2Array.get(i) + carry;
                tempResult.add(0, product % 10);
                carry = product / 10;
            }

            if (carry > 0) {
                tempResult.add(0, carry);
            }

            mulArray = addition(mulArray, tempResult);
        }
        return mulArray;
    }

    // Function for subtracting individual digits
    public static ArrayList<Integer> minusDigit(ArrayList<Integer> maxArray, ArrayList<Integer> minArray) {
        ArrayList<Integer> subArray = new ArrayList<>();
        int borrowCount = 0;

        for (int i = maxArray.size() - 1; i >= 0; i--) {
            int temp;
            if (maxArray.get(i) - minArray.get(i) < 0) {
                if (borrowCount == 0) {
                    maxArray.set(i, maxArray.get(i) + 10);
                    temp = maxArray.get(i) - minArray.get(i);
                    subArray.add(temp);
                    borrowCount++;
                } else {
                    maxArray.set(i, maxArray.get(i) + 9);
                    subArray.add(maxArray.get(i) - minArray.get(i));
                }
            } else {
                if (borrowCount > 0) {
                    temp = maxArray.get(i) - minArray.get(i) - 1;
                    borrowCount--;
                } else {
                    temp = maxArray.get(i) - minArray.get(i);
                }
                subArray.add(temp);
            }
        }
        return subArray;
    }

    // Function for checking if the elements of the array are integers
    public static boolean testingNumber(ArrayList<Integer> numArray) {
        for (int num : numArray) {
            if (num < 0 || num > 9) {
                throw new IllegalArgumentException("The number " + num + " is not an integer between 0 and 9");
            }
        }
        return true;
    }

    // Function for dividing two arrays
    public static void dividing(ArrayList<Integer> divisor, ArrayList<Integer> dividend) {
        if (divisor.size() == 0 || dividend.size() == 0) {
            throw new IllegalArgumentException("Divisor or dividend cannot be empty.");
        }

        if (isEqual(divisor, new ArrayList<>(Arrays.asList(0)))) {
            throw new IllegalArgumentException("Division by zero error.");
        }

        ArrayList<Integer> quotientArray = new ArrayList<>();
        ArrayList<Integer> remainderArray = new ArrayList<>();

        for (int i = 0; i < dividend.size(); i++) {
            remainderArray.add(dividend.get(i));
            int quotientDigit = 0;

            while (isGreaterOrEqual(remainderArray, divisor)) {
                remainderArray = subtract(remainderArray, divisor);
                quotientDigit++;
            }

            quotientArray.add(quotientDigit);
        }

        quotientArray = removeLeadingZeros(quotientArray);

        System.out.println(quotientArray);
    }

    public static boolean isGreaterOrEqual(ArrayList<Integer> a, ArrayList<Integer> b) {
        if (a.size() > b.size()) return true;
        if (a.size() < b.size()) return false;

        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) > b.get(i)) return true;
            if (a.get(i) < b.get(i)) return false;
        }
        return true;
    }

    public static ArrayList<Integer> subtract(ArrayList<Integer> a, ArrayList<Integer> b) {
        ArrayList<Integer> result = new ArrayList<>();
        int borrow = 0;

        for (int i = a.size() - 1; i >= 0; i--) {
            int diff = a.get(i) - (b.size() > i ? b.get(i) : 0) - borrow;

            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            result.add(0, diff);
        }
        return result;
    }

    public static ArrayList<Integer> removeLeadingZeros(ArrayList<Integer> array) {
        int i = 0;
        while (i < array.size() - 1 && array.get(i) == 0) {
            i++;
        }
        return new ArrayList<>(array.subList(i, array.size()));
    }

    public static boolean isEqual(ArrayList<Integer> array1, ArrayList<Integer> array2) {
        if (array1.size() != array2.size()) return false;

        for (int i = 0; i < array1.size(); i++) {
            if (!array1.get(i).equals(array2.get(i))) return false;
        }
        return true;
    }
}