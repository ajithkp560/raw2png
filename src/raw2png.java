
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AJITH KP
 * (c) Ajith Kp (c) - http://fb.com/ajithkp560
 * http://www.terminalcoders.blogspot.com - http://www.tctech.in
 * 
 */
public class raw2png {
    public static void main(String[] args) throws IOException {
        if(args.length>0){
            File root = new File(args[0]);
            FilenameFilter imgFilter = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    name = name.toLowerCase();
                    return name.endsWith(".raw");
                }
            };
            int width = 1152;
            int height = 1152;
            Scanner sc = new Scanner(System.in);
            System.out.print("Width of image: ");
            width = sc.nextInt();
            System.out.print("Height of image: ");
            height = sc.nextInt();
            File[] imageFiles = root.listFiles(imgFilter);
            for(int y=0;y<imageFiles.length;y++){
                FileInputStream f;
                String line = "";
                List<Integer> arr = new ArrayList<Integer>();
                try {
                    Path path = Paths.get(imageFiles[y].getPath());
                    byte[] data = Files.readAllBytes(path);
                    BufferedImage buff = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
                    WritableRaster wr = buff.getRaster();
                    int x = 0;
                    for(int i=0;i<height;i++){
                        for(int j=0;j<width;j++){
                            wr.setSample(j, i, 0, data[x]);
                            x++;
                        }
                    }
                    buff.setData(wr);
                    ImageIO.write(buff, "png", new File(imageFiles[y].getPath().replace("raw", "")+"png"));
                    System.out.println(imageFiles[y].getName()+" is converted");
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
        else{
            System.out.println("Usage\njava raw2png <Directory where raw files saved>");
        }
    }
}
