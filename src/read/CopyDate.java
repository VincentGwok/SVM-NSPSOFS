package read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class CopyDate {

	
	private static BufferedReader bf = null;
	private static PrintWriter pw = null;
	private static PrintWriter pw1 = null;
	
	public static void main(String[] args) throws IOException {
		
		String path = "wine.scale";//��Ϊ��ת��Ϊlibsvm��׼�����ݼ�
		String Newpath = "Alltra";//ѵ�����ݼ�
		String Newpath1 = "Alltest";//�������ݼ�
		
		File file = new File(Newpath);
		File file1 = new File(Newpath1);
		
		file.delete();
		file1.delete();
		file.createNewFile();
		file1.createNewFile();
		
		
		bf=new BufferedReader(new FileReader(path));
		pw=new PrintWriter(new FileOutputStream(Newpath, true));
		pw1=new PrintWriter(new FileOutputStream(Newpath1, true));
		
		String count = bf.readLine();
		int datenum = 0;
		
		while(true){
			if(count!=null){
				datenum++;
				count = bf.readLine();
			}else{
				break;
			}
		}
		
		bf.close();
		bf=new BufferedReader(new FileReader(path));
		
		String date = bf.readLine();
		int testnum = (int) (datenum*0.3);
		int tranum = datenum-testnum;
		int count1 = 0;
		
		//�����ÿ�����ݷŵ��������ݼ���ѵ�����ݼ���
		for(int i = 0;i<datenum;i++){
			if(i%2==0&&count1!=testnum){
				pw1.write(date);
				pw1.write("\n");
				date = bf.readLine();
				count1++;
			}else{
				pw.write(date);
				pw.write("\n");
				date = bf.readLine();
			}
			pw.flush();
			pw1.flush();
		}
		System.out.println("done!");
		pw.close();
		pw1.close();
		bf.close();
		
	}

}
