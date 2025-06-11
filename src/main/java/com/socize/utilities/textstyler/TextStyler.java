package com.socize.utilities.textstyler;

import javafx.scene.control.Labeled;

public interface TextStyler {

    /**
     * Styles the {@code message} in a success theme, used to indicate a successful operation.
     * 
     * @param label the label to set the style on
     * @param message the message to display
     */
    void showSuccessMessage(Labeled label, String message);

    /**
     * Styles the {@code message} in an error theme, used to indicate that an error had occured.
     * 
     * @param label the label to set the style on
     * @param message the message to display
     */
    void showErrorMessage(Labeled label, String message);
}