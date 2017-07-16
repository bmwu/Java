package com.bmwu.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by michael on 7/16/17.
 */
@Configuration
@Import(HelloWorldConfig.class)
public class TextEditorConfig {
    @Bean
    public TextEditor textEditor() {
        return new TextEditor(spellChecker(), "hello");
    }

    @Bean
    public SpellChecker spellChecker() {
        return new SpellChecker();
    }

    @Bean
    public HelloWorld helloWorld() {
        return new HelloWorld();
    }
}
