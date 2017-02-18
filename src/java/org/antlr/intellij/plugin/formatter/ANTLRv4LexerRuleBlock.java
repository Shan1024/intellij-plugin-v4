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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ANTLRv4LexerRuleBlock extends AbstractBlock {

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

    protected ANTLRv4LexerRuleBlock(@NotNull ASTNode node, @Nullable Alignment alignment, @Nullable Indent indent,
                                    @Nullable
                                            Wrap wrap, @NotNull CodeStyleSettings settings, SpacingBuilder
                                            spacingBuilder) {
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
        ASTNode child = myNode.getFirstChildNode().getFirstChildNode();
        IElementType parentElementType = myNode.getElementType();

        while (child != null) {
            IElementType childElementType = child.getElementType();
            if (childElementType != TokenType.WHITE_SPACE) {
                Indent indent = Indent.getNoneIndent();

                indent = Indent.getSpaceIndent(4);
                //                }
                Block block = new ANTLRv4Block(
                        child, Alignment.createAlignment(), indent, null, mySettings, spacingBuilder
                );
                blocks.add(block);
            }
            child = child.getTreeNext();
        }
        return blocks;
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
