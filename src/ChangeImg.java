import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChangeImg {

    private BufferedImage bufferedImage;
    private int height;
    private int width;
    private int[] pixels;
    private int [][] pixMatrix;
    private Color [][] colorMatrix;

    public int getPixel(int x, int y) {
        return pixels[y * width + x];
    }

    public int getRed(int color) {
        return color >> 16;
    }         // получить красную составляющую цвета

    public int getGreen(int color) {
        return (color >> 8) & 0xFF;
    } // получить зеленую составляющую цвета

    public int getBlue(int color) {
        return color & 0xFF;
    }


    public ChangeImg(String filename) throws IOException {
        this.bufferedImage = readFromFile(filename);
        this.height = bufferedImage.getHeight();
        this.width = bufferedImage.getWidth();
        this.pixels = copyFromBufferedImg(bufferedImage);
        this.pixMatrix = copyMatrixFromBufferedImg(bufferedImage);
    }


    private int[] copyFromBufferedImg(BufferedImage bi) {
        int[] pict = new int[height * width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                pict[i * width + j] = bi.getRGB(j, i) & 0xFFFFFF;
        return pict;
    }
    private int[][] copyMatrixFromBufferedImg(BufferedImage bi) {
        int[][] pict = new int[width][height];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
//                pict[j][i] = bi.getRGB(j, i) & 0xFFFFFF;
                pict[j][i] = bi.getRGB(j, i);
        return pict;
    }

    private BufferedImage readFromFile(String filename) throws IOException {
        BufferedImage bi = ImageIO.read(new File(filename));
        return bi;
    }

    public void changeColor (int c1, int c2){
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (pixMatrix[i][j] == c1) pixMatrix[i][j] = c2;
                bufferedImage.setRGB(i, j, pixMatrix[i][j]);
            }
        }
    }

    public void changeColor (Color c1, Color c2){
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (pixMatrix[i][j] == c1.getRGB()) pixMatrix[i][j] = c2.getRGB();
                bufferedImage.setRGB(i, j, pixMatrix[i][j]);
            }
        }
    }

    private BufferedImage writeFromFile(String filename) throws IOException {
        BufferedImage bi = ImageIO.read(new File(filename));
        return bi;
    }

    public void changeWhiteColor (Color c){
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                Color bufColor = new Color(bufferedImage.getRGB(i,j));

                if (checkWhite(bufColor)) pixMatrix[i][j] = c.getRGB();

                bufferedImage.setRGB(i, j, pixMatrix[i][j]);

            }
        }
    }

    public void changeBlackColor (Color c){
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                Color bufColor = new Color(bufferedImage.getRGB(i,j));

                if (checkBlack(bufColor))
                    pixMatrix[i][j] = c.getRGB();

                bufferedImage.setRGB(i, j, pixMatrix[i][j]);

            }
        }
    }

    private boolean checkWhite(Color color){
        if (
                color.getRed() <= 255 && color.getRed() >= 200 &&
                        color.getGreen() <= 255 && color.getGreen() >= 200 &&
                        color.getBlue() <= 255 && color.getBlue() >= 200
        ) return true;

        return false;

    }

    private boolean checkBlack(Color color){
        if (
                color.getRed() <= 30 && color.getRed() >= 0 &&
                        color.getGreen() <= 30 && color.getGreen() >= 0 &&
                        color.getBlue() <= 30 && color.getBlue() >= 0
        ) return true;

        return false;
    }

    private boolean checkPointStart(int i, int j){
        if (
                j > 0 &&  i > 0
                        && i < width && j < height
                        && checkWhite(new Color(bufferedImage.getRGB(i, j)))
                        && checkBlack(new Color(bufferedImage.getRGB(i,j - 1)))
                        && checkBlack(new Color(bufferedImage.getRGB(i - 1, j)))
                        && checkBlack(new Color(bufferedImage.getRGB(i - 1,j - 1)))) {

            return true;
        }
        return false;
    }

    private boolean checkPointEnd(int i, int j){
        if (
                j > 0 &&  i > 0 && i < width && j < height &&
                        checkBlack(new Color(bufferedImage.getRGB(i + 1,j))) &&
                        checkBlack(new Color(bufferedImage.getRGB(i, j + 1))) &&
                        checkBlack(new Color(bufferedImage.getRGB(i + 1,j + 1)))) {

            return true;
        }
        return false;
    }

    public void blackNearWhite(){
        int[][] pixels = new int[width][height];
        for (int j = 0; j < height - 1; j++) {
            for (int i = 0; i < width - 1; i++) {
                int iBuf = i;
                int jBuf = j;
                int x = 0;
                int y = 0;
                class Coord{
                    int x = 0;
                    int y = 0;

                }
                Coord c = new Coord();

                Color color = new Color(bufferedImage.getRGB(iBuf, jBuf));

                if (checkPointStart(iBuf,jBuf)){

                    boolean flag = false;

                    while (
                            jBuf > 0 && jBuf < width - 1
                            && checkBlack(new Color(bufferedImage.getRGB(iBuf - 1, jBuf)))
                            && checkWhite(new Color(bufferedImage.getRGB(iBuf, jBuf)))
                    ) {
                        while (
                                iBuf < width - 1 && iBuf > 0 &&
                                checkWhite(new Color(bufferedImage.getRGB(iBuf, jBuf)))
                        ) {
                            if (checkBlack(new Color(bufferedImage.getRGB(iBuf, jBuf - 1)))) {
                                //pixels[iBuf][jBuf] = new Color(5, 5, 245).getRGB();
                                bufferedImage.setRGB(iBuf, jBuf, new Color(3, 253, 195).getRGB());
                                iBuf++;
                                flag = true;
                                System.out.println(iBuf);
                            } else if (flag == true){
                                //pixels[iBuf][jBuf] = new Color(5, 5, 245).getRGB();
                                bufferedImage.setRGB(iBuf, jBuf, new Color(4, 246, 181).getRGB());

                                iBuf++;
                                System.out.println(iBuf);
                            }
                        }
                        iBuf = i;
                        jBuf++;
                    }
                }
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public int[][] getPixMatrix() {
        return pixMatrix;
    }

    public void setPixMatrix(int[][] pixMatrix) {
        this.pixMatrix = pixMatrix;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}