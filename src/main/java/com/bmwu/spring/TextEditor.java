package com.bmwu.spring;

/**
 * Created by michael on 4/12/17.
 */
public class TextEditor {
    private SpellChecker spellChecker;
    public TextEditor(SpellChecker spellChecker) {
        System.out.println("TextEditor constructor.");
        this.spellChecker = spellChecker;
    }

    public void checkSpelling() {
        this.spellChecker.checkSpelling();
    }
}
