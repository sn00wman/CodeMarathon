package com.LengthChange;
/**
 *  作者： 王磊
 *  邮箱： 552347859@qq.com
 *  平台： eclipse
 *  单位转换的主程序，完成测试规定的要求
 *  输入input.txt(要求输入的文件) ，table.txt(单位单复数对照表)
 *  输出output.txt 位于当前的工作目录 
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
	
	// 我的邮箱
	String mymail = "552347859@qq.com" ;         
	// 存放转换和计算的最终结果  
	ArrayList<Double> resultList = new ArrayList<Double>();
	// 存放其他单位与m转换比例的table
	HashMap<String, Double> change_rate_table = new  HashMap<String, Double>() ;
	// 存放 各单位单复数表示的table
	HashMap<String, String> sing_plu_table = new  HashMap<String, String>() ;
	// 正则分别匹配等号，加号减号 
	Pattern pattern1 = Pattern.compile("\\=") ;
	Pattern pattern2 = Pattern.compile("(\\+|\\-)") ;
	
	public LengthChange(String file)
	{
		
		this.filename = file ;
		try {
			
			// table.txt 长度单位 单复数对照表
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
						// 获得转换比例 存入 change_rate_table 中 						
						setchange_rate_table(s) ;
						continue ;
					}
					
					Matcher matcher2 = pattern2.matcher(s);
					if(matcher2.find())
					{
						// 计算不同单位的长度运算
						calcul_length(s) ;
						continue ;	
						
					}else{						
						// 长度转换 
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
	 * 将输入的数精确到小数点后两位
	 * @param d
	 * @return
	 */
	private double myformat(double d) {
		BigDecimal b = new BigDecimal(d);  
		d = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		return d;
	}

	/**
	 * 把得到的结果输出到output.txt 中
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
	 * 对长度进行计算
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
	 * 生成不同长度单位的单复数对照表
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
	 * 长度单位转换 
	 * @param key 长度的单位
	 * @param val 长度的大小
	 * @return 对应单位 m 的值 
	 */
	private double changeToM(String val, String key) {
		
		double value = (double) Float.parseFloat(val) ;
		
		double ratio = change_rate_table.get(key) ;	
		return value*ratio ;
	}
	
	/**
	 *  获取各长度单位与米转化的比例
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
