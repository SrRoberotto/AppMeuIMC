package com.example.meuimc2;

public class ItemModel {
    private  int imcID;
    private  int imcImage;
    private  double userPeso;
    private  double userIMC;
    private  String sampleDate;

    ItemModel(int imcID, int imcImage, double userPeso, double userIMC, String sampleDate){
        this.imcID=imcID;
        this.imcImage=imcImage;
        this.userPeso=userPeso;
        this.userIMC=userIMC;
        this.sampleDate=sampleDate;
    }

    public int getImcImage() {
        return imcImage;
    }

    public String getUserPesoStr() {
        return Double.toString(userPeso);
    }
    public double getUserPeso() { return userPeso; }

    public String getUserIMCStr() {  return Double.toString(userIMC);  }
    public double getUserIMC() { return userIMC; }

    public String getSampleDateStr() {
        return sampleDate;
    }

    public int getUserId() {
        return imcID;
    }
    public String getUserIdStr() {
        return Integer.toString(imcID);
    }
}
