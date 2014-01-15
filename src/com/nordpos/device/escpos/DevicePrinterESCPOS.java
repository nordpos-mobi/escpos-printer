//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.
package com.nordpos.device.escpos;

import com.nordpos.device.ticket.TicketPrinterException;
import com.nordpos.device.receiptprinter.DevicePrinter;
import com.nordpos.device.writter.Writter;

public class DevicePrinterESCPOS implements DevicePrinter {

    private final Writter m_CommOutputPrinter;
    private final Procedures printerProcedures;
    private final UnicodeTranslator m_trans;
    private final String m_sName;

    public DevicePrinterESCPOS(Writter CommOutputPrinter, Procedures printerProcedures, UnicodeTranslator trans) throws TicketPrinterException {

        m_sName = "Printer.Serial";
        m_CommOutputPrinter = CommOutputPrinter;
        m_trans = trans;
        this.printerProcedures = printerProcedures;

        m_CommOutputPrinter.init(printerProcedures.getInitSequence());
        m_CommOutputPrinter.write(printerProcedures.getSelectPrinter());
//        m_CommOutputPrinter.init(m_codes.getInitSequence());
//        m_CommOutputPrinter.write(m_trans.getCodeTable());

        m_CommOutputPrinter.flush();
    }

    @Override
    public String getPrinterName() {
        return m_sName;
    }

    @Override
    public String getPrinterDescription() {
        return null;
    }


    @Override
    public void reset() {
    }

    @Override
    public void beginReceipt() {
    }

    @Override
    public void beginLine(int iTextSize) {

        m_CommOutputPrinter.write(printerProcedures.getSelectPrinter());

        if (iTextSize == DevicePrinter.SIZE_0) {
            m_CommOutputPrinter.write(printerProcedures.getSize0());
        } else if (iTextSize == DevicePrinter.SIZE_1) {
            m_CommOutputPrinter.write(printerProcedures.getSize1());
        } else if (iTextSize == DevicePrinter.SIZE_2) {
            m_CommOutputPrinter.write(printerProcedures.getSize2());
        } else if (iTextSize == DevicePrinter.SIZE_3) {
            m_CommOutputPrinter.write(printerProcedures.getSize3());
        } else {
            m_CommOutputPrinter.write(printerProcedures.getSize0());
        }
    }

    @Override
    public void printText(int iStyle, String sText) {

        m_CommOutputPrinter.write(printerProcedures.getSelectPrinter());

        if ((iStyle & DevicePrinter.STYLE_BOLD) != 0) {
            m_CommOutputPrinter.write(printerProcedures.getBoldSet());
        }
        if ((iStyle & DevicePrinter.STYLE_UNDERLINE) != 0) {
            m_CommOutputPrinter.write(printerProcedures.getUnderlineSet());
        }
        m_CommOutputPrinter.write(m_trans.transString(sText));
        if ((iStyle & DevicePrinter.STYLE_UNDERLINE) != 0) {
            m_CommOutputPrinter.write(printerProcedures.getUnderlineReset());
        }
        if ((iStyle & DevicePrinter.STYLE_BOLD) != 0) {
            m_CommOutputPrinter.write(printerProcedures.getBoldReset());
        }
    }

    @Override
    public void endLine() {
        m_CommOutputPrinter.write(printerProcedures.getSelectPrinter());
        m_CommOutputPrinter.write(printerProcedures.getNewLine());
    }

    @Override
    public void endReceipt() {
        m_CommOutputPrinter.write(printerProcedures.getSelectPrinter());

        m_CommOutputPrinter.write(printerProcedures.getNewLine());
        m_CommOutputPrinter.write(printerProcedures.getNewLine());
        m_CommOutputPrinter.write(printerProcedures.getNewLine());
        m_CommOutputPrinter.write(printerProcedures.getNewLine());
        m_CommOutputPrinter.write(printerProcedures.getNewLine());

        m_CommOutputPrinter.write(printerProcedures.getCutReceipt());
        m_CommOutputPrinter.flush();
    }

}
