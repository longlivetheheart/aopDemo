package recursion;

/**
 * @author joe
 * @date 2023/8/15 16:52
 * @description 递归求阶乘
 */
public class Factorial {
    public static void main(String[] args) {
        System.out.println(factorial(5));
    }

    public static int factorial(int num) {
        if (num == 0 || num == 1) {
            return 1;
        }
        return num * factorial(num - 1);
    }
}

