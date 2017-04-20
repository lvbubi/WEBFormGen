/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webformgen;

import java.sql.Date;

/**
 *
 * @author lvbubi
 */

public class PDFDatas implements  org.primefaces.model.SelectableDataModel  {
        int id,SzemelyID;
        Date Datum;
        String Hova;
        float OsszkoltsegFT;
        String DokumentumTipusa;
        boolean Ellenorizve;
        public int getId() {
            return id;
        }

        public int getSzemelyID() {
            return SzemelyID;
        }

        public Date getDatum() {
            return Datum;
        }

        public String getHova() {
            return Hova;
        }

        public float getOsszkoltsegFT() {
            return OsszkoltsegFT;
        }

        public String getDokumentumTipusa() {
            return DokumentumTipusa;
        }

        public boolean isEllenorizve() {
            return Ellenorizve;
        }

        public PDFDatas(int id, int SzemelyID, Date Datum, String Hova, float OsszkoltsegFT, String DokumentumTipusa, boolean ellenorizve) {
            this.id = id;
            this.SzemelyID = SzemelyID;
            this.Datum = Datum;
            this.Hova = Hova;
            this.OsszkoltsegFT = OsszkoltsegFT;
            this.DokumentumTipusa = DokumentumTipusa;
            this.Ellenorizve = ellenorizve;
        }

    @Override
    public Object getRowKey(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getRowData(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    }