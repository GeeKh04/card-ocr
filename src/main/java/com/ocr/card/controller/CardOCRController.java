package com.ocr.card.controller;

import com.ocr.card.model.Card;
import com.ocr.card.service.CardOCRService;
import com.ocr.card.service.ICardOCRService;

import javax.imageio.ImageIO;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;

import static spark.Spark.post;

// Java 11
public class CardOCRController {
    private ICardOCRService cardOCRService;

    public void uploadCardTransferFile(){
        post("/api/upload", (req, res) -> {
            Card card = null;
            // TO allow for multipart file uploads
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement(""));
            try {
                // "file" is the key of the form data with the file itself being the value
                Part filePart = req.raw().getPart("file");

                // The name of the file user uploaded
                InputStream streamImage = filePart.getInputStream();
                BufferedImage originalImage = ImageIO.read(streamImage);

                cardOCRService = new CardOCRService();
                card = cardOCRService.getCardData(originalImage);
            } catch (IOException | ServletException e) {
                return "Exception occurred while uploading file" + e.getMessage();
            }

            return card;
        });
    }
}
