package nspsofs;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class NS_test {

	public static void main(String[] args) throws IOException {
		NS_PSO pso = new NS_PSO();
		List<NS_Particle> list = new ArrayList<NS_Particle>();
		
		for(int i=0;i<30;i++){
			pso.init(30,"zoo");
			pso.run(500,i);
			pso.showresult(list);
			System.out.println("NSPSOFS:"+NS_PSO.name+"第"+(i+1)+"次测试完成");
		}
		NS_SortBest sb = new NS_SortBest();
		sb.getNSResult(NS_PSO.name,list,"All");
	}
}
