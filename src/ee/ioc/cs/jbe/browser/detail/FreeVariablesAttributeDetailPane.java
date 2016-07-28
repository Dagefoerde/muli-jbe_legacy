package ee.ioc.cs.jbe.browser.detail;

import ee.ioc.cs.jbe.browser.BrowserServices;
import org.apache.bcel.classfile.Utility;
import org.gjt.jclasslib.structures.AttributeInfo;
import org.gjt.jclasslib.util.ExtendedJLabel;

import javax.swing.tree.TreePath;

public class FreeVariablesAttributeDetailPane extends FixedListDetailPane {
    private ExtendedJLabel lblContent;
    public FreeVariablesAttributeDetailPane(BrowserServices services) {
        super(services);
    }


    protected void setupLabels() {
        addDetailPaneEntry(normalLabel("Attribute bytes:"),
                lblContent = highlightLabel());
    }

    public void show(TreePath treePath) {
        AttributeInfo attribute = findAttribute(treePath);

        String content = Utility.toHexString(attribute.getInfo());

        lblContent.setText(content);

    }
}
