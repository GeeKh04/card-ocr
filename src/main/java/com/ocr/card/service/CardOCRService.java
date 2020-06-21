package com.ocr.card.service;

import com.ocr.card.model.Card;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept;
import org.bytedeco.javacpp.tesseract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.bytedeco.javacpp.lept.*;

public class CardOCRService implements ICardOCRService {
    private String tessdataLocation = "./src/main/resources/data/tessdata";

    private static final Logger logger = LoggerFactory.getLogger(CardOCRService.class);

    @Override
    public Card getCardData(BufferedImage file){
        /** Capture des données de carte: **/
        byte[] croppedImageInByte=null;

        // Text Region
        double x1; // x1 coordinate of the upper-left corner
        double y1; // y1 coordinate of the downer-left corner
        double x2; // x2 coordinate of the upper-right corner
        double y2; // y2 coordinate of the downer-right corner
        String tessdata;

        // Capture LastName
        x1 = 0.13;//0.32;
        y1 = 0.43;//0.29
        x2 = 0.58;//0.71;
        y2 = 0.52;//0.39;
        tessdata = "eng";
        croppedImageInByte = cropImage(file,x1,y1,x2,y2);
        String lastName = ocr(croppedImageInByte,tessdata);

        // Capture FirstName
        x1 = 0.16;//0.38;
        y1 = 0.52;//0.39;
        x2 = 0.58;//0.71;
        y2 = 0.60;//0.49;
        tessdata = "eng";
        croppedImageInByte = cropImage(file,x1,y1,x2,y2);
        String firstName = ocr(croppedImageInByte,tessdata);

        // Capture Reference (CNE)
        x1 = 0.32;//0.31;
        y1 = 0.60;//0.49;
        x2 = 0.58;//0.71;
        y2 = 0.68;//0.59;
        tessdata = "digits";
        croppedImageInByte = cropImage(file,x1,y1,x2,y2);
        long reference = Long.parseLong(ocr(croppedImageInByte,tessdata));

        // Capture BirthDate
        x1 = 0.18;
        y1 = 0.68;
        x2 = 0.34;
        y2 = 0.77;
        tessdata = "eng";

        croppedImageInByte = cropImage(file,x1,y1,x2,y2);
        String strDate = ocr(croppedImageInByte,tessdata);
        Date date=null;
        try {
            date=new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + ": ERREUR (SignatureDate)!" + strDate);
        }

        /* Capture code barre
        x1 = 0.18;
        y1 = 0.59;
        x2 = 0.71;
        y2 = 0.89;
        croppedImageInByte = cropImage(file,x1,y1,x2,y2);
        String cardNumber;
        /*try (FileOutputStream fos = new FileOutputStream("pathname.png")) {
            fos.write(croppedImageInByte);
            //fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
        } catch (IOException e) {
            e.printStackTrace();
        }*
        try {
            cardNumber = QRCodeReader.decodeQRCode(croppedImageInByte);
            if(cardNumber == null) {
                cardNumber = "null";
                System.out.println("No QR Code found in the image");
            }
        } catch (IOException e) {
            cardNumber = "null";
            System.out.println("Could not decode QR Code, IOException :: " + e.getMessage());
        }*/

        /** Enregistrer le fichier reçu **/
        Card cardData = new Card();

        cardData.setLastName(lastName);
        cardData.setFirstName(firstName);
        cardData.setReference(reference);
        //cardData.setCardNumber(cardNumber);
        cardData.setBirthDate(date);

        System.out.println(cardData);

        return cardData;
    }

    public static final int OCR_COLOR_THRESHOLD = 180;
    private String ocr(byte[] imageBytes, String tessdata) {
        String parsedOut=null;

        try{
            // OCR recognition
            tesseract.TessBaseAPI api = new tesseract.TessBaseAPI();

            // Initialize tesseract-ocr with English, without specifying tessdata path
            if (api.Init(tessdataLocation, tessdata) != 0) {
                System.err.println("Could not initialize tesseract.");
                System.exit(1);
            }

            ByteBuffer imgBB = ByteBuffer.wrap(imageBytes);

            lept.PIX image = pixReadMem(imgBB, imageBytes.length);
            api.SetImage(image);

            // Get OCR result
            BytePointer outText = api.GetUTF8Text();
            parsedOut = outText.getString();
            // OCR corrections
            parsedOut=parsedOut.replaceAll("\\s+","");
            // for Date
            parsedOut=parsedOut.replaceAll("l","1").replaceAll("o","0").replaceAll("O","0");

            // Destroy used object and release memory
            api.End();
            api.close();
            outText.deallocate();
            pixDestroy(image);

        } catch (Exception e) {
            logger.warn("IOException in OCR", e);
        }
        return parsedOut;
    }

    private byte[] cropImage(BufferedImage originalImage, double x1, double y1, double x2, double y2) {
        byte[] imageInByte = null;
        try {
            // Get image dimensions
            int height = originalImage.getHeight();
            int width = originalImage.getWidth();

            BufferedImage croppedImage = originalImage.getSubimage(
                    (int)(width*x1), // x coordinate of the upper-left corner
                    (int)(height*y1), // y coordinate of the upper-left corner
                    (int)((x2-x1)*width), // width
                    (int)((y2-y1)*height) // height
            );

            /*/ Color image to pure black and white
            for (int x = 0; x < croppedImage.getWidth(); x++) {
                for (int y = 0; y < croppedImage.getHeight(); y++) {
                    Color color = new Color(croppedImage.getRGB(x, y));
                    int red = color.getRed();
                    int green = color.getBlue();
                    int blue = color.getGreen();
                    if (red + green + blue > OCR_COLOR_THRESHOLD) {
                        red = green = blue = 0; // Black
                    } else {
                        red = green = blue = 255; // White
                    }
                    Color col = new Color(red, green, blue);
                    croppedImage.setRGB(x, y, col.getRGB());
                }
            }//*/

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( croppedImage, "png", baos );
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            logger.warn("IOException in CROP", e);
        }

        return imageInByte;
    }
}
