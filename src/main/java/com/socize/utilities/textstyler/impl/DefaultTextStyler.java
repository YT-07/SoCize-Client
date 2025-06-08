package com.socize.utilities.textstyler.impl;

import com.socize.config.CssStyles;
import com.socize.utilities.textstyler.spi.TextStyler;

import javafx.scene.control.Labeled;

public class DefaultTextStyler implements TextStyler {

    private DefaultTextStyler() {}

    private static class SingletonInstanceHolder {
        private static final DefaultTextStyler INSTANCE = new DefaultTextStyler();
    }

    public static DefaultTextStyler getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public void showSuccessMessage(Labeled label, String message) {
        label.getStyleClass().clear();
        label.getStyleClass().add(CssStyles.SUCCESS_STYLE);

        label.setText(message);
    }

    @Override
    public void showErrorMessage(Labeled label, String message) {
        label.getStyleClass().clear();
        label.getStyleClass().add(CssStyles.ERROR_STYLE);

        label.setText(message);
    }
    
}
