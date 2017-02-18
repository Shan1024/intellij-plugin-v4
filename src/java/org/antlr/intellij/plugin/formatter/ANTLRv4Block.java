package org.antlr.intellij.plugin.formatter;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.formatting.Wrap;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.plugin.ANTLRv4Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static org.antlr.intellij.plugin.ANTLRv4TokenTypes.*;

public class ANTLRv4Block extends AbstractBlock {

    @NotNull
    private final ASTNode myNode;
    @Nullable
    private final Alignment myAlignment;
    @Nullable
    private final Indent myIndent;
    @Nullable
    private final Wrap myWrap;
    @NotNull
    private final CodeStyleSettings mySettings;
    @NotNull
    private final SpacingBuilder spacingBuilder;
    @Nullable
    private List<Block> mySubBlocks;


    protected ANTLRv4Block(@NotNull ASTNode node, @Nullable Alignment alignment, @Nullable Indent indent, @Nullable
            Wrap wrap, @NotNull CodeStyleSettings settings, SpacingBuilder spacingBuilder) {
        super(node, wrap, alignment);

        this.myNode = node;
        this.myAlignment = alignment;
        this.myIndent = indent;
        this.myWrap = wrap;
        this.mySettings = settings;
        this.spacingBuilder = spacingBuilder;
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> blocks = new ArrayList<Block>();
        ASTNode child = myNode.getFirstChildNode();
        IElementType parentElementType = myNode.getElementType();

        while (child != null) {
            IElementType childElementType = child.getElementType();
            if (childElementType != TokenType.WHITE_SPACE) {
                Indent indent = Indent.getNoneIndent();
                if (childElementType == LEXER_RULE_BLOCK) {
                    //
                    //                    ASTNode blockChild = child.getFirstChildNode().getFirstChildNode();
                    //
                    //                    //                    Block block = new ANTLRv4Block(
                    //                    //                            blockChild, Alignment.createAlignment(),
                    // null, null,
                    //                    //                            mySettings, spacingBuilder
                    //                    //                    );
                    //                    //                    blocks.add(block);
                    //                    while (blockChild != null) {
                    //                        IElementType blockChildElementType = blockChild
                    //                                .getElementType();
                    //                        if (blockChildElementType != TokenType.WHITE_SPACE) {
                    //
                    //                            Indent blockIndent = Indent.getNoneIndent();
                    //
                    //                            if (blockChildElementType == OR) {
                    //                                blockIndent = Indent.getSpaceIndent(4);
                    //                            }
                    //                            Block block = new ANTLRv4Block(
                    //                                    blockChild, Alignment.createAlignment(),
                    //                                    null, null,
                    //                                    mySettings, spacingBuilder
                    //                            );
                    //                            blocks.add(block);
                    //                        }
                    //                        blockChild = blockChild.getTreeNext();
                    //                    }
                    Block block = new ANTLRv4LexerRuleBlock(
                            child, null, null, null, mySettings, createSpaceBuilder(mySettings)
                    );
                    blocks.add(block);
                } else {
                    if (parentElementType == LEXER_RULE) {
                        if (childElementType == COLON || childElementType == SEMI) {
                            indent = Indent.getSpaceIndent(4);
                        }
                    }
                    if (childElementType == ID_LIST) {
                        indent = Indent.getSpaceIndent(4);
                    }
                    Block block = new ANTLRv4Block(
                            child, Alignment.createAlignment(), indent, null, mySettings, spacingBuilder
                    );
                    blocks.add(block);
                }
            }
            child = child.getTreeNext();
        }
        return blocks;
    }

    private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
        return new SpacingBuilder(settings, ANTLRv4Language.INSTANCE)
                //                .after(GRAMMAR_TYPE).spaceIf(true)
                //                .withinPairInside(ID, SEMI, GRAMMAR_SPEC).spaceIf(false)
                //                .before(TOKEN_REF).spaceIf(false)
                //                .after(TOKEN_REF).lineBreakInCode()
                //                .after(LEXER_RULE_BLOCK).lineBreakInCode()
                //                .beforeInside(SEMI, LEXER_RULE).spaces(4)
                //                .between(COLON, LEXER_RULE_BLOCK).spaces(4)
                //                .between(COLON, LEXER_ALT_LIST).spaces(4)
                //                //                .between(OR, LEXER_RULE_BLOCK).spaces(4)
                //                //                .between(OR, LEXER_ALT_LIST).spaces(4)


                //                .before(LPAREN).lineBreakInCode()
                //                .before(RPAREN).lineBreakInCode()
                //                .before(OR).lineBreakInCode()
                //                .between(OR, LEXER_ALT).spaces(4)


                .around(LEXER_ELEMENT).spaceIf(true)
                .around(LEXER_ALT_LIST).spaceIf(false)
                .around(LEXER_COMMAND_EXPRESSION).spaceIf(false)
                .after(LEXER_COMMAND_NAME).spaceIf(false)
                .aroundInside(OR, LEXER_ALT_LIST).spaceIf(true)
                .before(LEXER_ALT).spaces(4)
                //                .beforeInside(OR, LEXER_ALT_LIST).lineBreakInCode()

                //                .around(OR).spaceIf(true)
                //                .around(LEXER_ELEMENT).spaceIf(true)
                //
                //                .beforeInside(OR, LEXER_RULE_BLOCK).spaces(0)
                //
                //                .before(RULES).blankLines(1)
                //                .after(RULES).blankLines(1)
                //
                //                .before(RULE_SPEC).blankLines(1)
                //                .after(RULE_SPEC).blankLines(1)

                ;
    }

    @Override
    public Indent getIndent() {
        return myIndent;
    }

    @Nullable
    @Override
    public Alignment getAlignment() {
        return myAlignment;
    }

    @Nullable
    @Override
    public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
        return spacingBuilder.getSpacing(this, child1, child2);
    }

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }
}
