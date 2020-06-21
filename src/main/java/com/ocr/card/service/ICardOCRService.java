package com.ocr.card.service;

import com.ocr.card.model.Card;

import java.awt.image.BufferedImage;

public interface ICardOCRService {

    public Card getCardData(BufferedImage file);
}
