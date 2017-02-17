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
                .between(OR, LEXER_RULE_BLOCK).spaces(4)
                .between(OR, LEXER_ALT_LIST).spaces(4)
                .between(OR, LEXER_ALT).spaces(4)
                .around(LEXER_ELEMENT).spaceIf(true)

                .beforeInside(OR, LEXER_RULE_BLOCK).spaces(0)

                .before(RULES).blankLines(1)
                .after(RULES).blankLines(1)

                .before(RULE_SPEC).blankLines(1)
                .after(RULE_SPEC).blankLines(1)


                //                .before(ALL).spaceIf(false)
                //                .after(ALL).spaceIf(true)
                //                .before(ANY).spaceIf(false)
                //                .after(ANY).spaceIf(true)
                //                .around(AS).spaceIf(true)
                //                .around(BREAK).spaceIf(false)
                //                .around(CATCH).spaceIf(true)
                //                .after(CONNECTOR).spaceIf(true)
                //                .after(CONST).spaceIf(true)
                //                .after(CREATE).spaceIf(true)
                //                .around(ELSE).spaceIf(true)
                //                .after(FORK).spaceIf(true)
                //                .after(FUNCTION).spaceIf(true)
                //                .after(IF).spaceIf(true)
                //                .after(IMPORT).spaceIf(true)
                //                .after(ITERATE).spaceIf(true)
                //                .around(JOIN).spaceIf(true)
                //                .after(PACKAGE).spaceIf(true)
                //                .after(REPLY).spaceIf(true)
                //                .after(RESOURCE).spaceIf(true)
                //                .after(SERVICE).spaceIf(true)
                //                .after(STRUCT).spaceIf(true)
                //                .after(THROW).spaceIf(true)
                //                .around(THROWS).spaceIf(true)
                //                .around(TIMEOUT).spaceIf(true)
                //                .after(TRY).spaceIf(true)
                //                .after(TYPEMAPPER).spaceIf(true)
                //                .after(WHILE).spaceIf(true)
                //                .after(WORKER).spaceIf(true)
                //                .before(SEMI).spaceIf(false)
                //                .around(DOT).spaceIf(false)
                //                .between(LPAREN, RPAREN).spaceIf(false)
                //                .between(RPAREN, LBRACE).spaceIf(true)
                //                .around(COLON).spaceIf(false)
                //                .before(ARGUMENT_LIST).spaceIf(false)
                //                .around(PARAMETER_LIST).spaceIf(false)
                //                .around(RETURN_TYPE_LIST).spaceIf(false)
                //                .around(TYPE_MAPPER_INPUT).spaceIf(false)
                //                .between(TYPE_MAPPER_TYPE, IDENTIFIER).spaceIf(true)
                //                .before(COMMA).spaceIf(false)
                //                .after(COMMA).spaceIf(true)
                //                .between(IDENTIFIER, LPAREN).spaceIf(true)
                //                .between(TYPE_NAME, IDENTIFIER).spaceIf(true)
                //                .around(LBRACK).spaceIf(false)
                //                .around(RBRACK).spaceIf(false)
                //                .after(LBRACE).spaceIf(false)
                //                .before(LBRACE).spaceIf(true)
                //                .around(RBRACE).spaceIf(false)
                //                .before(RPAREN).spaceIf(false)
                //                .after(LPAREN).spaceIf(false)
                //                .between(RPAREN, LPAREN).spaceIf(true)
                //                .after(AT).spaceIf(false)
                //                .after(ANNOTATION_NAME).spaceIf(true)
                //                .after(ANNOTATION).spaceIf(true)
                //                .around(EXPRESSION).spaceIf(false)
                //                .before(RETURN_PARAMETERS).spaceIf(true)
                //                .around(SENDARROW).spaceIf(true)
                //                .around(RECEIVEARROW).spaceIf(true)
                //                .before(TILDE).spaceIf(false)
                //                .between(RETURN, EXPRESSION_LIST).spaceIf(true)
                ;
    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}
