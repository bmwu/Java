package com.bmwu.spring;

/**
 * Created by michael on 4/12/17.
 */
public class TextEditor {
    private SpellChecker spellChecker;
    private String name;

    public TextEditor(SpellChecker spellChecker, String name) {
        System.out.println("TextEditor constructor.");
        this.spellChecker = spellChecker;
        this.name = name;
    }

    public void checkSpelling() {
        this.spellChecker.checkSpelling();
    }

    public SpellChecker getSpellChecker() {
        return spellChecker;
    }

    public String getName() {
        return name;
    }
}
