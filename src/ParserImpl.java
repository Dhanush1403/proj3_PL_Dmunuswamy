public class ParserImpl extends Parser {

    @Override
    public Expr do_parse() throws Exception {
        Expr expr = parseT();
        if (tokens != null) {
            throw new Exception("Unexpected tokens remaining");
        }
        return expr;
    }

    private Expr parseT() throws Exception {
        Expr f = parseF();
        if (peekAddOp()) {
            Token op = consumeAddOp();
            Expr t = parseT();
            if (op.ty == TokenType.PLUS) {
                return new PlusExpr(f, t);
            } else {
                return new MinusExpr(f, t);
            }
        }
        return f;
    }

    private Expr parseF() throws Exception {
        Expr lit = parseLit();
        if (peekMulOp()) {
            Token op = consumeMulOp();
            Expr f = parseF();
            if (op.ty == TokenType.TIMES) {
                return new TimesExpr(lit, f);
            } else {
                return new DivExpr(lit, f);
            }
        }
        return lit;
    }

    private Expr parseLit() throws Exception {
        if (peek(TokenType.NUM, 0)) {
            Token num = consume(TokenType.NUM);
            return new FloatExpr(Float.parseFloat(num.lexeme));
        } else if (peek(TokenType.LPAREN, 0)) {
            consume(TokenType.LPAREN);
            Expr t = parseT();
            consume(TokenType.RPAREN);
            return t;
        } else {
            throw new Exception("Unexpected token: " + (tokens != null ? tokens.elem.ty : ""));
        }
    }

    private boolean peekAddOp() {
        return peek(TokenType.PLUS, 0) || peek(TokenType.MINUS, 0);
    }

    private boolean peekMulOp() {
        return peek(TokenType.TIMES, 0) || peek(TokenType.DIV, 0);
    }

    private Token consumeAddOp() throws Exception {
        if (peek(TokenType.PLUS, 0)) {
            return consume(TokenType.PLUS);
        } else if (peek(TokenType.MINUS, 0)) {
            return consume(TokenType.MINUS);
        } else {
            throw new Exception("Expected '+' or '-'");
        }
    }

    private Token consumeMulOp() throws Exception {
        if (peek(TokenType.TIMES, 0)) {
            return consume(TokenType.TIMES);
        } else if (peek(TokenType.DIV, 0)) {
            return consume(TokenType.DIV);
        } else {
            throw new Exception("Expected '*' or '/'");
        }
    }
}
