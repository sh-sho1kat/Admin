package com.example.admin.userpart.ui.ebook;

public class EbookData {
    private String title,pdfurl;

    public EbookData(){

    }
    public EbookData(String title, String pdfurl) {
        this.title = title;
        this.pdfurl = pdfurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPdfurl() {
        return pdfurl;
    }

    public void setPdfurl(String pdfurl) {
        this.pdfurl = pdfurl;
    }


}
