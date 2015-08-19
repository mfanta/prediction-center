package cz.mfanta.tip_centrum.view.listeners;

import javax.swing.*;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SelectingFocusListener extends FocusAdapter {

    @Override
    public void focusGained(FocusEvent e) {
        super.focusGained(e);
        final Component component = e.getComponent();
        if (!(component instanceof JTextField)) {
            throw new IllegalArgumentException("Component must be a JTextField or its ancestor");
        }
        final JTextField textField = (JTextField) component;
        textField.selectAll();
    }
}
