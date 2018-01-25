/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/
package de.wwu.muli.attributes;

import org.gjt.jclasslib.structures.AttributeInfo;
import org.gjt.jclasslib.structures.InvalidByteCodeException;
import org.gjt.jclasslib.structures.attributes.LocalVariableTableEntry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Contains common attributes to a free variable table attribute structure.
 *
 * @author <a href="mailto:jan.dagefoerde@ercis.uni-muenster.de">Jan C. Dageförde</a>
 */
public class FreeVariablesAttribute extends AttributeInfo {
    /**
     * Name of the attribute as in the corresponding constant pool entry.
     */
    public static final String ATTRIBUTE_NAME = "FreeVariables";

    protected static final int INITIAL_LENGTH = 2;

    protected FreeVariablesEntry[] freeVariableTable;

    /**
     * Get the list of local variable associations of the parent <tt>Code</tt>
     * structure as an array of <tt>LocalVariableCommonEntry</tt> structures.
     *
     * @return the array
     */
    public FreeVariablesEntry[] getFreeVariableEntries() {
        return freeVariableTable;
    }

    /**
     * Set the list of local variable associations of the parent <tt>Code</tt>
     * structure as an array of <tt>LocalVariableCommonEntry</tt> structures.
     *
     * @param freeVariableEntries the array
     */
    public void setFreeVariableEntries(FreeVariablesEntry[] freeVariableEntries) {
        this.freeVariableTable = freeVariableEntries;
    }

    public void write(DataOutput out)
            throws InvalidByteCodeException, IOException {

        super.write(out);

        int localVariableTableLength = getLength(freeVariableTable);
        out.writeShort(localVariableTableLength);
        for (int i = 0; i < localVariableTableLength; i++) {
            freeVariableTable[i].write(out);
        }

        if (debug) debug("wrote ");
    }

    public void read(DataInput in)
            throws InvalidByteCodeException, IOException {
        super.read(in);

        int localVariableTableLength = in.readUnsignedShort();
        freeVariableTable = new FreeVariablesEntry[localVariableTableLength];
        for (int i = 0; i < localVariableTableLength; i++) {
            freeVariableTable[i] = FreeVariablesEntry.create(in, classFile);
        }

        if (debug) debug("read ");
    }

    public int getAttributeLength() {
        return INITIAL_LENGTH + getLength(freeVariableTable) * FreeVariablesEntry.LENGTH;
    }


    protected void debug(String message) {
        super.debug(message + "LocalVariableTable attribute with " + getLength(freeVariableTable) + " entries");
    }
}
