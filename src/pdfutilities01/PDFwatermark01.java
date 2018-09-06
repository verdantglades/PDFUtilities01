/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfutilities01;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 *
 * @author satadru
 */
class MyCustomFilter01 extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        return file.isDirectory() || file.getAbsolutePath().endsWith(".pdf");
    }

    @Override
    public String getDescription() {
        return "PDF documents (*.pdf)";
    }
}

class MyCustomFilter02 extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        return file.isDirectory() || file.getAbsolutePath().endsWith(".png") || file.getAbsolutePath().endsWith(".bmp") || file.getAbsolutePath().endsWith(".gif") || file.getAbsolutePath().endsWith(".tiff") || file.getAbsolutePath().endsWith(".jpg");
    }

    @Override
    public String getDescription() {
        return "Image files (*.bmp, *.gif, *.jpg, *.png, *.tiff)";
    }
}

class MyCustomFilter03 extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        return file.isDirectory() || file.getAbsolutePath().endsWith(".pdf");
    }

    @Override
    public String getDescription() {
        return "PDF documents (PDF) (*.pdf)";
    }
}

public class PDFwatermark01 extends javax.swing.JFrame {

    /**
     * Creates new form PDFwatermark01
     */
    protected static File file1; //PDF input file.
    protected static File file2; //Image input file.
    protected static File file3; //PDF output file.
    protected static File tempPDFFile; //Temporary PDF output file.

    protected static int returnVal;

    protected static float imageWidthDetected = 0;
    protected static float imageHeightDetected = 0;

    protected static int imageWidthSliderValue = 25;
    protected static int imageHeigthSliderValue = 25;
    protected static float imageTransparency = 0;

    protected static int countFileExtension1 = 0;
    protected static int countFileExtension2 = 0;
    protected static int countFileExtension3 = 0;

    protected static float yAxisPDFPageMAX = 0;
    protected static float yAxisPDFPageMIN = 0;
    protected static float xAxisPDFPageMAX = 0;
    protected static float xAxisPDFPageMIN = 0;
    protected static float yAxisImagePosition = 0;
    protected static float xAxisImagePosition = 0;

    protected static Integer pageNumber = 0;

