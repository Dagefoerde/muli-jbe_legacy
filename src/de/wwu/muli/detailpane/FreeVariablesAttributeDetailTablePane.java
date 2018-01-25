package de.wwu.muli.detailpane;

import ee.ioc.cs.jbe.browser.BrowserServices;
import ee.ioc.cs.jbe.browser.ConstantPoolHyperlinkListener;
import ee.ioc.cs.jbe.browser.detail.attributes.AbstractAttributeListDetailPane;
import ee.ioc.cs.jbe.browser.detail.attributes.AbstractAttributeTableModel;
import org.gjt.jclasslib.structures.AttributeInfo;
import de.wwu.muli.attributes.FreeVariablesAttribute;
import de.wwu.muli.attributes.FreeVariablesEntry;

public class FreeVariablesAttributeDetailTablePane extends AbstractAttributeListDetailPane {
    /**
     * Constructor.
     *
     * @param services the associated browser services.
     */
    public FreeVariablesAttributeDetailTablePane(BrowserServices services) {
        super(services);
    }

    protected AbstractAttributeTableModel createTableModel(AttributeInfo attribute) {
        return createTableModel(attribute, "descriptor");
    }

    protected AbstractAttributeTableModel createTableModel(AttributeInfo attribute, String descriptorOrSignatureDescription) {
        return new FreeVariablesAttributeDetailTablePane.AttributeTableModel(attribute, descriptorOrSignatureDescription);
    }

    protected float getRowHeightFactor() {
        return 2f;
    }

    private class AttributeTableModel extends AbstractAttributeTableModel {
        private static final int COLUMN_COUNT = BASE_COLUMN_COUNT + 2;

        private static final int NAME_COLUMN_INDEX = BASE_COLUMN_COUNT;
        private static final int INDEX_COLUMN_INDEX = BASE_COLUMN_COUNT + 1;

        private static final int COMMENT_LINK_COLUMN_WIDTH = 200;

        private FreeVariablesEntry[] freeVariablesEntries;

        private AttributeTableModel(AttributeInfo attribute,
                                    String descriptorOrSignatureVerbose) {

            super(attribute);
            freeVariablesEntries = ((FreeVariablesAttribute)attribute).getFreeVariableEntries();
        }

        public int getColumnWidth(int column) {
            switch (column) {
                case INDEX_COLUMN_INDEX:
                    return NUMBER_COLUMN_WIDTH;

                case NAME_COLUMN_INDEX:
                    return COMMENT_LINK_COLUMN_WIDTH;

                default:
                    return LINK_COLUMN_WIDTH;
            }
        }

        public void link(int row, int column) {
            int constantPoolIndex;
            switch (column) {
                case NAME_COLUMN_INDEX:
                    constantPoolIndex = freeVariablesEntries[row].getNameIndex();
                    break;
                default:
                    return;
            }
            ConstantPoolHyperlinkListener.link(services, constantPoolIndex);
        }

        public int getRowCount() {
            return freeVariablesEntries.length;
        }

        public int getColumnCount() {
            return COLUMN_COUNT;
        }

        protected String doGetColumnName(int column) {
            switch (column) {
                case INDEX_COLUMN_INDEX:
                    return "index";
                case NAME_COLUMN_INDEX:
                    return "name";
                default:
                    return "";
            }
        }

        protected Class doGetColumnClass(int column) {
            switch (column) {
                case INDEX_COLUMN_INDEX:
                    return Number.class;
                case NAME_COLUMN_INDEX:
                    return Link.class;
                default:
                    return String.class;
            }
        }

        protected Object doGetValueAt(int row, int column) {
            FreeVariablesEntry localVariableEntry = freeVariablesEntries[row];

            switch (column) {
                case INDEX_COLUMN_INDEX:
                    return String.valueOf(localVariableEntry.getIndex());
                case NAME_COLUMN_INDEX:
                    return createCommentLink(localVariableEntry.getNameIndex());
                default:
                    return "";
            }
        }
    }

}
