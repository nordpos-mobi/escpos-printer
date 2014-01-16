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

public class CommandsEpsonPrinter extends Procedures {

    /**
     *
     * @author Andrey Svininykh <svininykh@gmail.com>
     * @version NORD POS 3.0
     */
    public CommandsEpsonPrinter() {

    }

    @Override
    public byte[] getInitSequence() {
        return new byte[]{0x1B, 0x40};
    }

    @Override
    public byte[] getSelectPrinter() {
        return new byte[]{0x1B, 0x3D, 0x01};
    }

    @Override
    public byte[] getBoldSet() {
        return new byte[]{0x1B, 0x45, 0x01};
    }

    @Override
    public byte[] getBoldReset() {
        return new byte[]{0x1B, 0x45, 0x00};
    }

    @Override
    public byte[] getUnderlineSet(String sUnderlineType) {
        switch (sUnderlineType) {
            case "slim":
                return new byte[]{0x1B, 0x2D, 0x01};
            case "average":
                return new byte[]{0x1B, 0x2D, 0x02};
            default:
                return new byte[]{0x1B, 0x2D, 0x00};
        }
    }

    @Override
    public byte[] getUnderlineReset() {
        return new byte[]{0x1B, 0x2D, 0x00};
    }

    @Override
    public byte[] getSizeSet(Integer iCharacterSize) {
        return new byte[]{0x1D, 0x21, iCharacterSize.byteValue()};
    }

    @Override
    public byte[] getSizeReset() {
        return new byte[]{0x1D, 0x21, 0x00};
    }

    @Override
    public byte[] getCutReceipt() {
        return new byte[]{0x1B, 0x69};
    }

    @Override
    public byte[] getNewLine() {
        return new byte[]{0x0D, 0x0A}; // CR LF
    }
}
