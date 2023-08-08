import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PaletteSampler {

   public static void main(String args[]) throws Exception {

      //reading the image
      File file = new File("input_image.png");
      BufferedImage img = ImageIO.read(file);

      ArrayList < String > colors = new ArrayList < String > ();

      //go through each pixel
      for (int y = 0; y < img.getHeight(); y++) {
         for (int x = 0; x < img.getWidth(); x++) {
            //Retrieving contents of a pixel
            int pixel = img.getRGB(x, y);
            //Creating a Color object from pixel value
            Color color = new Color(pixel, true);
            //Retrieving the R G B values
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            String str = red + ":" + green + ":" + blue + "";
            //check if color already exists in the arraylist
            if (!colors.contains(str)) {
               colors.add(str);
            }
         }
      }

      //write results into txt file
      FileWriter writer = new FileWriter("output_RGBvalues.txt");
      for (int i = 0; i < colors.size(); i++) {
         writer.append((i+1) + ". " + colors.get(i) + "\n");
         writer.flush();
      }
      writer.close();
      System.out.println("output_RGBvalues.txt successfully created.");

      //********** create image file with the colors palette **********//

      //image file dimensions
      int width = (int) ((double)Math.ceil((double)Math.sqrt(colors.size())));
      int height = (int) ((double)Math.ceil((double)colors.size() / width));

      /*
      System.out.println("-------------------");  
      System.out.println("\ncolors count: " + colors.size());
      System.out.println("palette width: " +  width);
      System.out.println("palette height: " + height);
      System.out.println("-------------------");  
      */
     

      //create buffered image object
      BufferedImage img2 = null;
      img2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

      //file object
      File f = null;
      int hop = 0;

      //create rgb values pixel by pixel from colors
      for (int y = 0; y < height; y++) {
         for (int x = 0; x < width; x++) {
         
            //if we drew all the colors, use last color to cover remaining space in the square
            if (hop >= colors.size()) { /*break;*/ hop = colors.size()-1; }

            String str = colors.get(hop);
            int index = 0;
            int a = 255; //alpha
            int r = Integer.parseInt(str.substring(0, str.indexOf(":", 0)));
            index = str.indexOf(":", 0);
            int g = Integer.parseInt(str.substring(index + 1, str.indexOf(":", index + 1)));
            index = str.indexOf(":", index + 1);
            int b = Integer.parseInt(str.substring(index + 1));

            /*
            System.out.println(hop);
            System.out.println(r);
            System.out.println(g);
            System.out.println(b);
            System.out.println("-------------------");
            */

            hop++;

            //pixel colors as an int
            int p = (a << 24) | (r << 16) | (g << 8) | b;

            img2.setRGB(x, y, p);
         }
      }

      //write image
      try {
         f = new File("output_pallet.png");
         ImageIO.write(img2, "png", f);

         System.out.println("Image generated successfully");
      } catch (IOException e) {
         System.out.println("Error: " + e);
      }

   }
}
