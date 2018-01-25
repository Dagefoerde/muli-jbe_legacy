/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/
package de.wwu.muli.attributes;

import org.gjt.jclasslib.structures.AbstractStructure;
import org.gjt.jclasslib.structures.ClassFile;
import org.gjt.jclasslib.structures.InvalidByteCodeException;
import org.gjt.jclasslib.structures.attributes.LocalVariableTableEntry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Contains common attributes to a free variable table entry structure.
 *
 * @author <a href="mailto:jan.dagefoerde@ercis.uni-muenster.de">Jan C. Dageförde</a>
 */
public class FreeVariablesEntry extends AbstractStructure {
    /**
     * Length in bytes of a local variable association.
     */
    public static final int LENGTH = 4;

    protected int nameIndex;
    protected int index;

    /**
     * Get the index of the constant pool entry containing the name of this
     * local variable.
     *
     * @return the index
     */
    final public int getNameIndex() {
        return nameIndex;
    }

    /**
     * Set the index of the constant pool entry containing the name of this
     * local variable.
     *
     * @param nameIndex the index
     */
    final public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    /**
     * Get the index of this local variable.
     *
     * @return the index
     */
    final public int getIndex() {
        return index;
    }

    /**
     * Set the index of this local variable.
     * Set the index of this local variable.
     */
    final public void setIndex(int index) {
        this.index = index;
    }

    final public void read(DataInput in)
            throws InvalidByteCodeException, IOException {
        super.read(in);

        nameIndex = in.readUnsignedShort();
        index = in.readUnsignedShort();

        if (debug) debug("read ");
    }

    final public void write(DataOutput out)
            throws InvalidByteCodeException, IOException {
        super.write(out);

        out.writeShort(nameIndex);
        out.writeShort(index);

        if (debug) debug("wrote ");
    }

    protected String printAccessFlagsVerbose(int accessFlags) {
        if (accessFlags != 0)
            throw new RuntimeException("Access flags should be zero: " +
                    Integer.toHexString(accessFlags));
        return "";
    }

    /**
     * Factory method for creating <tt>LocalVariableTableEntry</tt> structures.
     *
     * @param in        the <tt>DataInput</tt> from which to read the
     *                  <tt>LocalVariableTableEntry</tt> structure
     * @param classFile the parent class file of the structure to be created
     * @return the new <tt>LocalVariableTableEntry</tt> structure
     * @throws InvalidByteCodeException if the byte code is invalid
     * @throws IOException              if an exception occurs with the <tt>DataInput</tt>
     */
    public static FreeVariablesEntry create(DataInput in, ClassFile classFile)
            throws InvalidByteCodeException, IOException {

        FreeVariablesEntry freeVariablesEntry = new FreeVariablesEntry();
        freeVariablesEntry.setClassFile(classFile);
        freeVariablesEntry.read(in);

        return freeVariablesEntry;
    }

}
