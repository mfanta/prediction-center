package cz.mfanta.tip_centrum.view.dialog;

import cz.mfanta.tip_centrum.view.handlers.IOkCancelHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancelDialogAction implements ActionListener {

    private IOkCancelHandler handler;

    public CancelDialogAction(IOkCancelHandler handler) {
        this.handler = handler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        handler.onCancel();
    }
}
