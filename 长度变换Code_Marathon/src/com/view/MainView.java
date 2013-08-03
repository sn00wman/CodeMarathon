package com.view;
/**
 * 	���ߣ� ����
 *  ���䣺 552347859@qq.com
 *  ƽ̨�� eclipse
 *  ����ת�������������
 *  ����ѡ���ļ�����ʼת��  
 */
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.LengthChange.LengthChange;
public class MainView extends JFrame implements ActionListener{
	 JTextField jtf ;
	 JPanel jp1 ;
	 JPanel jp2 ;
	 JButton jb1 , jb2 , jb3;                // ѡ���ļ���ť
	 JLabel jlb ;
	 String filename ;
	 Pattern pattern = Pattern.compile("\\\\") ;
	 public MainView()
	 {		
		 jlb = new JLabel("��ѡ��Ҫ�����ļ�") ;
		 
		 jtf = new JTextField(20);
		 jb1 = new JButton("ѡ���ļ�");
		 jb1.addActionListener(this);
		 jp1 = new JPanel();  
		 jp1.setLayout(new FlowLayout(FlowLayout.LEFT));
		 jp1.add(jtf);
		 jp1.add(jb1);
		  
		 jp2 = new JPanel();  
		 jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		 jb2 = new JButton("��ʼת��") ;
		 jb2.addActionListener(this) ;
		 jb3 = new JButton("ȡ��") ;
		 jb3.addActionListener(this) ;
		 jp2.add(jb2) ;
		 jp2.add(jb3) ;
		 		 
		 this.setLayout(new FlowLayout()) ;
		 this.add(jlb) ;
		 this.add(jp1) ;
		 this.add(jp2) ;
		 // �����
		 setTitle("����ת��");
		 setSize(350,200);
		 setVisible(true);
	 }
 
	 public static void main(String args[]){
		 new MainView();
	 }
 
	 @Override
	 public void actionPerformed(ActionEvent arg0) {
		 if(arg0.getSource() == jb1)
		 {
			 open();
		 }
		 else if(arg0.getSource() == jb2)
		 {
			// ���õ�λת������ LengthChange
			 try{
				 LengthChange lc = new LengthChange(filename) ;
				 JOptionPane.showMessageDialog(this,"����������output.txt") ;
			 }catch ( java.lang.NullPointerException e){
				 JOptionPane.showMessageDialog(this,"��ѡ����ȷ���ļ�") ;
			 }
			 			 
		 }
		 else if(arg0.getSource() == jb3)
		 {
			 this.dispose() ;
		 }
	 }
	 
	 public void open(){
		 
		 JFileChooser chooser = new JFileChooser();
		 chooser.setMultiSelectionEnabled(false);
		 FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT","txt");
		 chooser.setFileFilter(filter);
		 chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		 chooser.setDialogTitle("Seletct TXT File");
		    
		 int result = chooser.showOpenDialog(this);
		 if(result == JFileChooser.APPROVE_OPTION){
			 File file = chooser.getSelectedFile();
			 String filename1 = file.getName();
			 File file1 = chooser.getCurrentDirectory();
			 String filepath = file1.getAbsolutePath();
			 filename = filepath+filename1 ;
			 Matcher matcher = pattern.matcher(filename);
			 filename = matcher.replaceAll("\\\\\\\\") ;
			 
			 jtf.setText("���ļ�:"+filename);//���ļ�·���赽JTextField
		 }
		 
	 }
}