package org.antlr.intellij.plugin.formatter.settings;

import com.intellij.application.options.CodeStyleAbstractConfigurable;
import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.application.options.TabbedLanguageCodeStylePanel;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.antlr.intellij.plugin.ANTLRv4Language;
import org.jetbrains.annotations.NotNull;

public class ANTLRv4CodeStyleConfigurable extends CodeStyleAbstractConfigurable {

    public ANTLRv4CodeStyleConfigurable(@NotNull CodeStyleSettings settings, CodeStyleSettings cloneSettings) {
        super(settings, cloneSettings, "ANTLRv4");
    }

    @NotNull
    @Override
    protected CodeStyleAbstractPanel createPanel(CodeStyleSettings settings) {
        return new ANTLRv4CodeStyleMainPanel(getCurrentSettings(), settings);
    }

    @Override
    public String getHelpTopic() {
        return null;
    }

    private static class ANTLRv4CodeStyleMainPanel extends TabbedLanguageCodeStylePanel {

        private ANTLRv4CodeStyleMainPanel(CodeStyleSettings currentSettings, CodeStyleSettings settings) {
            super(ANTLRv4Language.INSTANCE, currentSettings, settings);
        }

        @Override
        protected void addSpacesTab(CodeStyleSettings settings) {
        }

        @Override
        protected void addBlankLinesTab(CodeStyleSettings settings) {
        }

        @Override
        protected void addWrappingAndBracesTab(CodeStyleSettings settings) {
        }
    }
}
