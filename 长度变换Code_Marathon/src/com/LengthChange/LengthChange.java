package com.LengthChange;
/**
 *  ���ߣ� ����
 *  ���䣺 552347859@qq.com
 *  ƽ̨�� eclipse
 *  ��λת������������ɲ��Թ涨��Ҫ��
 *  ����input.txt(Ҫ��������ļ�) ��table.txt(��λ���������ձ�)
 *  ���output.txt λ�ڵ�ǰ�Ĺ���Ŀ¼ 
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LengthChange {
	
	String filename ;
	BufferedReader br1 = null ;    
	BufferedReader br2 = null ;
	BufferedWriter bw = null ;
	
	// �ҵ�����
	String mymail = "552347859@qq.com" ;         
	// ���ת���ͼ�������ս��  
	ArrayList<Double> resultList = new ArrayList<Double>();
	// ���������λ��mת��������table
	HashMap<String, Double> change_rate_table = new  HashMap<String, Double>() ;
	// ��� ����λ��������ʾ��table
	HashMap<String, String> sing_plu_table = new  HashMap<String, String>() ;
	// ����ֱ�ƥ��Ⱥţ��Ӻż��� 
	Pattern pattern1 = Pattern.compile("\\=") ;
	Pattern pattern2 = Pattern.compile("(\\+|\\-)") ;
	
	public LengthChange(String file)
	{
		
		this.filename = file ;
		try {
			
			// table.txt ���ȵ�λ ���������ձ�
			br2 = new BufferedReader(new FileReader("table.txt")) ;
			getsing_plu_table(br2) ;
			
			br1 = new BufferedReader(new FileReader(this.filename)) ;
			String s ;
			
			bw = new BufferedWriter(new FileWriter("output.txt")) ;
			
			while((s=br1.readLine())!=null)
			{	
				if(s.length()>0)
				{
					Matcher matcher1 = pattern1.matcher(s);
					
					if(matcher1.find())
					{
						// ���ת������ ���� change_rate_table �� 						
						setchange_rate_table(s) ;
						continue ;
					}
					
					Matcher matcher2 = pattern2.matcher(s);
					if(matcher2.find())
					{
						// ���㲻ͬ��λ�ĳ�������
						calcul_length(s) ;
						continue ;	
						
					}else{						
						// ����ת�� 
						String[] stemplist = s.split("\\s+") ;						
						resultList.add(myformat(changeToM(stemplist[0],stemplist[1]))) ;					
					}
				}
			}		
			
			outputResult(bw)  ;
					
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {				
				br1.close() ;
				br2.close() ;
				bw.close() ;
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		
	}
	/**
	 * �����������ȷ��С�������λ
	 * @param d
	 * @return
	 */
	private double myformat(double d) {
		BigDecimal b = new BigDecimal(d);  
		d = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		return d;
	}

	/**
	 * �ѵõ��Ľ�������output.txt ��
	 * @param bw2
	 */
	private void outputResult(BufferedWriter bw2) {
		
		try {
			
			bw.write(mymail) ;
			bw.flush() ;
			bw.newLine() ;
			bw.newLine() ;
			
			Iterator<Double> it = resultList.iterator();
			while(it.hasNext())
			{		
				String t = it.next()+" m" ;
				bw.write(t) ;
				bw.flush() ;
				bw.newLine() ;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}				
	}

	/**
	 * �Գ��Ƚ��м���
	 * @param s
	 */
	private void calcul_length(String s) {
		String[] stemplist = s.split("\\s+") ;	
		int len = stemplist.length ;
		double sum = changeToM(stemplist[0],stemplist[1]) ;
		double temp = 0 ;
		for(int i=2; i<len;)
		{			
			if(stemplist[i].equals("+"))
			{
				temp = changeToM(stemplist[++i],stemplist[++i]) ;
				sum = sum + temp ;
			}else{
				temp = changeToM(stemplist[++i],stemplist[++i]) ;
				sum = sum - temp ;
			}
			i++ ;
		}
		
		resultList.add(myformat(sum)) ;
		//System.out.println(sum) ;
	}

	/**
	 * ���ɲ�ͬ���ȵ�λ�ĵ��������ձ�
	 * @param br
	 */
	private void getsing_plu_table(BufferedReader br) {
		
		String s ;
		try {
			while((s=br.readLine())!=null)
			{
				String[] stemplist = s.split("\\s+") ;
				sing_plu_table.put(stemplist[0], stemplist[1]) ;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ȵ�λת�� 
	 * @param key ���ȵĵ�λ
	 * @param val ���ȵĴ�С
	 * @return ��Ӧ��λ m ��ֵ 
	 */
	private double changeToM(String val, String key) {
		
		double value = (double) Float.parseFloat(val) ;
		
		double ratio = change_rate_table.get(key) ;	
		return value*ratio ;
	}
	
	/**
	 *  ��ȡ�����ȵ�λ����ת���ı���
	 * @param s 
	 */
	private void setchange_rate_table(String s) {
		String[] stemplist = s.split("\\s+") ;
		String temp ;
		change_rate_table.put(stemplist[1], (double) Float.parseFloat(stemplist[3])) ;	
		if(!change_rate_table.containsKey((temp=sing_plu_table.get(stemplist[1]))))
		{
			change_rate_table.put(temp, (double) Float.parseFloat(stemplist[3])) ;
		}
	}

}
