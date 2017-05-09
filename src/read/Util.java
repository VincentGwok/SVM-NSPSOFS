package read;

import java.util.Random;

public class Util {
    /** 
     * ����low��uper֮����� (double)
     * @param low ���� 
     * @param uper ���� 
     * @return ����low��uper֮����� 
     */  
    public static double rand(double low, double uper) {
    	Random rnd = new Random();
        return rnd.nextDouble() * (uper - low) + low;  
    }
    
    /**
     * ����min��max֮���n�������
     * @param min
     * @param max
     * @param n
     * @return
     */
    public static int[] randomNum(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {  
               return null;  
           }  
        int[] result = new int[n];  
        int count = 0;  
        while(count < n) {  
            int num = (int) (Math.random() * (max - min)) + min;  
            boolean flag = true;  
            for (int j = 0; j < n; j++) {  
                if(num == result[j]){  
                    flag = false;
                    break;
                }
            }  
            if(flag){ 
            	result[count] = num;
                count++;  
            }  
        }  
        return result;  
    }  
}
