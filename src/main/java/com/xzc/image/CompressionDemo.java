package com.xzc.image;

import com.sun.imageio.plugins.common.ImageUtil;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

public class CompressionDemo {

    public static void main(String[] args) {
        OpenCV.loadLocally();
//        OpenCV.loadShared();
        // 不支持中文名
        Mat src = Imgcodecs.imread("C:/Users/Administrator/Desktop/dlam.jpg");
        MatOfInt dstImage = new MatOfInt(Imgcodecs.IMWRITE_JPEG_QUALITY, 1);
        Imgcodecs.imwrite("C:/Users/Administrator/Desktop/dlam12312321.jpg", src, dstImage);

    }

    static void jdkImage() {
        try {
            File input = new File("C:\\Users\\Administrator\\Desktop\\哆啦A梦1.jpg");
            BufferedImage image = ImageIO.read(input);

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            ImageWriter writer = writers.next();

            File compressionImage = new File("C:\\Users\\Administrator\\Desktop\\哆啦A梦11.jpg");
            OutputStream os = new FileOutputStream(compressionImage);
            ImageOutputStream ios = ImageIO.createImageOutputStream(os);
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.5f);
            writer.write(null, new IIOImage(image, null, null), param);

            os.close();
            ios.close();
            writer.dispose();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
