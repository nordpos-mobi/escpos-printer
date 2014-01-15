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

import com.nordpos.device.receiptprinter.DevicePrinter;
import com.nordpos.device.receiptprinter.DevicePrinterNull;
import com.nordpos.device.ReceiptPrinterInterface;
import com.nordpos.device.writter.WritterFile;
import com.nordpos.device.writter.WritterRXTX;
import com.nordpos.device.util.SerialPortParameters;
import com.nordpos.device.util.StringParser;

/**
 *
 * @author Andrey Svininykh <svininykh@gmail.com>
 * @version NORD POS 3.0
 */
public class ReceiptPrinterDriver implements ReceiptPrinterInterface {

    @Override
    public DevicePrinter getReceiptPrinter(String sProperty) throws Exception {
        StringParser sp = new StringParser(sProperty);
        String sPrinterType = sp.nextToken(':');
        String sPrinterParam1 = sp.nextToken(',');
        String sPrinterParam2 = sp.nextToken(',');
        Integer iPrinterSerialPortSpeed;
        Integer iPrinterSerialPortDataBits;
        Integer iPrinterSerialPortStopBits;
        Integer iPrinterSerialPortParity;

        switch (sPrinterType) {
            case "epson":
                if ("rxtx".equals(sPrinterParam1) || "serial".equals(sPrinterParam1)) {
                    iPrinterSerialPortSpeed = SerialPortParameters.getSpeed(sp.nextToken(','));
                    iPrinterSerialPortDataBits = SerialPortParameters.getDataBits(sp.nextToken(','));
                    iPrinterSerialPortStopBits = SerialPortParameters.getStopBits(sp.nextToken(','));
                    iPrinterSerialPortParity = SerialPortParameters.getParity(sp.nextToken(','));
                    return new DevicePrinterESCPOS(new WritterRXTX(sPrinterParam2, iPrinterSerialPortSpeed, iPrinterSerialPortDataBits, iPrinterSerialPortStopBits, iPrinterSerialPortParity), new CommandsEpsonPrinter(), new UnicodeTranslatorInt());
                } else {
                    return new DevicePrinterESCPOS(new WritterFile(sPrinterParam2), new CommandsEpsonPrinter(), new UnicodeTranslatorInt());
                }
            default:
                return new DevicePrinterNull();
        }

    }
}
