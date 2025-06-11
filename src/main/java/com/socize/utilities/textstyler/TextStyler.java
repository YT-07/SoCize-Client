package com.socize.utilities.textstyler;

import javafx.scene.control.Labeled;

public interface TextStyler {

    void showSuccessMessage(Labeled label, String message);
    void showErrorMessage(Labeled label, String message);
}