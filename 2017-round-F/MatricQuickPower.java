package codejam;

/***
 * 快速幂算法
 */
public class MatricQuickPower {

    public static double[][] multiply(double [][]a, double[][]b){
        double[][] arr = new double[a.length][b[0].length];
        for(int i = 0; i < a.length; i++){
           for(int j = 0; j < b[0].length; j++){
               for(int k = 0; k < a[0].length; k++){
                   arr[i][j] += a[i][k] * b[k][j];
               }  
           }  
        }  
        return arr;  
    }

    public static double[][] multiplyPower(double[][] a, int n){
        double[][] res = new double[a.length][a[0].length];
        for(int i = 0; i < res.length; i++){
            for(int j = 0; j < res[0].length; j++) {
                if (i == j)
                    res[i][j] = 1;
                else
                    res[i][j] = 0;
            }
        }  
        while(n != 0){
            if((n & 1) == 1)
                res = multiply(res, a);
            n >>= 1;
            a = multiply(a, a);
        }  
        return res;  
    }
}  
