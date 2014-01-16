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

/**
 *
 * @author Andrey Svininykh <svininykh@gmail.com>
 * @version NORD POS 3.0
 */
public abstract class Procedures {

    public abstract byte[] getInitSequence();

    public abstract byte[] getSelectPrinter();

    public abstract byte[] getBoldSet();

    public abstract byte[] getBoldReset();

   public abstract byte[] getSizeSet(Integer iCharacterSize);

    public abstract byte[] getSizeReset();

    public abstract byte[] getUnderlineSet(String sUnderlineType);

    public abstract byte[] getUnderlineReset();

    public abstract byte[] getCutReceipt();

    public abstract byte[] getNewLine();
}
