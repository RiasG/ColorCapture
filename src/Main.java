import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {


        ChangeImg chImg = new ChangeImg("square.jpeg");

        chImg.changeWhiteColor(new Color(28, 215, 28));
        chImg.changeBlackColor(new Color(6, 56, 245));

        File buffFile = new File("changeSquare.jpeg");
        ImageIO.write(chImg.getBufferedImage(),"png",buffFile);



    }
}


