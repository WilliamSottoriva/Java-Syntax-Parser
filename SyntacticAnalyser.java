import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyntacticAnalyser {

	public static Deque<Pair<Symbol, TreeNode>> stack = new ArrayDeque<Pair<Symbol, TreeNode>>();
	
	public static ParseTree parse(List<Token> tokens) throws SyntaxException {
		
		Map<Pair<TreeNode.Label,Token.TokenType>, Integer> parsingTable = new HashMap<>();
		
		parsingTable.put(new Pair<>(TreeNode.Label.prog, Token.TokenType.PUBLIC), 1);

		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.RBRACE), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.SEMICOLON), 2);
		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.WHILE), 2);
		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.IF), 2);
		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.FOR), 2);
		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.TYPE), 2);
		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.TYPE), 2);
		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.TYPE), 2);
		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.ID), 2); 
		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.PRINT), 2); 

		parsingTable.put(new Pair<>(TreeNode.Label.stat, Token.TokenType.SEMICOLON), 10);
		parsingTable.put(new Pair<>(TreeNode.Label.stat, Token.TokenType.WHILE), 4);
		parsingTable.put(new Pair<>(TreeNode.Label.stat, Token.TokenType.IF), 6);
		parsingTable.put(new Pair<>(TreeNode.Label.stat, Token.TokenType.FOR), 5);
		parsingTable.put(new Pair<>(TreeNode.Label.stat, Token.TokenType.TYPE), 8);
		parsingTable.put(new Pair<>(TreeNode.Label.stat, Token.TokenType.TYPE), 8);
		parsingTable.put(new Pair<>(TreeNode.Label.stat, Token.TokenType.TYPE), 8);
		parsingTable.put(new Pair<>(TreeNode.Label.stat, Token.TokenType.ID), 2);
		parsingTable.put(new Pair<>(TreeNode.Label.stat, Token.TokenType.PRINT), 9); 

		parsingTable.put(new Pair<>(TreeNode.Label.whilestat, Token.TokenType.WHILE), 11);

		parsingTable.put(new Pair<>(TreeNode.Label.forstat, Token.TokenType.FOR), 12);

		parsingTable.put(new Pair<>(TreeNode.Label.forstart, Token.TokenType.SEMICOLON), 15);
		parsingTable.put(new Pair<>(TreeNode.Label.forstart, Token.TokenType.TYPE), 13);
		parsingTable.put(new Pair<>(TreeNode.Label.forstart, Token.TokenType.TYPE), 13);
		parsingTable.put(new Pair<>(TreeNode.Label.forstart, Token.TokenType.TYPE), 13);
		parsingTable.put(new Pair<>(TreeNode.Label.forstart, Token.TokenType.ID), 14);

		parsingTable.put(new Pair<>(TreeNode.Label.forarith, Token.TokenType.ID), 16);
		parsingTable.put(new Pair<>(TreeNode.Label.forarith, Token.TokenType.RPAREN), 17);
		parsingTable.put(new Pair<>(TreeNode.Label.forarith, Token.TokenType.LPAREN), 16);
		parsingTable.put(new Pair<>(TreeNode.Label.forarith, Token.TokenType.NUM), 16);

		parsingTable.put(new Pair<>(TreeNode.Label.ifstat, Token.TokenType.IF), 18);

		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.RBRACE), 20);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.SEMICOLON), 20);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.WHILE), 20);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.IF), 20);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.FOR), 20);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.TYPE), 20);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.TYPE), 20);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.TYPE), 20);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.ID), 20);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.PRINT), 20);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.ELSE), 19);

		parsingTable.put(new Pair<>(TreeNode.Label.elseorelseif, Token.TokenType.ELSE), 21);

		parsingTable.put(new Pair<>(TreeNode.Label.possif, Token.TokenType.IF), 22);
		parsingTable.put(new Pair<>(TreeNode.Label.possif, Token.TokenType.LBRACE), 23);

		parsingTable.put(new Pair<>(TreeNode.Label.assign, Token.TokenType.ID), 24);

		parsingTable.put(new Pair<>(TreeNode.Label.possassign, Token.TokenType.SEMICOLON), 27);
		parsingTable.put(new Pair<>(TreeNode.Label.possassign, Token.TokenType.ASSIGN), 26);

		parsingTable.put(new Pair<>(TreeNode.Label.print, Token.TokenType.PRINT), 28);

		parsingTable.put(new Pair<>(TreeNode.Label.type, Token.TokenType.TYPE), 30);
		parsingTable.put(new Pair<>(TreeNode.Label.type, Token.TokenType.TYPE), 31);
		parsingTable.put(new Pair<>(TreeNode.Label.type, Token.TokenType.TYPE), 29);

		parsingTable.put(new Pair<>(TreeNode.Label.charexpr, Token.TokenType.SQUOTE), 34);

		parsingTable.put(new Pair<>(TreeNode.Label.boolexpr, Token.TokenType.SEMICOLON), 36);
		parsingTable.put(new Pair<>(TreeNode.Label.boolexpr, Token.TokenType.RPAREN), 36);
		parsingTable.put(new Pair<>(TreeNode.Label.boolexpr, Token.TokenType.EQUAL), 35);
		parsingTable.put(new Pair<>(TreeNode.Label.boolexpr, Token.TokenType.NEQUAL), 35);
		parsingTable.put(new Pair<>(TreeNode.Label.boolexpr, Token.TokenType.AND), 35);
		parsingTable.put(new Pair<>(TreeNode.Label.boolexpr, Token.TokenType.OR), 35);

		parsingTable.put(new Pair<>(TreeNode.Label.booleq, Token.TokenType.EQUAL), 39);
		parsingTable.put(new Pair<>(TreeNode.Label.booleq, Token.TokenType.NEQUAL), 40);

		parsingTable.put(new Pair<>(TreeNode.Label.boollog, Token.TokenType.AND), 41);
		parsingTable.put(new Pair<>(TreeNode.Label.boollog, Token.TokenType.OR), 41);

		parsingTable.put(new Pair<>(TreeNode.Label.relexpr, Token.TokenType.ID), 43);
		parsingTable.put(new Pair<>(TreeNode.Label.relexpr, Token.TokenType.TRUE), 44);
		parsingTable.put(new Pair<>(TreeNode.Label.relexpr, Token.TokenType.FALSE), 45);
		parsingTable.put(new Pair<>(TreeNode.Label.relexpr, Token.TokenType.LBRACE), 43);
		parsingTable.put(new Pair<>(TreeNode.Label.relexpr, Token.TokenType.NUM), 43);

		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.SEMICOLON), 47);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.TYPE), 47);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.TYPE), 47);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.ID), 47);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.RPAREN), 47);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.TRUE), 47);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.FALSE), 47);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.LPAREN), 47);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.NUM), 47);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.EQUAL), 47);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.NEQUAL), 47);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.AND), 47);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.OR), 47);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.TYPE), 47);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.LT), 46);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.LE), 46);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.GT), 46);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.GE), 46);

		parsingTable.put(new Pair<>(TreeNode.Label.relop, Token.TokenType.LT), 48);
		parsingTable.put(new Pair<>(TreeNode.Label.relop, Token.TokenType.LE), 49);
		parsingTable.put(new Pair<>(TreeNode.Label.relop, Token.TokenType.GT), 50);
		parsingTable.put(new Pair<>(TreeNode.Label.relop, Token.TokenType.GE), 51);

		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.SEMICOLON), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.TYPE), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.TYPE), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.TYPE), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.ID), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.RPAREN), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.TRUE), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.FALSE), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.LPAREN), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.NUM), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.EQUAL), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.NEQUAL), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.AND), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.OR), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.LT), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.LE), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.GT), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.GE), 55);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.PLUS), 53);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.MINUS), 54);

		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.SEMICOLON), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.TYPE), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.TYPE), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.TYPE), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.ID), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.RPAREN), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.TRUE), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.FALSE), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.LPAREN), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.NUM), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.EQUAL), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.NEQUAL), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.AND), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.OR), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.LT), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.LE), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.GT), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.GE), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.PLUS), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.MINUS), 60);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.TIMES), 57);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.DIVIDE), 58);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.MOD), 59);

		parsingTable.put(new Pair<>(TreeNode.Label.factor, Token.TokenType.ID), 62);
		parsingTable.put(new Pair<>(TreeNode.Label.factor, Token.TokenType.LPAREN), 61);
		parsingTable.put(new Pair<>(TreeNode.Label.factor, Token.TokenType.NUM), 63);

		parsingTable.put(new Pair<>(TreeNode.Label.printexpr, Token.TokenType.ID), 64);
		parsingTable.put(new Pair<>(TreeNode.Label.printexpr, Token.TokenType.TRUE), 64);
		parsingTable.put(new Pair<>(TreeNode.Label.printexpr, Token.TokenType.FALSE), 64);
		parsingTable.put(new Pair<>(TreeNode.Label.printexpr, Token.TokenType.LPAREN), 64);
		parsingTable.put(new Pair<>(TreeNode.Label.printexpr, Token.TokenType.NUM), 64);
		parsingTable.put(new Pair<>(TreeNode.Label.printexpr, Token.TokenType.DQUOTE), 65);

		parsingTable.put(new Pair<>(TreeNode.Label.decl, Token.TokenType.TYPE), 25);
		parsingTable.put(new Pair<>(TreeNode.Label.decl, Token.TokenType.TYPE), 25);
		parsingTable.put(new Pair<>(TreeNode.Label.decl, Token.TokenType.TYPE), 25);

		parsingTable.put(new Pair<>(TreeNode.Label.boolop, Token.TokenType.NEQUAL), 37);
		parsingTable.put(new Pair<>(TreeNode.Label.boolop, Token.TokenType.EQUAL), 37);
		parsingTable.put(new Pair<>(TreeNode.Label.boolop, Token.TokenType.AND), 38);
		parsingTable.put(new Pair<>(TreeNode.Label.boolop, Token.TokenType.OR), 38);

		parsingTable.put(new Pair<>(TreeNode.Label.term, Token.TokenType.ID), 56);
		parsingTable.put(new Pair<>(TreeNode.Label.term, Token.TokenType.LPAREN), 56);
		parsingTable.put(new Pair<>(TreeNode.Label.term, Token.TokenType.NUM), 56);

		parsingTable.put(new Pair<>(TreeNode.Label.arithexpr, Token.TokenType.ID), 52);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexpr, Token.TokenType.LPAREN), 52);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexpr, Token.TokenType.NUM), 52);

		parsingTable.put(new Pair<>(TreeNode.Label.expr, Token.TokenType.ID), 32);
		parsingTable.put(new Pair<>(TreeNode.Label.expr, Token.TokenType.TRUE), 32);
		parsingTable.put(new Pair<>(TreeNode.Label.expr, Token.TokenType.FALSE), 32);
		parsingTable.put(new Pair<>(TreeNode.Label.expr, Token.TokenType.LPAREN), 33);
		parsingTable.put(new Pair<>(TreeNode.Label.expr, Token.TokenType.NUM), 32);
		parsingTable.put(new Pair<>(TreeNode.Label.expr, Token.TokenType.SQUOTE), 33);
		
		stack.push(new Pair<>(TreeNode.Label.prog, null));
		int tokenIndex = 0;
		ParseTree pt = new ParseTree();

		if (tokens.size() == 0) {throw new SyntaxException("Syntax Error");}

		while (!stack.isEmpty() && tokenIndex < tokens.size()) {
			Pair p = stack.pop();
			if (p.fst() == tokens.get(tokenIndex).getType()) { 
				((TreeNode)p.snd()).addChild(new TreeNode(TreeNode.Label.terminal, tokens.get(tokenIndex), (TreeNode) p.snd()));
				tokenIndex += 1;
			}
			else {
				if (parsingTable.get(new Pair<>(p.fst(), tokens.get(tokenIndex).getType())) != null) {
					int ruleNo = parsingTable.get(new Pair<>(p.fst(), tokens.get(tokenIndex).getType())); 
					if (p.snd() != null) {
						TreeNode newPsnd = new TreeNode((TreeNode.Label) p.fst(), tokens.get(tokenIndex), (TreeNode) p.snd());
						addToStack(ruleNo, newPsnd);
						((TreeNode)p.snd()).addChild(newPsnd);
					}
					else {
						pt.setRoot(new TreeNode(TreeNode.Label.prog, tokens.get(tokenIndex), null));
						addToStack(ruleNo, pt.getRoot());
					}
				}
				else if (p.fst() == TreeNode.Label.epsilon) { 
					TreeNode newPsnd = new TreeNode((TreeNode.Label) p.fst(), tokens.get(tokenIndex), (TreeNode) p.snd());
					((TreeNode)p.snd()).addChild(newPsnd);
				}
				else {
					throw new SyntaxException("Syntax Error"); 
				}
			}

		}
		return pt;
	}

	public static void addToStack (int rule, TreeNode t) {
		switch (rule) {
			case 1:
				stack.push(new Pair<>(Token.TokenType.RBRACE, t));
				stack.push(new Pair<>(Token.TokenType.RBRACE, t));
				stack.push(new Pair<>(TreeNode.Label.los, t));
				stack.push(new Pair<>(Token.TokenType.LBRACE, t));
				stack.push(new Pair<>(Token.TokenType.RPAREN, t));
				stack.push(new Pair<>(Token.TokenType.ARGS, t));
				stack.push(new Pair<>(Token.TokenType.STRINGARR, t));
				stack.push(new Pair<>(Token.TokenType.LPAREN, t));
				stack.push(new Pair<>(Token.TokenType.MAIN, t));
				stack.push(new Pair<>(Token.TokenType.VOID, t));
				stack.push(new Pair<>(Token.TokenType.STATIC, t));
				stack.push(new Pair<>(Token.TokenType.PUBLIC, t));
				stack.push(new Pair<>(Token.TokenType.LBRACE, t));
				stack.push(new Pair<>(Token.TokenType.ID, t));
				stack.push(new Pair<>(Token.TokenType.CLASS, t));
				stack.push(new Pair<>(Token.TokenType.PUBLIC, t));
				break; 
			case 2:
				stack.push(new Pair<>(TreeNode.Label.los, t));
				stack.push(new Pair<>(TreeNode.Label.stat, t));
				break; 
			case 3:
				stack.push(new Pair<>(TreeNode.Label.epsilon, t));
				break;
			case 4:
				stack.push(new Pair<>(TreeNode.Label.whilestat, t));
				break; 
			case 5:
				stack.push(new Pair<>(TreeNode.Label.forstat, t));
				break; 
			case 6:
				stack.push(new Pair<>(TreeNode.Label.ifstat, t));
				break; 
			case 7:
				stack.push(new Pair<>(Token.TokenType.SEMICOLON, t));
				stack.push(new Pair<>(TreeNode.Label.assign, t));
				break; 
			case 8:
				stack.push(new Pair<>(Token.TokenType.SEMICOLON, t));
				stack.push(new Pair<>(TreeNode.Label.decl, t));
				break; 
			case 9:
				stack.push(new Pair<>(Token.TokenType.SEMICOLON, t));
				stack.push(new Pair<>(TreeNode.Label.print, t));
				break; 
			case 10:
				stack.push(new Pair<>(Token.TokenType.SEMICOLON, t));
				break;
			case 11:
				stack.push(new Pair<>(Token.TokenType.RBRACE, t));
				stack.push(new Pair<>(TreeNode.Label.los, t));
				stack.push(new Pair<>(Token.TokenType.LBRACE, t));
				stack.push(new Pair<>(Token.TokenType.RPAREN, t));
				stack.push(new Pair<>(TreeNode.Label.boolexpr, t));
				stack.push(new Pair<>(TreeNode.Label.relexpr, t));
				stack.push(new Pair<>(Token.TokenType.LPAREN, t));
				stack.push(new Pair<>(Token.TokenType.WHILE, t));
				break; 
			case 12:
				stack.push(new Pair<>(Token.TokenType.RBRACE, t));
				stack.push(new Pair<>(TreeNode.Label.los, t));
				stack.push(new Pair<>(Token.TokenType.LBRACE, t));
				stack.push(new Pair<>(Token.TokenType.RPAREN, t));
				stack.push(new Pair<>(TreeNode.Label.forarith, t));
				stack.push(new Pair<>(Token.TokenType.SEMICOLON, t));
				stack.push(new Pair<>(TreeNode.Label.boolexpr, t));
				stack.push(new Pair<>(TreeNode.Label.relexpr, t));
				stack.push(new Pair<>(Token.TokenType.SEMICOLON, t));
				stack.push(new Pair<>(TreeNode.Label.forstart, t));
				stack.push(new Pair<>(Token.TokenType.LPAREN, t));
				stack.push(new Pair<>(Token.TokenType.FOR, t));
				break; 
			case 13:
				stack.push(new Pair<>(TreeNode.Label.decl, t));
				break; 
			case 14:
				stack.push(new Pair<>(TreeNode.Label.assign, t));
				break;
			case 15:
				stack.push(new Pair<>(TreeNode.Label.epsilon, t));
				break; 
			case 16:
				stack.push(new Pair<>(TreeNode.Label.arithexpr, t));
				break; 
			case 17:
				stack.push(new Pair<>(TreeNode.Label.epsilon, t));
				break;
			case 18:
				stack.push(new Pair<>(TreeNode.Label.elseifstat, t));
				stack.push(new Pair<>(Token.TokenType.RBRACE, t));
				stack.push(new Pair<>(TreeNode.Label.los, t));
				stack.push(new Pair<>(Token.TokenType.LBRACE, t));
				stack.push(new Pair<>(Token.TokenType.RPAREN, t));
				stack.push(new Pair<>(TreeNode.Label.boolexpr, t));
				stack.push(new Pair<>(TreeNode.Label.relexpr, t));
				stack.push(new Pair<>(Token.TokenType.LPAREN, t));
				stack.push(new Pair<>(Token.TokenType.IF, t));
				break; 
			case 19:
				stack.push(new Pair<>(TreeNode.Label.elseifstat, t));
				stack.push(new Pair<>(Token.TokenType.RBRACE, t));
				stack.push(new Pair<>(TreeNode.Label.los, t));
				stack.push(new Pair<>(Token.TokenType.LBRACE, t));
				stack.push(new Pair<>(TreeNode.Label.elseorelseif, t));
				break; 
			case 20:
				stack.push(new Pair<>(TreeNode.Label.epsilon, t));
				break; 
			case 21:
				stack.push(new Pair<>(TreeNode.Label.possif, t));
				stack.push(new Pair<>(Token.TokenType.ELSE, t));
				break;
			case 22:
				stack.push(new Pair<>(Token.TokenType.RPAREN, t));
				stack.push(new Pair<>(TreeNode.Label.boolexpr, t));
				stack.push(new Pair<>(TreeNode.Label.relexpr, t));
				stack.push(new Pair<>(Token.TokenType.LPAREN, t));
				stack.push(new Pair<>(Token.TokenType.IF, t));
				break; 
			case 23:
				stack.push(new Pair<>(TreeNode.Label.epsilon, t));
				break; 
			case 24:
				stack.push(new Pair<>(TreeNode.Label.expr, t));
				stack.push(new Pair<>(Token.TokenType.EQUAL, t));
				stack.push(new Pair<>(Token.TokenType.ID, t));
				break;
			case 25:
				stack.push(new Pair<>(TreeNode.Label.possassign, t));
				stack.push(new Pair<>(Token.TokenType.ID, t));
				stack.push(new Pair<>(TreeNode.Label.type, t));
				break; 
			case 26:
				stack.push(new Pair<>(TreeNode.Label.expr, t));
				stack.push(new Pair<>(Token.TokenType.ASSIGN, t));
				break; 
			case 27:
				stack.push(new Pair<>(TreeNode.Label.epsilon, t));
				break; 
			case 28:
				stack.push(new Pair<>(Token.TokenType.RPAREN, t));
				stack.push(new Pair<>(TreeNode.Label.printexpr, t));
				stack.push(new Pair<>(Token.TokenType.LPAREN, t));
				stack.push(new Pair<>(Token.TokenType.PRINT, t));
				break;
			case 29:
				stack.push(new Pair<>(Token.TokenType.TYPE, t));
				break; 
			case 30:
				stack.push(new Pair<>(Token.TokenType.TYPE, t)); 
				break; 
			case 31:
				stack.push(new Pair<>(Token.TokenType.TYPE, t)); 
				break;
			case 32:
				stack.push(new Pair<>(TreeNode.Label.boolexpr, t));
				stack.push(new Pair<>(TreeNode.Label.relexpr, t));
				break; 
			case 33:
				stack.push(new Pair<>(TreeNode.Label.charexpr, t));
				break; 
			case 34:
				stack.push(new Pair<>(Token.TokenType.SQUOTE, t));
				stack.push(new Pair<>(Token.TokenType.CHARLIT, t));
				stack.push(new Pair<>(Token.TokenType.SQUOTE, t));
				break; 
			case 35:
				stack.push(new Pair<>(TreeNode.Label.boolexpr, t));
				stack.push(new Pair<>(TreeNode.Label.relexpr, t));
				stack.push(new Pair<>(TreeNode.Label.boolop, t));
				break;
			case 36:
				stack.push(new Pair<>(TreeNode.Label.epsilon, t));
				break; 
			case 37:
				stack.push(new Pair<>(TreeNode.Label.booleq, t));
				break; 
			case 38:
				stack.push(new Pair<>(TreeNode.Label.boollog, t));
				break;
			case 39:
				stack.push(new Pair<>(Token.TokenType.EQUAL, t));
				break; 
			case 40:
				stack.push(new Pair<>(Token.TokenType.NEQUAL, t));
				break; 
			case 41:
				stack.push(new Pair<>(Token.TokenType.AND, t));
				break; 
			case 42:
				stack.push(new Pair<>(Token.TokenType.OR, t));
				break;
			case 43:
				stack.push(new Pair<>(TreeNode.Label.relexprprime, t));
				stack.push(new Pair<>(TreeNode.Label.arithexpr, t));
				break; 
			case 44:
				stack.push(new Pair<>(Token.TokenType.TRUE, t));
				break; 
			case 45:
				stack.push(new Pair<>(Token.TokenType.FALSE, t));
				break;
			case 46:
				stack.push(new Pair<>(TreeNode.Label.arithexpr, t));
				stack.push(new Pair<>(TreeNode.Label.relop, t));
				break; 
			case 47:
				stack.push(new Pair<>(TreeNode.Label.epsilon, t));
				break; 
			case 48:
				stack.push(new Pair<>(Token.TokenType.LT, t));
				break; 
			case 49:
				stack.push(new Pair<>(Token.TokenType.LE, t));
				break;
			case 50:
				stack.push(new Pair<>(Token.TokenType.GT, t));
				break; 
			case 51:
				stack.push(new Pair<>(Token.TokenType.GE, t));
				break; 
			case 52:
				stack.push(new Pair<>(TreeNode.Label.arithexprprime, t));
				stack.push(new Pair<>(TreeNode.Label.term, t));
				
				break;
			case 53:
				stack.push(new Pair<>(TreeNode.Label.arithexprprime, t));
				stack.push(new Pair<>(TreeNode.Label.term, t));
				stack.push(new Pair<>(Token.TokenType.PLUS, t));
				break; 
			case 54:
				stack.push(new Pair<>(TreeNode.Label.arithexprprime, t));
				stack.push(new Pair<>(TreeNode.Label.term, t));
				stack.push(new Pair<>(Token.TokenType.MINUS, t));
				break; 
			case 55:
				stack.push(new Pair<>(TreeNode.Label.epsilon, t));
				break; 
			case 56:
				stack.push(new Pair<>(TreeNode.Label.termprime, t));
				stack.push(new Pair<>(TreeNode.Label.factor, t));
				break;
			case 57:
				stack.push(new Pair<>(TreeNode.Label.termprime, t));
				stack.push(new Pair<>(TreeNode.Label.factor, t));
				stack.push(new Pair<>(Token.TokenType.TIMES, t));
				break; 
			case 58:
				stack.push(new Pair<>(TreeNode.Label.termprime, t));
				stack.push(new Pair<>(TreeNode.Label.factor, t));
				stack.push(new Pair<>(Token.TokenType.DIVIDE, t));
				break; 
			case 59:
				stack.push(new Pair<>(TreeNode.Label.termprime, t));
				stack.push(new Pair<>(TreeNode.Label.factor, t));
				stack.push(new Pair<>(Token.TokenType.MOD, t));
				break;
			case 60:
				stack.push(new Pair<>(TreeNode.Label.epsilon, t));
				break; 
			case 61:
				stack.push(new Pair<>(Token.TokenType.RPAREN, t));
				stack.push(new Pair<>(TreeNode.Label.arithexpr, t));
				stack.push(new Pair<>(Token.TokenType.LPAREN, t));
				break; 
			case 62:
				stack.push(new Pair<>(Token.TokenType.ID, t));
				break; 
			case 63:
				stack.push(new Pair<>(Token.TokenType.NUM, t));
				break;
			case 64: 
				stack.push(new Pair<>(TreeNode.Label.boolexpr, t));
				stack.push(new Pair<>(TreeNode.Label.relexpr, t));
				break;
			case 65: 
				stack.push(new Pair<>(Token.TokenType.DQUOTE, t));
				stack.push(new Pair<>(Token.TokenType.STRINGLIT, t));
				stack.push(new Pair<>(Token.TokenType.DQUOTE, t));
				break;
		}

	}

}

class Pair<A, B> {
	private final A a;
	private final B b;

	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}

	public A fst() {
		return a;
	}

	public B snd() {
		return b;
	}

	@Override
	public int hashCode() {
		return 3 * a.hashCode() + 7 * b.hashCode();
	}

	@Override
	public String toString() {
		return "{" + a + ", " + b + "}";
	}

	@Override
	public boolean equals(Object o) {
		if ((o instanceof Pair<?, ?>)) {
			Pair<?, ?> other = (Pair<?, ?>) o;
			return other.fst().equals(a) && other.snd().equals(b);
		}

		return false;
	}

}
