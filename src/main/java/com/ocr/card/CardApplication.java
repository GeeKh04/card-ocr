package com.ocr.card;

import com.ocr.card.controller.CardOCRController;
import com.ocr.card.ui.CardFrame;

public class CardApplication {

	public static void main(String[] args) {
		new CardFrame();
		//new CardChooser().getPath();
		CardOCRController obj = new CardOCRController();
		System.out.println("Testing 1 - Send Http GET request");
		obj.uploadCardTransferFile();
	}
}