import algorithms.select.DeterministicSelect;

public class Main {
    public static void main(String[] args) {
        int[] arr = {7, 10, 4, 3, 20, 15};

        System.out.println("0th smallest: " + DeterministicSelect.select(arr.clone(), 0));
        System.out.println("2nd smallest: " + DeterministicSelect.select(arr.clone(), 2));
        System.out.println("last smallest: " + DeterministicSelect.select(arr.clone(), arr.length - 1));

        int[] single = {5};
        System.out.println("Single element: " + DeterministicSelect.select(single, 0));
    }
}