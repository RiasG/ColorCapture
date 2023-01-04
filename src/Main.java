import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {


        ChangeImg chImg = new ChangeImg("square.jpeg");

        chImg.changeWhiteColor(new Color(145, 127, 51));
        chImg.changeBlackColor(new Color(18, 187, 121));

        File buffFile = new File("changeSquare.jpeg");
        ImageIO.write(chImg.getBufferedImage(),"png",buffFile);



    }
}


