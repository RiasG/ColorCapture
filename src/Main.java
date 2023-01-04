import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {


        ChangeImg chImg = new ChangeImg("photo.jpeg");

        chImg.changeWhiteColor(new Color(44, 238, 1));

        File buffFile = new File("qqq.jpeg");
        ImageIO.write(chImg.getBufferedImage(),"png",buffFile);



    }
}


