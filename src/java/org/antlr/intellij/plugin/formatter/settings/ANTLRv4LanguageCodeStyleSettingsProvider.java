package org.antlr.intellij.plugin.formatter.settings;

import com.intellij.application.options.IndentOptionsEditor;
import com.intellij.application.options.SmartIndentOptionsEditor;
import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import org.antlr.intellij.plugin.ANTLRv4Language;
import org.jetbrains.annotations.NotNull;

public class ANTLRv4LanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {

    private static final String DEFAULT_CODE_SAMPLE =
            "package main;\n" +
                    "\n" +
                    "import ballerina.lang.system;\n" +
                    "\n" +
                    "function main(string[] args) {\n" +
                    "\tsystem:println(\"Hello\");\n" +
                    "}";

    @NotNull
    @Override
    public Language getLanguage() {
        return ANTLRv4Language.INSTANCE;
    }

    @NotNull
    @Override
    public String getCodeSample(@NotNull SettingsType settingsType) {
        return DEFAULT_CODE_SAMPLE;
    }

    @Override
    public IndentOptionsEditor getIndentOptionsEditor() {
        return new SmartIndentOptionsEditor();
    }

    @Override
    public CommonCodeStyleSettings getDefaultCommonSettings() {
        CommonCodeStyleSettings defaultSettings = new CommonCodeStyleSettings(getLanguage());
        CommonCodeStyleSettings.IndentOptions indentOptions = defaultSettings.initIndentOptions();
        indentOptions.INDENT_SIZE = 4;
        indentOptions.CONTINUATION_INDENT_SIZE = 4;
        indentOptions.TAB_SIZE = 4;
        indentOptions.USE_TAB_CHARACTER = false;

        defaultSettings.KEEP_BLANK_LINES_IN_CODE = 1;
        defaultSettings.KEEP_BLANK_LINES_IN_DECLARATIONS = 1;

        defaultSettings.BLOCK_COMMENT_AT_FIRST_COLUMN = false;
        defaultSettings.LINE_COMMENT_AT_FIRST_COLUMN = false;
        return defaultSettings;
    }
}
