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

                if (
                        bufColor.getRed() <= 255 && bufColor.getRed() >= 200 &&
                        bufColor.getGreen() <= 255 && bufColor.getGreen() >= 200 &&
                        bufColor.getBlue() <= 255 && bufColor.getBlue() >= 200
                )
                    pixMatrix[i][j] = c.getRGB();

                bufferedImage.setRGB(i, j, pixMatrix[i][j]);

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