package com.bmwu.spring;

/**
 * Created by michael on 7/16/17.
 */
public class TextEditorProperty {

    private SpellChecker spellChecker;

    public SpellChecker getSpellChecker() {
        System.out.println("TextEditorProperty: getSpellChecker");
        return spellChecker;
    }

    public void setSpellChecker(SpellChecker spellChecker) {
        System.out.println("TextEditorProperty: setSpellChecker");
        this.spellChecker = spellChecker;
    }

    public void spellChecker() {
        System.out.println("spellChecker");
    }

    public void checkSpelling() {
        this.spellChecker.checkSpelling();
    }
}
