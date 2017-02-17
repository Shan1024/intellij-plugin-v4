package org.antlr.intellij.plugin.formatter.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider;
import org.jetbrains.annotations.NotNull;

public class ANTLRv4CodeStyleSettingsProvider extends CodeStyleSettingsProvider {

    @Override
    public String getConfigurableDisplayName() {
        return "ANTLRv4";
    }

    @NotNull
    @Override
    public Configurable createSettingsPage(@NotNull CodeStyleSettings settings, CodeStyleSettings originalSettings) {
        return new ANTLRv4CodeStyleConfigurable(settings, originalSettings);
    }
}
