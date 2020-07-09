import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.GroupLayout.Alignment;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class browse implements ActionListener {

	JButton jb,jb_en,jb_de;
	JFileChooser jfc;
	JFrame jf;
	FileNameExtensionFilter filter1,filter2,filter3;
	File file;
	BufferedImage image;
	JLabel jlb1;
	String path,file_name;
	JTextArea jta_in,jta_out,jta_res;
	JScrollPane jsp1,jsp2;
	char[] ext=new char[3];
	private JLabel lblOutputHere;
	private JTextArea textArea;
	
	browse()
	{		
		jf=new JFrame("DECRYPT-IT-OUT");	
	
		jta_in=new JTextArea("");
		jta_in.setFont(new Font("Rockwell Condensed", Font.BOLD, 15));
		
		jta_out=new JTextArea("");
		jta_out.setFont(new Font("SansSerif", Font.BOLD, 13));
		jta_in.setColumns(18);
		jta_out.setColumns(18);
		
		jsp1=new JScrollPane(jta_in);
		jsp1.setBounds(194, 460, 250, 100);
		jsp2=new JScrollPane(jta_out);
		jsp2.setBounds(449, 460, 250, 100);
		jsp1.setPreferredSize(new Dimension(250, 100));
		jsp2.setPreferredSize(new Dimension(250, 100));
		
		jb=new JButton("browse");
		jb.setIcon(new ImageIcon("C:\\Users\\HP\\Documents\\GitHub\\Stenography\\src\\icon new.png"));
		jb.setBounds(35, 218, 144, 100);
		jb.setForeground(Color.RED);
		jb_en=new JButton("encrypt");
		jb_en.setIcon(new ImageIcon("C:\\Users\\HP\\Desktop\\lock.png"));
		jb_en.setBounds(193, 571, 251, 89);
		jb_de=new JButton("decrypt");
		jb_de.setIcon(new ImageIcon("C:\\Users\\HP\\Desktop\\unlock.png"));
		jb_de.setBounds(465, 571, 234, 89);
		jb.addActionListener(this);
		jb_en.addActionListener(this);
		jb_de.addActionListener(this);
		
		jlb1=new JLabel("",null,JLabel.CENTER);
		jlb1.setBounds(193, 5, 506, 403);
		
		jlb1.setPreferredSize(new Dimension(450,450));
		jlb1.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		
		 
		filter1 = new FileNameExtensionFilter("PNG", "png" );
		filter2 = new FileNameExtensionFilter("BMP", "bmp" );
		filter3 = new FileNameExtensionFilter("PNG & BMP ","png","bmp");
		
		jfc=new JFileChooser();
		jfc.setFileFilter(filter1);
		jfc.setFileFilter(filter2);
		jfc.setFileFilter(filter3);
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		jf.setSize(900,700);
		jf.setResizable(false);
		jf.getContentPane().setLayout(null);
		jf.getContentPane().add(jb);
		jf.getContentPane().add(jlb1);
		jf.getContentPane().add(jb_en);
		jf.getContentPane().add(jb_de);
		jf.getContentPane().add(jsp1);
		jf.getContentPane().add(jsp2);
		
		JLabel lblBrowseImage = new JLabel("Browse Image");
		lblBrowseImage.setFont(new Font("Rockwell Condensed", Font.BOLD, 28));
		lblBrowseImage.setBounds(17, 318, 162, 40);
		jf.getContentPane().add(lblBrowseImage);
		
		JLabel lblInputTextHere = new JLabel("Input Text Here ");
		lblInputTextHere.setFont(new Font("Rockwell Condensed", Font.BOLD, 18));
		lblInputTextHere.setBounds(203, 422, 144, 27);
		jf.getContentPane().add(lblInputTextHere);
		
		lblOutputHere = new JLabel("Output Text  Here ");
		lblOutputHere.setFont(new Font("Rockwell Condensed", Font.BOLD, 18));
		lblOutputHere.setBounds(479, 419, 134, 30);
		jf.getContentPane().add(lblOutputHere);
		
		jta_res = new JTextArea("");
		jta_res.setFont(new Font("SansSerif", Font.BOLD, 20));
		jta_res.setColumns(18);
		jta_res.setBounds(714, 461, 170, 199);
		jf.getContentPane().add(jta_res);
		
		JLabel lblResult = new JLabel("RESULT");
		lblResult.setFont(new Font("Rockwell Condensed", Font.BOLD, 19));
		lblResult.setBounds(723, 422, 99, 22);
		jf.getContentPane().add(lblResult);
		
		jf.setResizable(false);
		jf.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getActionCommand()=="browse")
		{
			 int returnVal = jfc.showOpenDialog(jf);
			 jta_in.setText("");
			 jta_out.setText("");
			 jta_res.setText("");
		
			 if (returnVal == JFileChooser.APPROVE_OPTION)
			 {
				 File file = jfc.getSelectedFile();
				 path = file.getPath();
				 file_name=file.getName();
				 file_name.getChars(file_name.indexOf('.')+1, file_name.length(), ext, 0);
				 
				 
				 path=path.replace("\\", "/");
				
				 
				 file=new File(path);
				
				 try
				 {
					image=ImageIO.read(file);
				 }
				 catch(IOException io)
				 {}
				 
					
				 if(image.getWidth()>jlb1.getWidth() || image.getHeight()>jlb1.getHeight())
					 jlb1.setIcon(new ImageIcon(image.getScaledInstance(jlb1.getWidth(), jlb1.getHeight(),Image.SCALE_SMOOTH)));
				 
				 else
					 jlb1.setIcon(new ImageIcon(image));
				
				
			 }
		}
		else if(ae.getActionCommand()=="encrypt")
		{	 
				if(path!=null)
			    try 
			    {
					new steno(path,String.copyValueOf(ext),jta_in.getText());
				}
			    catch (IOException e) {}
		}	
		
		else if(ae.getActionCommand()=="decrypt")
		{
			    if(path!=null)
			    try 
				{	 
			    	
					decrypt d=new decrypt(String.copyValueOf(ext));
					jta_out.setText(d.output);
					String check=d.output;
					Document d1 = Jsoup.connect("https://www.dourish.com/goodies/jargon.html").timeout(6000).get();
					String title = d1.title();
					
					Elements links = d1.select("b");
				
					ArrayList<String> heading = new ArrayList<String>();
					for (Element link : links) {
						String linkText = link.text();
						
						heading.add(linkText);
					}
					
					
					if(heading.contains(check))
						jta_res.setText("IMAGE NOT SAFE");
						//System.out.println("found");
					else
						jta_res.setText("IMAGE SAFE");
						//System.out.println("not found");					
					
					
					
					
					
				} 
				catch (IOException e) {}
		}
	}
	
	public static void main(String args[])
	{
		new browse();
	}
}
