package org.antlr.intellij.plugin.formatter;

import com.intellij.formatting.FormattingModel;
import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.formatting.FormattingModelProvider;
import com.intellij.formatting.Indent;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.antlr.intellij.plugin.ANTLRv4Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.antlr.intellij.plugin.ANTLRv4TokenTypes.*;

public class ANTLRv4FormattingModelBuilder implements FormattingModelBuilder {

    @NotNull
    @Override
    public FormattingModel createModel(PsiElement element, CodeStyleSettings settings) {
        ANTLRv4Block rootBlock = new ANTLRv4Block(
                element.getNode(), null, Indent.getNoneIndent(), null, settings, createSpaceBuilder(settings)
        );
        return FormattingModelProvider.createFormattingModelForPsiFile(
                element.getContainingFile(), rootBlock, settings
        );
    }

    private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
        return new SpacingBuilder(settings, ANTLRv4Language.INSTANCE)
                .after(GRAMMAR_TYPE).spaceIf(true)
                .withinPairInside(ID, SEMI, GRAMMAR_SPEC).spaceIf(false)
                .before(TOKEN_REF).spaceIf(false)
                .after(TOKEN_REF).lineBreakInCode()
                .after(LEXER_RULE_BLOCK).lineBreakInCode()
                .beforeInside(SEMI, LEXER_RULE).spaces(4)
                .between(COLON, LEXER_RULE_BLOCK).spaces(4)
                .between(COLON, LEXER_ALT_LIST).spaces(4)
//                .between(OR, LEXER_RULE_BLOCK).spaces(4)
//                .between(OR, LEXER_ALT_LIST).spaces(4)
//                .between(OR, LEXER_ALT).spaces(4)
                .around(OR).spaceIf(true)
                .around(LEXER_ELEMENT).spaceIf(true)

                .beforeInside(OR, LEXER_RULE_BLOCK).spaces(0)

                .before(RULES).blankLines(1)
                .after(RULES).blankLines(1)

                .before(RULE_SPEC).blankLines(1)
                .after(RULE_SPEC).blankLines(1)

                ;
    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}
