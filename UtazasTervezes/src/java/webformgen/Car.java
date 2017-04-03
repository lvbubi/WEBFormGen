/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webformgen;

/**
 *
 * @author lvbubi
 */
public class Car {
    private String rdsz;
    private String gyarto;
    private String tipus;
    private String uzemanyag;
    private int henger;

    public Car(String rdsz, String gyarto, String tipus, String uzemanyag, int henger) {
        this.rdsz = rdsz;
        this.gyarto = gyarto;
        this.tipus = tipus;
        this.uzemanyag = uzemanyag;
        this.henger = henger;
    }

    public String getRdsz() {
        return rdsz;
    }

    public String getGyarto() {
        return gyarto;
    }

    public String getTipus() {
        return tipus;
    }

    public String getUzemanyag() {
        return uzemanyag;
    }

    public int getHenger() {
        return henger;
    }

    public Car(){
        
    }
    
    
}
