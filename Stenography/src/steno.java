import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
//import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class steno {


	steno(String path,String ext,String input) throws IOException
	{				
	
		
		File file = new File(path);  
        BufferedImage image = ImageIO.read(file);
    
        JLabel label_1 = new JLabel("",null,JLabel.CENTER);
      
        
       
        label_1.setSize(new Dimension(300, 300));
        label_1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    
        // display
        if(image.getWidth()>label_1.getWidth() || image.getHeight()>label_1.getHeight())
        	label_1.setIcon(new ImageIcon(image.getScaledInstance(label_1.getWidth(), label_1.getHeight(),Image.SCALE_SMOOTH)));
        	
		 else
		 {
			 label_1.setIcon(new ImageIcon(image));
			 label_1.setPreferredSize(new Dimension(300,300));
		 }
        
        // rgb = [0-255] black [0,0,0] white[255,255,255]
        // normalization (representation)
        int[] pixels = image.getRGB(0,0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        
        int w=image.getWidth();//300
        int h=image.getHeight();//300
        int x=0,y=0;        
           
  
        input+="$";
        //abc$
        char ch[]=input.toCharArray();
        
        for(int i=0,j=0;j<ch.length;j++)
        {		
        	if(x==w)
        	{
        		y++;
        		x=0;
        	}
        	
        	if(y==h)
        	{
        		break;
        	} // 300*300 ==> 90000
        	
            int asc=(byte)ch[j]; // 97 98 99 $
            /*
             * bitwise and operator
             * a = 10;
             * b = 8;
             * a & b ===>  1010 & 1000 ==> 1000 ==> (int)8
             * 
             * 97 & 224 ==> 350 >> 5 ===>   [0000000001011011111](64bit) (5 bit right)   [00000000010110]
             * SHifting we are modifying character and pixel values of image 
             * 
             * */
          
            // character encoding 
            int b765=(asc & 224 ) >> 5;
            int b432=(asc & 28 ) >> 2;
       	    int b10=(asc & 3);
     
       	    //image encoding 
       	    int extra=(pixels[i] & 0xFF000000);
            int red=(pixels[i] & 0x00FF0000)>> 16;        		    		
            int green=(pixels[i] & 0x0000FF00)>> 8;
       	    int blue=(pixels[i] & 0x000000FF);
       	    i++;
       	    
       	    //  embedding character into pixel of image 
       	    red=(red & 248) | b765;  
       	   	green=(green & 248) | b432;
       	   	blue=(blue & 252) | b10;
       	   	
       	   	// now storing / loading pixels & charcter to the image again 
       	   	int rgb = extra |(red << 16) | (green << 8) | blue;
       	   	image.setRGB(x, y, rgb);
       	   
       	   	x++;
        }	   
        
        file=new File("C:/Users/HP/Desktop/diss/large2."+ext);
        ImageIO.write(image,ext,file);
        
        image = ImageIO.read(file);
       
        pixels = image.getRGB(0,0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        
       
      
        JLabel label_2 = new JLabel("",null,JLabel.CENTER);
        
        label_2.setSize(new Dimension(300, 300));
        label_2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        
        if(image.getWidth()>label_2.getWidth() || image.getHeight()>label_2.getHeight())
        	label_2.setIcon(new ImageIcon(image.getScaledInstance(label_2.getWidth(), label_2.getHeight(),Image.SCALE_SMOOTH)));
		 
		 else
		 {
			 label_2.setIcon(new ImageIcon(image));
			 label_2.setPreferredSize(new Dimension(300,300));
		 }
        
        JLabel label_3 = new JLabel("Origial Image");
        JLabel label_4 = new JLabel("Encrypted Image");
          
        JFrame f = new JFrame();
        JPanel jp1,jp2;
        
        jp1=new JPanel(new GridLayout(1, 2,10,10));
        jp2=new JPanel(new GridLayout(1, 2,10,10));
		
        jp1.add(label_1);
        jp2.add(label_2);
        jp1.add(label_3);
        jp2.add(label_4);
        f.add(jp1);
        f.add(jp2);
        
        f.setSize(800, 700);
        f.setLayout(new FlowLayout());
        f.setResizable(false);
        f.setVisible(true);
     }
}