    protected static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }

    protected static void livePreviewPagePDF01() {

        BufferedImage bim = null;

        try {
            PDDocument document = PDDocument.load(new File(PDFwatermark01.tempPDFFile.getAbsolutePath())); //Append .PDF. However, temporary file is to be read.
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            //PDFwatermark01.pageNumber = 0;
            bim = pdfRenderer.renderImageWithDPI(PDFwatermark01.pageNumber, 300, ImageType.RGB);

            //Check page orientation
            PDPage currentPDFPage = document.getPage(PDFwatermark01.pageNumber);
            Float currentPDFPageH = currentPDFPage.getMediaBox().getHeight();
            Float currentPDFPageW = currentPDFPage.getMediaBox().getWidth();

            if (currentPDFPageH >= currentPDFPageW) {
                BufferedImage resizedImage = PDFwatermark01.resize(bim, 482, 633);
                lblPDFPageDisplay.setIcon(new ImageIcon(resizedImage));
            } else {
                BufferedImage resizedImage = PDFwatermark01.resize(bim, 482, 350);
                lblPDFPageDisplay.setIcon(new ImageIcon(resizedImage));
            }

            //Resize image to fit page image on Label.
            document.close();
            //System.out.println("At the top value is : " + PDFwatermark01.pageNumber);
        } catch (Exception ex) {
            Logger.getLogger(ex.toString());
        }
    }

    protected static void previewFirstPagePDF01() {

        BufferedImage bim = null;

        try {
            PDDocument document = PDDocument.load(new File(PDFwatermark01.tempPDFFile.getAbsolutePath())); //Append .PDF. However, temporary file is to be read.
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            PDFwatermark01.pageNumber = 0;
            bim = pdfRenderer.renderImageWithDPI(PDFwatermark01.pageNumber, 300, ImageType.RGB);

            //Check page orientation
            PDPage currentPDFPage = document.getPage(PDFwatermark01.pageNumber);
            Float currentPDFPageH = currentPDFPage.getMediaBox().getHeight();
            Float currentPDFPageW = currentPDFPage.getMediaBox().getWidth();

            if (currentPDFPageH >= currentPDFPageW) {
                BufferedImage resizedImage = PDFwatermark01.resize(bim, 482, 633);
                lblPDFPageDisplay.setIcon(new ImageIcon(resizedImage));
            } else {
                BufferedImage resizedImage = PDFwatermark01.resize(bim, 482, 350);
                lblPDFPageDisplay.setIcon(new ImageIcon(resizedImage));
            }

            //Resize image to fit page image on Label.
            document.close();
            //System.out.println("At the top value is : " + PDFwatermark01.pageNumber);
        } catch (Exception ex) {
            Logger.getLogger(ex.toString());
        }
    }

    protected static void savePDF01() {

        PDFwatermark01.varFileSaver01.setFileSelectionMode(JFileChooser.FILES_ONLY);
        PDFwatermark01.varFileSaver01.setDialogTitle("Enter name of file name (Extension name of .pdf will be added automatically.)");
        PDFwatermark01.varFileSaver01.setAcceptAllFileFilterUsed(false);

        if (PDFwatermark01.countFileExtension3 <= 0) {
            PDFwatermark01.varFileSaver01.setFileFilter(new MyCustomFilter03());
            PDFwatermark01.countFileExtension3++;
        }

        PDFwatermark01.returnVal = PDFwatermark01.varFileSaver01.showSaveDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            PDFwatermark01.file3 = PDFwatermark01.varFileSaver01.getSelectedFile();
            try {
                //process of watermarking takes place here.
                PDFwatermark01.varTextArea.append("Going on to save file : " + PDFwatermark01.file3.getAbsolutePath() + "\n");

                ImageStampingWatermarking.Watermark2(PDFwatermark01.file1, PDFwatermark01.file2, PDFwatermark01.file3, PDFwatermark01.imageWidthSliderValue, PDFwatermark01.imageHeigthSliderValue, PDFwatermark01.imageTransparency);
                //ImageStampingWatermarking.Watermark2(PDFwatermark01.file1, PDFwatermark01.file2, PDFwatermark01.file3, PDFwatermark01.imageWidthSliderValue, PDFwatermark01.imageHeigthSliderValue, PDFwatermark01.imageTransparency);
                //ImageStampingWatermarking.AddImage2(PDFwatermark01.file1, PDFwatermark01.file2, PDFwatermark01.file3, PDFwatermark01.imageWidthSliderValue, PDFwatermark01.imageHeigthSliderValue, PDFwatermark01.imageTransparency );
                PDFwatermark01.varTextArea.append("File saved : " + PDFwatermark01.file3.getAbsolutePath() + "\n");
            } catch (Exception ex) {
                PDFwatermark01.varTextArea.append("Problem saving file : " + PDFwatermark01.file3.getAbsolutePath() + "\n");
                PDFwatermark01.varTextArea.append("Problem saving file : " + ex.toString() + "\n");
            }
        } else {
            varTextArea.append("File saving processed cancelled by user." + "\n");
        }
    }

    public PDFwatermark01() {
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        varFileChooser01 = new javax.swing.JFileChooser();
        varFileChooser02 = new javax.swing.JFileChooser();
        varFileSaver01 = new javax.swing.JFileChooser();
        frmPreviewWindow = new javax.swing.JFrame();
        jPanelPDFPage = new javax.swing.JPanel();
        lblPDFPageDisplay = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        previewYaxis = new javax.swing.JSlider();
        previewXaxis = new javax.swing.JSlider();
        jScrollPane1 = new javax.swing.JScrollPane();
        varTextArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        lblFileName = new javax.swing.JLabel();
        txtImageSelected = new javax.swing.JTextField();
        lblImage = new javax.swing.JLabel();
        txtFileSelected = new javax.swing.JTextField();
        sliderHeight = new javax.swing.JSlider();
        lblSetImageWidth = new javax.swing.JLabel();
        lblImageWidthValue = new javax.swing.JLabel();
        lblSetImageHeight = new javax.swing.JLabel();
        sliderWidth = new javax.swing.JSlider();
        lblImageHeightValue = new javax.swing.JLabel();
        lblSetTransparencyValue = new javax.swing.JLabel();
        sliderTransparency = new javax.swing.JSlider();
        lblImageTransparencyValue = new javax.swing.JLabel();
        btnChooseFilePDF = new javax.swing.JButton();
        btnChooseFileImage = new javax.swing.JButton();
        btnFinalPDF = new javax.swing.JButton();
        btnPreviewPDF = new javax.swing.JButton();
        xAxisPDF = new javax.swing.JSlider();
        yAxisPDF = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        varExit = new javax.swing.JMenuItem();

        varFileChooser01.setCurrentDirectory(null);
        varFileChooser01.setDialogTitle("");

        frmPreviewWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frmPreviewWindow.setTitle("Preview PDF");
        frmPreviewWindow.setResizable(false);
        frmPreviewWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                frmPreviewWindowWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                frmPreviewWindowWindowOpened(evt);
            }
        });

        jPanelPDFPage.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblPDFPageDisplay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/education-148605_960_720.png"))); // NOI18N

        javax.swing.GroupLayout jPanelPDFPageLayout = new javax.swing.GroupLayout(jPanelPDFPage);
        jPanelPDFPage.setLayout(jPanelPDFPageLayout);
        jPanelPDFPageLayout.setHorizontalGroup(
            jPanelPDFPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPDFPageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPDFPageDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelPDFPageLayout.setVerticalGroup(
            jPanelPDFPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPDFPageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPDFPageDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton1.setText("First Page of PDF");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Previous Page");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Next Page");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Last Page of PDF");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Generate PDF");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        previewYaxis.setOrientation(javax.swing.JSlider.VERTICAL);
        previewYaxis.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                previewYaxisStateChanged(evt);
            }
        });

        previewXaxis.setOrientation(javax.swing.JSlider.VERTICAL);
        previewXaxis.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                previewXaxisStateChanged(evt);
            }
        });

        javax.swing.GroupLayout frmPreviewWindowLayout = new javax.swing.GroupLayout(frmPreviewWindow.getContentPane());
        frmPreviewWindow.getContentPane().setLayout(frmPreviewWindowLayout);
        frmPreviewWindowLayout.setHorizontalGroup(
            frmPreviewWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmPreviewWindowLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelPDFPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(frmPreviewWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmPreviewWindowLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(previewYaxis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(previewXaxis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(60, Short.MAX_VALUE))
                    .addGroup(frmPreviewWindowLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(frmPreviewWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        frmPreviewWindowLayout.setVerticalGroup(
            frmPreviewWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmPreviewWindowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmPreviewWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmPreviewWindowLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addGap(51, 51, 51)
                        .addGroup(frmPreviewWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(previewYaxis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(previewXaxis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanelPDFPage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("varMainFrame"); // NOI18N
        setResizable(false);

        varTextArea.setColumns(20);
        varTextArea.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        varTextArea.setForeground(new java.awt.Color(0, 51, 255));
        varTextArea.setRows(5);
        jScrollPane1.setViewportView(varTextArea);

        jLabel1.setText("Activity logs");

        lblFileName.setText("PDF file selected is");

        txtImageSelected.setEditable(false);

        lblImage.setText("Image file selected is");

        txtFileSelected.setEditable(false);

        sliderHeight.setMaximum(250);
        sliderHeight.setMinimum(25);
        sliderHeight.setMinorTickSpacing(10);
        sliderHeight.setPaintLabels(true);
        sliderHeight.setPaintTicks(true);
        sliderHeight.setValue(135);
        sliderHeight.setEnabled(false);
        sliderHeight.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderHeightStateChanged(evt);
            }
        });

        lblSetImageWidth.setText("Set image width (pixels)");

        lblSetImageHeight.setText("Set image height (pixels)");

        sliderWidth.setMaximum(250);
        sliderWidth.setMinimum(25);
        sliderWidth.setMinorTickSpacing(10);
        sliderWidth.setPaintLabels(true);
        sliderWidth.setPaintTicks(true);
        sliderWidth.setValue(135);
        sliderWidth.setEnabled(false);
        sliderWidth.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderWidthStateChanged(evt);
            }
        });

        lblSetTransparencyValue.setText("Set transparency value");

        sliderTransparency.setMinorTickSpacing(10);
        sliderTransparency.setPaintLabels(true);
        sliderTransparency.setPaintTicks(true);
        sliderTransparency.setValue(0);
        sliderTransparency.setEnabled(false);
        sliderTransparency.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderTransparencyStateChanged(evt);
            }
        });

        btnChooseFilePDF.setText("Choose PDF File");
        btnChooseFilePDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseFilePDFActionPerformed(evt);
            }
        });

        btnChooseFileImage.setText("Choose Image File");
        btnChooseFileImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseFileImageActionPerformed(evt);
            }
        });

        btnFinalPDF.setText("Generate PDF");
        btnFinalPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalPDFActionPerformed(evt);
            }
        });

        btnPreviewPDF.setText("Preview PDF");
        btnPreviewPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviewPDFActionPerformed(evt);
            }
        });

        xAxisPDF.setEnabled(false);
        xAxisPDF.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                xAxisPDFStateChanged(evt);
            }
        });

        yAxisPDF.setOrientation(javax.swing.JSlider.VERTICAL);
        yAxisPDF.setEnabled(false);
        yAxisPDF.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                yAxisPDFStateChanged(evt);
            }
        });

        jLabel2.setText("Vertical positioning of image relative to page");

        jLabel3.setText("Horizontal positioning of image relative to page");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/download.jpg"))); // NOI18N
        jLabel4.setText("jLabel4");

        jMenu1.setText("File");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        varExit.setText("Exit");
        varExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                varExitActionPerformed(evt);
            }
        });
        jMenu1.add(varExit);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFileName, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSetImageHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSetTransparencyValue, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(sliderTransparency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sliderHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(107, 107, 107)
                                .addComponent(yAxisPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(xAxisPDF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 312, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(145, 145, 145)
                                                .addComponent(btnFinalPDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(65, 65, 65)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(283, 283, 283)
                                        .addComponent(btnPreviewPDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSetImageWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtFileSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnChooseFilePDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtImageSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(sliderWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(38, 38, 38)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblImageHeightValue, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblImageWidthValue, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblImageTransparencyValue, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(18, 18, 18)
                                        .addComponent(btnChooseFileImage, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))))
                            .addComponent(jScrollPane1))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFileName)
                            .addComponent(txtFileSelected, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnChooseFilePDF))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtImageSelected, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImage)
                            .addComponent(btnChooseFileImage))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sliderWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblImageWidthValue, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSetImageWidth))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(lblSetImageHeight))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(sliderHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(lblImageHeightValue, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSetTransparencyValue)
                                    .addComponent(lblImageTransparencyValue, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sliderTransparency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(26, 26, 26)
                        .addComponent(yAxisPDF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(xAxisPDF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPreviewPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFinalPDF)
                        .addGap(50, 50, 50)))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        getAccessibleContext().setAccessibleParent(this);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sliderHeightStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderHeightStateChanged
        // TODO add your handling code here:
        PDFwatermark01.imageHeigthSliderValue = sliderHeight.getValue();
        lblImageHeightValue.setText(Integer.toString(sliderHeight.getValue()));

    }//GEN-LAST:event_sliderHeightStateChanged

    private void sliderWidthStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderWidthStateChanged

        PDFwatermark01.imageWidthSliderValue = sliderWidth.getValue();
        lblImageWidthValue.setText(Integer.toString(sliderWidth.getValue()));
    }//GEN-LAST:event_sliderWidthStateChanged

    private void sliderTransparencyStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderTransparencyStateChanged
        PDFwatermark01.imageTransparency = sliderTransparency.getValue();
        lblImageTransparencyValue.setText(Integer.toString(sliderTransparency.getValue()) + " %");
    }//GEN-LAST:event_sliderTransparencyStateChanged

    private void btnChooseFilePDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseFilePDFActionPerformed
        //  varFileChooser01.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        varFileChooser01.setFileSelectionMode(JFileChooser.FILES_ONLY);
        varFileChooser01.setAcceptAllFileFilterUsed(false);

        if (PDFwatermark01.countFileExtension1 <= 0) {
            varFileChooser01.setFileFilter(new MyCustomFilter01());
            PDFwatermark01.countFileExtension1++;
        }

        varFileChooser01.setDialogTitle("Choose a PDF file");
        int returnVal = varFileChooser01.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            PDFwatermark01.file1 = varFileChooser01.getSelectedFile();
            try {
                // What to do with the file, e.g. display it in a TextArea
                //varTextArea.read(new FileReader(file.getAbsolutePath()), null);
                txtFileSelected.setText(file1.getName());
                varTextArea.append("File accessed : " + file1.getAbsolutePath() + "\n");
            } catch (Exception ex) {
                varTextArea.append("Problem accessing PDF file : " + file1.getAbsolutePath() + "\n");
            }
        } else {
            varTextArea.append("PDF file access cancelled by user." + "\n");
        }
    }//GEN-LAST:event_btnChooseFilePDFActionPerformed

    private void btnChooseFileImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseFileImageActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        // TODO add your handling code here:
        //  varFileChooser01.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        varFileChooser02.setFileSelectionMode(JFileChooser.FILES_ONLY);
        varFileChooser02.setAcceptAllFileFilterUsed(false);

        if (PDFwatermark01.countFileExtension2 <= 0) {
            varFileChooser02.setFileFilter(new MyCustomFilter02());
            PDFwatermark01.countFileExtension2++;
        }

        varFileChooser02.setDialogTitle("Choose a Transparent Image file (PNG)");
        int returnVal = varFileChooser02.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            PDFwatermark01.file2 = varFileChooser02.getSelectedFile();

            try {

                BufferedImage bimg = ImageIO.read(PDFwatermark01.file2);
                PDFwatermark01.imageWidthDetected = bimg.getWidth();
                PDFwatermark01.imageHeightDetected = bimg.getHeight();

                sliderWidth.setValue((int) PDFwatermark01.imageWidthDetected);
                sliderHeight.setValue((int) PDFwatermark01.imageHeightDetected);

                // What to do with the file, e.g. display it in a TextArea
                txtImageSelected.setText(PDFwatermark01.file2.getName());
                varTextArea.append("File accessed : " + file2.getAbsolutePath() + "\n");

                ImageStampingWatermarking.obtainSliderPositions2();

                //Enable all sliders
                sliderWidth.setEnabled(true);
                sliderHeight.setEnabled(true);
                sliderTransparency.setEnabled(true);
                yAxisPDF.setEnabled(true);
                xAxisPDF.setEnabled(true);

                yAxisPDF.setMinimum((int) PDFwatermark01.yAxisPDFPageMIN);
                yAxisPDF.setMaximum((int) PDFwatermark01.yAxisPDFPageMAX);
                xAxisPDF.setMinimum((int) PDFwatermark01.xAxisPDFPageMIN);
                xAxisPDF.setMaximum((int) PDFwatermark01.xAxisPDFPageMAX);

                //Set preview slider values range.
                previewYaxis.setMinimum((int) PDFwatermark01.yAxisPDFPageMIN);
                previewYaxis.setMaximum((int) PDFwatermark01.yAxisPDFPageMAX);
                previewXaxis.setMinimum((int) PDFwatermark01.xAxisPDFPageMIN);
                previewXaxis.setMaximum((int) PDFwatermark01.xAxisPDFPageMAX);

                varTextArea.append("Lower most point of PDF page : " + PDFwatermark01.yAxisPDFPageMIN + "\n");
                varTextArea.append("Top most point of PDF page : " + PDFwatermark01.yAxisPDFPageMAX + "\n");
                varTextArea.append("Left-most point of PDF page : " + PDFwatermark01.xAxisPDFPageMIN + "\n");
                varTextArea.append("Right-most point of PDF page : " + PDFwatermark01.xAxisPDFPageMAX + "\n");

            } catch (Exception ex) {
                varTextArea.append("Problem accessing image file : " + file2.getAbsolutePath() + "\n");
            }
        } else {
            varTextArea.append("Image file access cancelled by user." + "\n");
        }
    }//GEN-LAST:event_btnChooseFileImageActionPerformed

    private void btnFinalPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalPDFActionPerformed
        // Final PDF production.
        PDFwatermark01.savePDF01();
    }//GEN-LAST:event_btnFinalPDFActionPerformed

    private void btnPreviewPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewPDFActionPerformed

        try {
            PDFwatermark01.tempPDFFile = null;
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            PDFwatermark01.tempPDFFile = File.createTempFile("tempPDF", ".pdf");
            ImageStampingWatermarking.Watermark2(PDFwatermark01.file1, PDFwatermark01.file2, PDFwatermark01.tempPDFFile, PDFwatermark01.imageWidthSliderValue, PDFwatermark01.imageHeigthSliderValue, PDFwatermark01.imageTransparency);
            //System.out.println("At preview creation, the temporary file read is " + PDFwatermark01.tempPDFFile.getAbsolutePath()); PDFwatermark01.yAxisImagePosition
        } catch (Exception ex) {

        } finally {
            //PDFwatermark01.tempPDFFile.deleteOnExit();
        }

        try {
            PDFwatermark01.this.setVisible(false);
//            frmPreviewWindow.setVisible(true);
            frmPreviewWindow.setSize(689, 682);
            frmPreviewWindow.setLocationRelativeTo(null);
            //Thread.sleep(2000);
            PDFwatermark01.previewFirstPagePDF01();
            previewYaxis.setValue(PDFwatermark01.yAxisPDF.getValue());
            previewXaxis.setValue(PDFwatermark01.xAxisPDF.getValue());
            frmPreviewWindow.setVisible(true);

        } catch (Exception ex) {

        }


    }//GEN-LAST:event_btnPreviewPDFActionPerformed

    private void frmPreviewWindowWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_frmPreviewWindowWindowClosed
        try {
            lblPDFPageDisplay.setIcon(null);
            PDFwatermark01.this.setVisible(true);
            PDFwatermark01.this.yAxisPDF.setValue(previewYaxis.getValue());
            PDFwatermark01.this.xAxisPDF.setValue(previewXaxis.getValue());
            PDFwatermark01.tempPDFFile.deleteOnExit();
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_frmPreviewWindowWindowClosed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed

    }//GEN-LAST:event_jMenu1ActionPerformed

    private void varExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_varExitActionPerformed

        System.exit(0);
    }//GEN-LAST:event_varExitActionPerformed

    private void yAxisPDFStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_yAxisPDFStateChanged
        PDFwatermark01.yAxisImagePosition = yAxisPDF.getValue();
    }//GEN-LAST:event_yAxisPDFStateChanged

    private void xAxisPDFStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_xAxisPDFStateChanged
        PDFwatermark01.xAxisImagePosition = xAxisPDF.getValue();
    }//GEN-LAST:event_xAxisPDFStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        BufferedImage bim = null;

        try {

            //System.out.println("At preview button clicks, the temporary file read is "+PDFwatermark01.tempPDFFile.getAbsolutePath());
            PDDocument document = PDDocument.load(new File(PDFwatermark01.tempPDFFile.getAbsolutePath())); //Append .PDF. However, temporary file is to be read.
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            PDFwatermark01.pageNumber = 0;

            bim = pdfRenderer.renderImageWithDPI(PDFwatermark01.pageNumber, 300, ImageType.RGB);

            //            int pageCounter = 0;
            //                       for (PDPage page : document.getPages()) {
            //                bim = pdfRenderer.renderImageWithDPI(pageCounter, 300, ImageType.RGB);
            //                //ImageIOUtil.writeImage(bim, pdfFilename + "-" + (pageCounter++) + ".png", 300);
            //                break;
            //            }
            document.close();

            //Check page orientation
            PDPage currentPDFPage = document.getPage(PDFwatermark01.pageNumber);
            Float currentPDFPageH = currentPDFPage.getMediaBox().getHeight();
            Float currentPDFPageW = currentPDFPage.getMediaBox().getWidth();

            if (currentPDFPageH >= currentPDFPageW) {
                BufferedImage resizedImage = PDFwatermark01.resize(bim, 482, 633);
                lblPDFPageDisplay.setIcon(new ImageIcon(resizedImage));
            } else {
                BufferedImage resizedImage = PDFwatermark01.resize(bim, 482, 350);
                lblPDFPageDisplay.setIcon(new ImageIcon(resizedImage));
            }

            //System.out.println("At the top value is : " + PDFwatermark01.pageNumber);
        } catch (Exception ex) {
            Logger.getLogger(ex.toString());
            //System.out.println("At the First Page button, the error is " + ex.toString());
        }

        try {
            previewXaxis.setValue(xAxisPDF.getValue());
            previewYaxis.setValue(yAxisPDF.getValue());
        } catch (Exception ex) {
            //This section contributes to slider values in the preview pane.
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        BufferedImage bim = null;
        try {
            if (PDFwatermark01.pageNumber >= 1) {
                PDFwatermark01.pageNumber = PDFwatermark01.pageNumber - 1;
            } else {
                PDFwatermark01.pageNumber = 0;
            }

            PDDocument document = PDDocument.load(new File(PDFwatermark01.tempPDFFile.getAbsolutePath()));
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            bim = pdfRenderer.renderImageWithDPI(PDFwatermark01.pageNumber, 300, ImageType.RGB);
            document.close();

            //Check page orientation
            PDPage currentPDFPage = document.getPage(PDFwatermark01.pageNumber);
            Float currentPDFPageH = currentPDFPage.getMediaBox().getHeight();
            Float currentPDFPageW = currentPDFPage.getMediaBox().getWidth();

            if (currentPDFPageH >= currentPDFPageW) {
                BufferedImage resizedImage = PDFwatermark01.resize(bim, 482, 633);
                lblPDFPageDisplay.setIcon(new ImageIcon(resizedImage));
            } else {
                BufferedImage resizedImage = PDFwatermark01.resize(bim, 482, 350);
                lblPDFPageDisplay.setIcon(new ImageIcon(resizedImage));
            }

            // System.out.println("At the previous page the value is : " + PDFwatermark01.pageNumber);
        } catch (Exception ex) {
            Logger.getLogger(ex.toString());
            //System.out.println("At the Previous Page button, the error is " + ex.toString());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        BufferedImage bim = null;

        try {
            PDDocument document = PDDocument.load(new File(PDFwatermark01.tempPDFFile.getAbsolutePath()));
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            if (PDFwatermark01.pageNumber < document.getNumberOfPages()) {
                PDFwatermark01.pageNumber = PDFwatermark01.pageNumber + 1;
            } else {
                PDFwatermark01.pageNumber = document.getNumberOfPages();
            }

            bim = pdfRenderer.renderImageWithDPI(PDFwatermark01.pageNumber, 300, ImageType.RGB);

            document.close();

            //Check page orientation
            PDPage currentPDFPage = document.getPage(PDFwatermark01.pageNumber);
            Float currentPDFPageH = currentPDFPage.getMediaBox().getHeight();
            Float currentPDFPageW = currentPDFPage.getMediaBox().getWidth();

            if (currentPDFPageH >= currentPDFPageW) {
                BufferedImage resizedImage = PDFwatermark01.resize(bim, 482, 633);
                lblPDFPageDisplay.setIcon(new ImageIcon(resizedImage));
            } else {
                BufferedImage resizedImage = PDFwatermark01.resize(bim, 482, 350);
                lblPDFPageDisplay.setIcon(new ImageIcon(resizedImage));
            }

            //System.out.println("At the next page the value is : " + PDFwatermark01.pageNumber);
        } catch (Exception ex) {
            PDFwatermark01.pageNumber = PDFwatermark01.pageNumber - 1;
            Logger.getLogger(ex.toString());
            //System.out.println("At the Next Page button, the error is " + ex.toString());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        BufferedImage bim = null;

        try {
            PDDocument document = PDDocument.load(new File(PDFwatermark01.tempPDFFile.getAbsolutePath()));
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            PDFwatermark01.pageNumber = document.getNumberOfPages() - 1;

            bim = pdfRenderer.renderImageWithDPI(PDFwatermark01.pageNumber, 300, ImageType.RGB);

            document.close();

            //Check page orientation
            PDPage currentPDFPage = document.getPage(PDFwatermark01.pageNumber);
            Float currentPDFPageH = currentPDFPage.getMediaBox().getHeight();
            Float currentPDFPageW = currentPDFPage.getMediaBox().getWidth();

            if (currentPDFPageH >= currentPDFPageW) {
                BufferedImage resizedImage = PDFwatermark01.resize(bim, 482, 633);
                lblPDFPageDisplay.setIcon(new ImageIcon(resizedImage));
            } else {
                BufferedImage resizedImage = PDFwatermark01.resize(bim, 482, 350);
                lblPDFPageDisplay.setIcon(new ImageIcon(resizedImage));
            }

//            BufferedImage resizedImage = PDFwatermark01.resize(bim, 482, 633);
//            lblPDFPageDisplay.setIcon(new ImageIcon(resizedImage));
            //System.out.println("At the last page the value is : " + PDFwatermark01.pageNumber);
        } catch (Exception ex) {
            Logger.getLogger(ex.toString());
            //System.out.println("At the Last Page button, the error is " + ex.toString());
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // Generate Production PDF from Preview.
        try {
            PDFwatermark01.savePDF01();
        } catch (Exception ex) {
            System.out.println("Error caught trying to save action in preview : " + ex.toString());
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void frmPreviewWindowWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_frmPreviewWindowWindowOpened

    }//GEN-LAST:event_frmPreviewWindowWindowOpened

    private void previewYaxisStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_previewYaxisStateChanged
        // Interesting bit. Live change preview section.
        try {
            PDFwatermark01.yAxisImagePosition = previewYaxis.getValue();
            ImageStampingWatermarking.Watermark2(PDFwatermark01.file1, PDFwatermark01.file2, PDFwatermark01.tempPDFFile, PDFwatermark01.imageWidthSliderValue, PDFwatermark01.imageHeigthSliderValue, PDFwatermark01.imageTransparency);
//            PDFwatermark01.previewFirstPagePDF01();
            //System.out.println("Current page number: "+PDFwatermark01.pageNumber);

            PDFwatermark01.livePreviewPagePDF01();

        } catch (Exception ex) {
            System.out.println("Error in Preview mode Y-Axis change : " + ex.toString());
        }
    }//GEN-LAST:event_previewYaxisStateChanged

    private void previewXaxisStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_previewXaxisStateChanged
        // Interesting bit. Live change preview section.
        try {
            PDFwatermark01.xAxisImagePosition = previewXaxis.getValue();
            ImageStampingWatermarking.Watermark2(PDFwatermark01.file1, PDFwatermark01.file2, PDFwatermark01.tempPDFFile, PDFwatermark01.imageWidthSliderValue, PDFwatermark01.imageHeigthSliderValue, PDFwatermark01.imageTransparency);
//            PDFwatermark01.previewFirstPagePDF01();

            PDFwatermark01.livePreviewPagePDF01();

        } catch (Exception ex) {
            System.out.println("Error in Preview mode Y-Axis change : " + ex.toString());
        }


    }//GEN-LAST:event_previewXaxisStateChanged

    /**
     * @param args the command line arguments
     */
    protected static PDFwatermark01 pdfWatermark01;

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PDFwatermark01.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PDFwatermark01.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PDFwatermark01.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PDFwatermark01.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                pdfWatermark01 = new PDFwatermark01();
                pdfWatermark01.setVisible(true);
                pdfWatermark01.setTitle("PDF Water Mark Utility Application");

                //Hidden from view prior to first trials for Mr. Sayok Das on 04-09-2018.
                //pdfWatermark01.btnPreviewPDF.setVisible(false);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChooseFileImage;
    private javax.swing.JButton btnChooseFilePDF;
    private javax.swing.JButton btnFinalPDF;
    private javax.swing.JButton btnPreviewPDF;
    protected static javax.swing.JFrame frmPreviewWindow;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    protected static javax.swing.JPanel jPanelPDFPage;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFileName;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblImageHeightValue;
    private javax.swing.JLabel lblImageTransparencyValue;
    private javax.swing.JLabel lblImageWidthValue;
    protected static javax.swing.JLabel lblPDFPageDisplay;
    private javax.swing.JLabel lblSetImageHeight;
    private javax.swing.JLabel lblSetImageWidth;
    private javax.swing.JLabel lblSetTransparencyValue;
    protected static javax.swing.JSlider previewXaxis;
    protected static javax.swing.JSlider previewYaxis;
    private javax.swing.JSlider sliderHeight;
    private javax.swing.JSlider sliderTransparency;
    private javax.swing.JSlider sliderWidth;
    private javax.swing.JTextField txtFileSelected;
    private javax.swing.JTextField txtImageSelected;
    private javax.swing.JMenuItem varExit;
    private static javax.swing.JFileChooser varFileChooser01;
    private static javax.swing.JFileChooser varFileChooser02;
    protected static javax.swing.JFileChooser varFileSaver01;
    protected static javax.swing.JTextArea varTextArea;
    protected static javax.swing.JSlider xAxisPDF;
    protected static javax.swing.JSlider yAxisPDF;
    // End of variables declaration//GEN-END:variables

}
