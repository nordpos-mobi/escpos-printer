/**
 *
 * NORD POS is a fork of Openbravo POS.
 *
 * Copyright (C) 2009-2013 Nord Trading Ltd. <http://www.nordpos.com>
 *
 * This file is part of NORD POS.
 *
 * NORD POS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * NORD POS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * NORD POS. If not, see <http://www.gnu.org/licenses/>.
 */
package com.nordpos.device.escpos;

import com.nordpos.device.traslator.UnicodeTranslator;
import com.nordpos.device.ticket.TicketPrinterException;
import com.nordpos.device.receiptprinter.DevicePrinter;
import com.nordpos.device.writter.Writter;

/**
 *
 * @author Andrey Svininykh <svininykh@gmail.com>
 * @version NORD POS 3.0
 */
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
        m_CommOutputPrinter.write(m_trans.getCodeTable());

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
    public void beginLine(Integer iTextSize) {
        m_CommOutputPrinter.write(printerProcedures.getSelectPrinter());
        if (iTextSize != null) {
            m_CommOutputPrinter.write(printerProcedures.getSizeSet(iTextSize));
        }
    }

    @Override
    public void printText(Integer iCharacterSize, String sUnderlineType, Boolean bBold, String sText) {

        if (iCharacterSize != null) {
            m_CommOutputPrinter.write(printerProcedures.getSizeSet(iCharacterSize));
        }

        if (sUnderlineType != null) {
            m_CommOutputPrinter.write(printerProcedures.getUnderlineSet(sUnderlineType));
        }

        if (bBold) {
            m_CommOutputPrinter.write(printerProcedures.getBoldSet());
        } else {
            m_CommOutputPrinter.write(printerProcedures.getBoldReset());
        }

        m_CommOutputPrinter.write(m_trans.transString(sText));

        if (bBold) {
            m_CommOutputPrinter.write(printerProcedures.getBoldReset());
        }

        if (sUnderlineType != null) {
            m_CommOutputPrinter.write(printerProcedures.getUnderlineReset());
        }

        if (iCharacterSize != null) {
            m_CommOutputPrinter.write(printerProcedures.getSizeReset());
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
