package org.springgear.support.enums;

import lombok.NonNull;

/**
 * @author SHUAI.W
 * @date 2020/01/10
 **/
public enum SymbolEnum {

    DOT("."),
    COMMA(","),
    DASH("-"),
    PLUS("+"),
    SEMICOLON(";"),
    PARENTHESIS_LEFT("("),
    PARENTHESIS_RIGHT(")"),
    VERTICAL("|"),
    QUESTION("?"),
    COLON(":"),
    TILDE("~"),
    QUOTES_SINGLE("'"),
    QUOTES_DOUBLE("\""),
    AT("@"),
    DOLLAR("$"),
    FORWARD_SLASH("/"),
    BACK_SLASH("\\"),
    STAR("*"),
    HASH("#"),
    BLANK(" "),
    UNDER_SCORE("_");

    private final String symbol;

    SymbolEnum(String symbol) {
        this.symbol = symbol;
    }

    public String getString() {
        return symbol;
    }

    public char getChar() {
        return symbol.charAt(0);
    }

    /**
     * 被包含
     *
     * @param string
     * @return
     */
    public boolean beContained(String string) {
        if (string == null || string.trim().isEmpty()) {
            return false;
        }
        return string.contains(this.symbol);
    }

    /**
     * 字符串被开始于
     *
     * @param string
     * @return
     */
    public boolean beStartsWith(String string) {
        if (string == null || string.trim().isEmpty()) {
            return false;
        }
        return string.startsWith(this.symbol);
    }

    /**
     * 字符传被结束于
     *
     * @param string
     * @return
     */
    public boolean beEndsWith(String string) {
        if (string == null || string.trim().isEmpty()) {
            return false;
        }
        return string.endsWith(this.symbol);
    }

    /**
     * 使用字符包裹
     *
     * @param string
     * @return
     */
    public String wrap(String string) {
        return this.symbol + string + this.symbol;
    }
}
