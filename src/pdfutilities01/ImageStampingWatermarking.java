package pdfutilities01;

/**
 * This example was written by Bruno Lowagie in answer to the following
 * StackOverflow question:
 * http://stackoverflow.com/questions/29560373/watermark-pdfs-using-text-or-images-in-java
 */
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfIndirectObject;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageStampingWatermarking {

    //NOT_IN_PRODUCTION USE.
    protected static void AddImage2(File fileSource, File fileImage, File fileOutput, int imgw1, int imgh1, float imgt1) throws IOException, DocumentException {
//		File file = new File(DEST);
//		file.getParentFile().mkdirs();
        String filePathSRC = fileSource.getAbsolutePath();
        String filePathIMG = fileImage.getAbsolutePath();
        String filePathDEST = fileOutput.getAbsolutePath();

        int imageWidth = imgw1;
        int imageHeight = imgh1;
        float imgTransparency = imgt1;
        // String path1 = ul01.path2scan();
        // String outputfilename = ul01.outputfilename(path1);

        new ImageStampingWatermarking().manipulatePdf2(filePathSRC, filePathIMG, filePathDEST, imageWidth, imageHeight, imgTransparency);
    }

    //NOT_IN_PRODUCTION USE.
    protected void manipulatePdf2a(String src, String img1, String dest, int w, int h, float transparent1) throws IOException, DocumentException {

        PdfReader reader = new PdfReader(src);
        int n = reader.getNumberOfPages();

        String output1 = null;
        if (dest.contains(".pdf")) {
            output1 = dest;
        } else {
            output1 = dest + ".pdf";
        }

        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(output1));

        Image img = Image.getInstance(img1);

        PdfImage stream = new PdfImage(img, "", null);
        stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
        PdfIndirectObject ref = stamper.getWriter().addToBody(stream);
        img.setDirectReference(ref.getIndirectReference());
        img.setAbsolutePosition(36, 400);
        PdfContentByte over = stamper.getOverContent(1);
        over.addImage(img);
        stamper.close();
        reader.close();

    }

    protected static void obtainSliderPositions2() throws IOException, DocumentException {
        Image img = Image.getInstance(PDFwatermark01.file2.getAbsolutePath());
        try {

            BufferedImage bimg = ImageIO.read(PDFwatermark01.file2);
            PDFwatermark01.imageWidthDetected = bimg.getWidth();
            PDFwatermark01.imageHeightDetected = bimg.getHeight();
        } catch (Exception ex) {

        }

        PdfReader reader = new PdfReader(PDFwatermark01.file1.getAbsolutePath());
        Rectangle pagesize;
        pagesize = reader.getPageSizeWithRotation(1);
//        PDFwatermark01.yAxisPDFPageMIN = pagesize.getBottom()+ PDFwatermark01.imageHeightDetected+2;
//        PDFwatermark01.yAxisPDFPageMAX = pagesize.getTop() - PDFwatermark01.imageHeightDetected-2;
//        PDFwatermark01.xAxisPDFPageMIN = pagesize.getLeft()+PDFwatermark01.imageWidthDetected+2;
//        PDFwatermark01.xAxisPDFPageMAX = pagesize.getRight()-PDFwatermark01.imageWidthDetected-2;
        PDFwatermark01.yAxisPDFPageMIN = pagesize.getBottom();
        PDFwatermark01.yAxisPDFPageMAX = pagesize.getTop();
        PDFwatermark01.xAxisPDFPageMIN = pagesize.getLeft();
        PDFwatermark01.xAxisPDFPageMAX = pagesize.getRight();

    }

    protected static void Watermark2(File fileSource, File fileImage, File fileOutput, int imgw1, int imgh1, float imgt1) throws IOException, DocumentException {

        String filePathSRC = fileSource.getAbsolutePath();
        String filePathIMG = fileImage.getAbsolutePath();
        String filePathDEST = fileOutput.getAbsolutePath();

        int imageWidth = imgw1;
        int imageHeight = imgh1;
        float imgTransparency = imgt1;

        new ImageStampingWatermarking().manipulatePdf2(filePathSRC, filePathIMG, filePathDEST, imageWidth, imageHeight, imgTransparency);
    }

    protected void manipulatePdf2(String src, String img1, String dest, int w, int h, float transparent1) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        int n = reader.getNumberOfPages();

        String output1 = null;
        if (dest.contains(".pdf")) {
            output1 = dest;
        } else {
            output1 = dest + ".pdf";
        }

        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(output1));

        // image watermark
        Image img = Image.getInstance(img1);

        float w1 = w;
        float h1 = h;
        float tr1 = 1 * (100 - transparent1) / 100;
        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(tr1); //gs1.setFillOpacity(0.5f);

        PdfContentByte over, under;
        Rectangle pagesize;

        for (int i = 1; i <= n; i++) {
            pagesize = reader.getPageSizeWithRotation(i);

            over = stamper.getOverContent(i);
            //under = stamper.getUnderContent(i);
            over.saveState();
            over.setGState(gs1);

            over.addImage(img, w, 0, 0, h, PDFwatermark01.xAxisImagePosition, PDFwatermark01.yAxisImagePosition);
            over.restoreState();

        }
        stamper.close();
        reader.close();
    }
}
