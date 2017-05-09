package nspsofs;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import read.ReadFile;
import read.Util;

public class NS_Particle {  
    public double[] pos;//粒子的位置，求解问题多少维，则此数组为多少维  
    public double[] v;//粒子的速度，维数同位置  
    public double[] fitness;//粒子的适应度  
    public double[] pbest;//粒子的历史最好位置  
    public double[] gbest;//所有粒子找到的最好位置  
    public static Random rnd;  
    public double randnum1;
    public double randnum2;
    
    public static int dims;  
    public static double w;  
    public static double c1;  
    public static double c2;  
    
    public double[] gbest_fitness;
    public double[] pbest_fitness;//历史最优解  

    /** 
     * 初始化粒子 
     * @param dim 表示粒子的维数 
     * @throws IOException 
     */  
    public void initial(int dim) throws IOException {  
        pos = new double[dim]; 
        v = new double[dim];
        pbest = new double[dim];  
        dims = dim;
        fitness = new double[2];					//适应值，这里有2个，第一个是特征数，第二个是错误率
        pbest_fitness = new double[2];
        gbest = new double[dim];					
        gbest_fitness = new double[2];
        
        for(int i=0;i<pos.length;i++){
        	pos[i]=Util.rand(0,1);
        	pbest[i]=pos[i];						 //初始化粒子的个体最优
        	v[i] = Util.rand(-0.6, 0.6);   //初始化粒子的速度
        }
            
        pbest_fitness[0] = ReadFile.getFeatureNum(NS_PSO.name)+1;
        pbest_fitness[1] = 1;
        gbest_fitness[0] = ReadFile.getFeatureNum(NS_PSO.name)+1;
        gbest_fitness[1] = 1;
        
        randnum1 = Util.rand(0,1);
        randnum2 = Util.rand(0,1);
    }  
    /** 
     * 评估函数值,同时记录历史最优位置 
     * @throws IOException 
     */  
    public void evaluate() throws IOException {
    	

    	List<String> choose = new ArrayList<String>();
    	int j=1;
    	for(int i=0;i<pos.length;i++){
    		if(pos[i]>0.6){
    			choose.add(String.valueOf(j));
    		}
    		j++;
    	}

    	
		ReadFile rf = new ReadFile();
		rf.getFile(choose, "dataset\\Alltra--"+NS_PSO.name, "tra");
		rf.getFile(choose, "dataset\\Alltest--"+NS_PSO.name, "test");
		
		String[] trainArgs = {"tra"};						//directory of training file
		String modelFile = svm_train.main(trainArgs);
		String[] testArgs = {"test", modelFile, "result"};		//directory of test file, model file, result file
		Double accuracy = svm_predict.main(testArgs);
		Double errorRate = 1-accuracy;

		fitness[0] = choose.size();
        fitness[1] = errorRate;
        
		if(choose.size()==0){
			fitness[1] = 1;
		}
        //更新个体最优解
        if (fitness[0] < pbest_fitness[0]&&fitness[1] < pbest_fitness[1]) {
        	pbest_fitness[0] = fitness[0];
        	pbest_fitness[1] = fitness[1];
        	System.arraycopy(pos, 0, pbest, 0, pos.length);
        }else if(fitness[0] == pbest_fitness[0]&&fitness[1] < pbest_fitness[1]){
        	pbest_fitness[0] = fitness[0];
        	pbest_fitness[1] = fitness[1];
        	System.arraycopy(pos, 0, pbest, 0, pos.length);
        }else if(fitness[0] < pbest_fitness[0]&&fitness[1] == pbest_fitness[1]){
        	pbest_fitness[0] = fitness[0];
        	pbest_fitness[1] = fitness[1];
        	System.arraycopy(pos, 0, pbest, 0, pos.length);
        }
    }  
    /** 
     * 更新速度和位置 
     * @throws IOException 
     */  
    public void updatev() throws IOException {  
        for (int i = 0; i < pos.length; i++) {
            v[i] = w * v[i] + c1 * randnum1 * (pbest[i] - pos[i])  
                    + c2 * randnum2 * (gbest[i] - pos[i]);  
            if (v[i] > 0.6) {  
                v[i] = Util.rand(-0.6, 0.6);  
            }  
            if (v[i] < -0.6) {  
                v[i] = Util.rand(-0.6, 0.6); 
            }  
            pos[i] = (pos[i] + v[i]);  
            if (pos[i] > 1) {
                pos[i] = Util.rand(0, 1);
            }
            if (pos[i] < 0) {  
                pos[i] = Util.rand(0, 1);
            }
        }
    }  
}  
