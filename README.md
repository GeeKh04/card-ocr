# card-ocr

> Student ID  card reader

> scanner java tesseract ocr swing

[![Build Status](http://img.shields.io/travis/badges/badgerbadgerbadger.svg?style=flat-square)](https://travis-ci.org/badges/badgerbadgerbadger)
[![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org) 

## Preview
![Preview](http://g.recordit.co/lhL0938J07.gif)

---

## Table of Contents

- [Installation](#installation)
- [Features](#features)
- [Team](#team)
- [Support](#support)
- [License](#license)


---

## Installation

### Built With

* [IntelliJ IDEA](https://www.jetbrains.com/idea/promo/ultimate/) - IntelliJ IDEA v2019.2.4
* [Tesseract OCR](https://developer.android.com/studio/releases/gradle-plugin) - Tesseract OCR v4

### Clone

- Clone this repo to your local machine using `https://github.com/GeeKh04/card-ocr`

### Usage
 After the cloning of this repo you can test the app by flowing the instructions :
 - Get your ID card informations position by %
 - Go to the File `card-ocr/src/main/java/com/ocr/card/service/CardOCRService.java`
 - Update the x & y value by your text region position
 ```// Text Region
 double x1; // x1 coordinate of the upper-left corner
 double y1; // y1 coordinate of the downer-left corner
 double x2; // x2 coordinate of the upper-right corner
 double y2; // y2 coordinate of the downer-right corner
 ```
 -  Run CardApplication on IntelliJ IDEA or any other IDE
 - Choose your ID card
 - Click the Scan button
---

## Features
- Get a student ID card data

---

## Team

| <a href="http://www.khouna.com" target="_blank">**KHOUNA Rida**</a> |
| :---: |
| [![KHOUNA](https://avatars0.githubusercontent.com/u/11447240?s=200&u=b9b5600cdbeb616a4c8b4b07dc41195119862c5b&v=4)](http://www.khouna.com)    |
| <a href="http://github.com/GeeKh04" target="_blank">`github.com/GeeKh04`</a> |

---

## Support

Reach out to me at one of the following places!

- Website at <a href="http://www.khouna.com" target="_blank">`khouna.com`</a>
- Twitter at <a href="https://twitter.com/Rida_Khouna" target="_blank">`@Rida_Khouna`</a>

---

## License

[![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org)

- **[MIT license](http://opensource.org/licenses/mit-license.php)**
- Copyright 2020 © <a href="http://www.khouna.com" target="_blank">KHOUNA</a>.
