package com.ocr.card.ui;

import com.ocr.card.model.Card;
import com.ocr.card.service.CardOCRService;
import com.ocr.card.service.ICardOCRService;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardFrame extends JFrame implements ActionListener {
    private JPanel principalPanel;
    private JPanel textBoxPanel;
    private CardPanel cardPanel = new CardPanel();
    private JFileChooser cardChooser;
    private JTextField textField= new JTextField(30);;
    private JButton jButtonBrowse = new JButton("Browse...");;
    private JButton jButton = new JButton("Scan");

    JLabel jLabelLastName = new JLabel("Last Name : ");
    JLabel jLabelFirstName = new JLabel("First Name : ");
    JLabel jLabelReference = new JLabel("Reference : ");
    //JLabel jLabelCardNumber = new JLabel("Card Number : ");
    JLabel jLabelBirthDate = new JLabel("Birth Date : ");

    JTextField jTextFieldLastName = new JTextField(10);
    JTextField jTextFieldFirstName = new JTextField(10);
    JTextField jTextFieldReference = new JTextField(10);
    JTextField jTextFieldCardNumber = new JTextField(10);
    JTextField jTextFieldBirthDate = new JTextField(10);

    public CardFrame(){
        try {
            this.setTitle("Check OCR with Tesseract");
            this.setSize(900,600);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setLayout(new BorderLayout());

            principalPanel = new JPanel(new FlowLayout());

            cardChooser = new JFileChooser(new File("./src/main/resources/data/card"));
            cardChooser.setDialogTitle("Select an image");
            cardChooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG and JPG images", "png", "jpg");
            cardChooser.addChoosableFileFilter(filter);

            textBoxPanel = new JPanel(new GridLayout(4, 2));

            textBoxPanel.add(jLabelLastName);
            textBoxPanel.add(jTextFieldLastName);
            textBoxPanel.add(jLabelFirstName);
            textBoxPanel.add(jTextFieldFirstName);
            textBoxPanel.add(jLabelReference);
            textBoxPanel.add(jTextFieldReference);
            /*textBoxPanel.add(jLabelCardNumber);
            textBoxPanel.add(jTextFieldCardNumber);*/
            textBoxPanel.add(jLabelBirthDate);
            textBoxPanel.add(jTextFieldBirthDate);
            textBoxPanel.setVisible(false);

            principalPanel.add(textField);
            principalPanel.add(jButtonBrowse);
            principalPanel.add(jButton);
            principalPanel.add(textBoxPanel);
            this.add(principalPanel,BorderLayout.NORTH);
            this.add(cardPanel,BorderLayout.CENTER);
            this.setVisible(true);

            jButtonBrowse.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    buttonActionPerformed(evt);
                }
            });

            jButton.addActionListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            String selectedCard = textField.getText();
            File imageFile = new File(selectedCard);
            byte[] image = Files.readAllBytes(imageFile.toPath());

            // Get a BufferedImage object from a byte array
            InputStream imageStream = new ByteArrayInputStream(image);
            BufferedImage originalImage = ImageIO.read(imageStream);

            ICardOCRService cardStorageService = new CardOCRService();
            Card card = cardStorageService.getCardData(originalImage);

            jTextFieldLastName.setText(card.getLastName());
            jTextFieldFirstName.setText(card.getFirstName());
            jTextFieldReference.setText(String.valueOf(card.getReference()));
            //jTextFieldCardNumber.setText(card.getCardNumber());

            Date date = card.getBirthDate();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String strDate= formatter.format(date);
            jTextFieldBirthDate.setText(strDate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            jTextFieldBirthDate.setText("null");
            e.printStackTrace();
        }

        textBoxPanel.setVisible(true);

        cardPanel.repaint();
    }
    private void buttonActionPerformed(ActionEvent evt) {
        if (cardChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String selectedCard = cardChooser.getSelectedFile().getAbsolutePath();
                textField.setText(selectedCard);
                BufferedImage bufferedImage = ImageIO.read(new File(selectedCard));
                cardPanel.setBufferedImage(bufferedImage);
                cardPanel.repaint();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}