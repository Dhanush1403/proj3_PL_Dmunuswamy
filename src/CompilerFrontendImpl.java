public class CompilerFrontendImpl extends CompilerFrontend {
    public CompilerFrontendImpl() {
        super();
    }

    public CompilerFrontendImpl(boolean debug_) {
        super(debug_);
    }

    @Override
    protected void init_lexer() {
        lex = new LexerImpl();

        // NUM: [0-9]*\.[0-9]+
        Automaton num = new AutomatonImpl();
        num.addState(0, true, false);
        num.addState(1, false, false);
        num.addState(2, false, true);
        for (char c = '0'; c <= '9'; c++) {
            num.addTransition(0, c, 0);
        }
        num.addTransition(0, '.', 1);
        for (char c = '0'; c <= '9'; c++) {
            num.addTransition(1, c, 2);
            num.addTransition(2, c, 2);
        }
        lex.add_automaton(TokenType.NUM, num);

        // PLUS: \+
        Automaton plus = new AutomatonImpl();
        plus.addState(0, true, false);
        plus.addState(1, false, true);
        plus.addTransition(0, '+', 1);
        lex.add_automaton(TokenType.PLUS, plus);

        // MINUS: -
        Automaton minus = new AutomatonImpl();
        minus.addState(0, true, false);
        minus.addState(1, false, true);
        minus.addTransition(0, '-', 1);
        lex.add_automaton(TokenType.MINUS, minus);

        // TIMES: \*
        Automaton times = new AutomatonImpl();
        times.addState(0, true, false);
        times.addState(1, false, true);
        times.addTransition(0, '*', 1);
        lex.add_automaton(TokenType.TIMES, times);

        // DIV: /
        Automaton div = new AutomatonImpl();
        div.addState(0, true, false);
        div.addState(1, false, true);
        div.addTransition(0, '/', 1);
        lex.add_automaton(TokenType.DIV, div);

        // LPAREN: \(
        Automaton lparen = new AutomatonImpl();
        lparen.addState(0, true, false);
        lparen.addState(1, false, true);
        lparen.addTransition(0, '(', 1);
        lex.add_automaton(TokenType.LPAREN, lparen);

        // RPAREN: \)
        Automaton rparen = new AutomatonImpl();
        rparen.addState(0, true, false);
        rparen.addState(1, false, true);
        rparen.addTransition(0, ')', 1);
        lex.add_automaton(TokenType.RPAREN, rparen);

        // WHITE_SPACE: [ \n\r\t]*
        Automaton ws = new AutomatonImpl();
        ws.addState(0, true, true);
        for (char c : new char[]{' ', '\n', '\r', '\t'}) {
            ws.addTransition(0, c, 0);
        }
        lex.add_automaton(TokenType.WHITE_SPACE, ws);
    }
}
