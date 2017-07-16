package com.bmwu.spring;

/**
 * Created by michael on 7/16/17.
 */
public class TextEditorProperty {

    private SpellChecker spellChecker;
    private String name;

    public SpellChecker getSpellChecker() {
        System.out.println("TextEditorProperty: getSpellChecker");
        return spellChecker;
    }

    public void setSpellChecker(SpellChecker spellChecker) {
        System.out.println("TextEditorProperty: Inside setSpellChecker." );
        this.spellChecker = spellChecker;
    }

    public void spellChecker() {
        System.out.println("spellChecker");
    }

    public void checkSpelling() {
        this.spellChecker.checkSpelling();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
