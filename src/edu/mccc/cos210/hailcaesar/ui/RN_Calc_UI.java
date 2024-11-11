
package edu.mccc.cos210.hailcaesar.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import edu.mccc.cos210.hailcaesar.calc.RN_Calc;

public class RN_Calc_UI {
	private final RN_Calc rnc;  
	JButton parLTBtn = new JButton("(");	
	JButton parRTBtn = new JButton(")");
	JButton IBtn = new JButton("I");
	JButton VBtn = new JButton("V");
	JButton XBtn = new JButton("X");
	JButton LBtn = new JButton("L");
	JButton CBtn = new JButton("C");
	JButton DBtn = new JButton("D");
	JButton MBtn = new JButton("M");
	JButton plusBtn = new JButton("+");
	JButton minBtn = new JButton("-");
	JButton multBtn = new JButton("*");
	JButton carrotBtn = new JButton("^");
	JButton dollarSnBtn = new JButton("$");
	JButton clearBtn = new JButton("liquet");
	JButton backspaceBtn = new JButton("\u2190");
	JButton equalBtn = new JButton(" = ");
	
	public RN_Calc_UI(RN_Calc rnc) {
		this.rnc = rnc;
		Font tnr = new Font("Times New Roman", Font.PLAIN, 16);
		JFrame jf = new JFrame("Ave Caesar");
		jf.setLayout(new BorderLayout(5, 5));
		jf.setFont(tnr);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel inputLabel = new JLabel();
		inputLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
		inputLabel.setFont(tnr);
		inputLabel.setAutoscrolls(true);
		JScrollPane jsp = new JScrollPane(inputLabel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.getViewport().setPreferredSize(new Dimension(205, 50));
		jsp.getHorizontalScrollBar().setUnitIncrement(70);
		JMenuBar options = new JMenuBar();
		JMenu helpMenu = new JMenu("Help Caesar");
		helpMenu.getAccessibleContext().setAccessibleDescription("Help menu ");
		options.add(helpMenu);
		helpMenu.addSeparator();
		JMenu aboutMenu = new JMenu("About Us");
		JMenuItem aboutItem = new JMenuItem("Project Team");
		ImageIcon ic = new ImageIcon("./popupFiles/Team_Hail_Caesar.png");
	    JScrollPane scrollPane = null;
		aboutItem.addActionListener(
				 ae -> {
					 JOptionPane.showMessageDialog(
							 jf, 
							 scrollPane, 
							 "Project Team", 
							 JOptionPane.INFORMATION_MESSAGE, 
							 ic
							 );
				 });
		aboutMenu.add(aboutItem);
		options.add(aboutMenu);
		JMenuItem romanMenu = new JMenuItem("Roman Numeral Rules");
		JScrollPane sp;
		ImageIcon ric = new ImageIcon("./popupFiles/RomanRules.png");
		JLabel lic = new JLabel(ric);
		sp = new JScrollPane(lic, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.getViewport().setPreferredSize(new Dimension(1109, 600));
		sp.getVerticalScrollBar().setUnitIncrement(40);
		aboutMenu.getAccessibleContext().setAccessibleDescription("A guide to Roman Numerals");
		romanMenu.addActionListener(
				ae -> {
					JOptionPane.showMessageDialog(
							jf, 
							sp,
							"Roman Numeral Rules",
							JOptionPane.PLAIN_MESSAGE
							);
				});
		helpMenu.add(romanMenu);							
		JMenuItem guideMenu = new JMenuItem("Guide");
		ImageIcon ic1 = new ImageIcon("./popupFiles/Guide.jpg");
		JLabel gl = new JLabel(ic1);
		JScrollPane si = new JScrollPane(gl, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		si.getViewport().setPreferredSize(new Dimension(861, 600));
		si.getVerticalScrollBar().setUnitIncrement(40);
		guideMenu.addActionListener(
				ae -> {
					JOptionPane.showMessageDialog(
							jf,
							si,
							"Guide",
							JOptionPane.PLAIN_MESSAGE
							);
				});
		helpMenu.add(guideMenu);
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(4,4));
		contentPanel.add(backspaceBtn);
		contentPanel.add(clearBtn);
		contentPanel.add(parLTBtn);
		contentPanel.add(parRTBtn);
		contentPanel.add(dollarSnBtn);
		contentPanel.add(MBtn);
		contentPanel.add(carrotBtn);
		contentPanel.add(multBtn);
		contentPanel.add(LBtn);
		contentPanel.add(CBtn);
		contentPanel.add(DBtn);
		contentPanel.add(plusBtn);
		contentPanel.add(IBtn);
		contentPanel.add(VBtn);
		contentPanel.add(XBtn);
		contentPanel.add(minBtn);
		Component [] com = contentPanel.getComponents();
		setTNR(com, tnr);
		JPanel calculatePanel = new JPanel();
		calculatePanel.setLayout(new BorderLayout());
		calculatePanel.add(equalBtn, BorderLayout.EAST);
		calculatePanel.add(contentPanel, BorderLayout.WEST);
		jf.add(options, BorderLayout.NORTH);
		jf.add(jsp, BorderLayout.CENTER);
		jf.add(calculatePanel,BorderLayout.SOUTH);
		jf.pack();
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		backspaceBtn.addActionListener(
				ae -> {
					display(inputLabel, "\u2190");
				});
		clearBtn.addActionListener(
				ae -> {
					display(inputLabel, "Clear");
				});
		dollarSnBtn.addActionListener(
				ae -> {
					display(inputLabel, "$");
				});
		carrotBtn.addActionListener(
				ae -> {
					display(inputLabel, "^");
				});
		multBtn.addActionListener(
				ae -> {
					display(inputLabel, "*");
				});
		parLTBtn.addActionListener(
				ae -> {
					display(inputLabel, "(");
				});
		parRTBtn.addActionListener(
				ae -> {
					display(inputLabel, ")");
				});
		MBtn.addActionListener(
				ae -> {
					display(inputLabel, "M");
				});
		minBtn.addActionListener(
				ae -> {
					display(inputLabel, "-");
				});
		LBtn.addActionListener(
				ae -> {
					display(inputLabel, "L");
				});
		CBtn.addActionListener(
				ae -> {
					display(inputLabel, "C");
				});
		DBtn.addActionListener(
				ae -> {
					display(inputLabel, "D");
				});
		plusBtn.addActionListener(
				ae -> {
					display(inputLabel, "+");
				});
		IBtn.addActionListener(
				ae -> {
					display(inputLabel, "I");
				});
		VBtn.addActionListener(
				ae -> {
					display(inputLabel, "V");
				});
		XBtn.addActionListener(
				ae -> {
					display(inputLabel, "X");
				});
		equalBtn.addActionListener(
				ae -> {
					display(inputLabel, "=");
				});			
	}
	private void setTNR(Component[] com, Font tnr) {
		for (Component c : com) {
			c.setFont(tnr);
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(
			() -> new RN_Calc_UI(new RN_Calc())
		);
	}
	private int leftP = 0;
	private int rightP = 0;
	private int lastNonNumeral = -1;
	private StringBuilder sb = new StringBuilder();
	private String expressionBuilder(String s) {
		switch (s) {
			case "\u2190":
				if (sb.length() > 0) {
					switch (sb.substring(sb.length() - 1)) {
						case "(":
							leftP--;
							break;
						case ")":
							rightP++;
							break;
					}
					sb.deleteCharAt(sb.length() - 1);
					findLastNonNumeral();
				}
				break;
			case "Clear":
				sb.delete(0, sb.length());
				leftP = 0;
				rightP = 0;
				lastNonNumeral = -1;
				break;
			case "(":
				leftP++;
				lastNonNumeral = sb.length();
				sb.append(s);
				break;
			case ")":
				rightP++;
				lastNonNumeral = sb.length();
				sb.append(s);
				break;
			default:
				sb.append(s);
				break;
		}
		if (isOperator(s)) {
			lastNonNumeral = sb.length() - 1;
		}
		return sb.toString();
	}
	private void display(JLabel l, String s) {
		if (isValid(s)) {
			if (s.contentEquals("=")) {
				String result = this.rnc.calc(sb.substring(0));
				expressionBuilder("Clear");
				l.setText(result);
			} else {
				expressionBuilder(s);
				l.setText(sb.substring(0));
			}
		}
	}
	private boolean isValid(String s) {
		if (sb.length() == 0) {
			if (isOperator(s) || s.equals(")") || s.equals("=")) {
				return false;
			}
			return true;
		}
		if (s.equals("\u2190")) {
			return true;
		}
		if (s.equals(")")) {
			return (leftP > rightP);
		}
		if (sb.substring(0).contains("$")) {
			if (s.equals("$")) {
				return false;
			}
		}
		String lastChar = sb.substring(sb.length() - 1);
		if (isOperator(lastChar) || lastChar.equals("(")) {
			if (isOperator(s) || s.equals(")")) {
				return false;
			}
			if (isNumeral(s)) {
				return true;
			}
		}
		if (isNumeral(lastChar)) {
			if (s.equals("$")) {
				return false;
			}
			if (isOperator(s) || s.equals(")")) {
				return true;
			}
		}
		if (lastChar.equals("$")) {
			if (isNumeral(s)) {
				return false;
			}
		}
		if (isNumeral(s)) {
			String currentNumeral = sb.append(s).substring(lastNonNumeral + 1);
			if (this.rnc.checkNumeralOrder(currentNumeral)) {
				sb.deleteCharAt(sb.length() - 1);
				return true;
			} else {
				sb.deleteCharAt(sb.length() - 1);
				return false;
			}
		}
		return true;
	}
	private boolean isOperator(String s) {
		return s.equals("^") || s.equals("*") || 
				s.equals("+") || s.equals("-");
	}
	private boolean isNumeral(String s) {
		return s.equals("I") || s.equals("V") || 
				s.equals("X") || s.equals("L") || 
				s.equals("C") || s.equals("D") ||
				s.equals("M");
	}
	private void findLastNonNumeral() {
		lastNonNumeral = -1;
		int i = sb.length() - 1;
		while (lastNonNumeral == -1) {
			switch (sb.charAt(i)) {
			case '^':
				lastNonNumeral = i;
				break;
			case '*':
				lastNonNumeral = i;
				break;
			case '+':
				lastNonNumeral = i;
				break;
			case '-':
				lastNonNumeral = i;
				break;
			case '(':
				lastNonNumeral = i;
				break;
			case ')':
				lastNonNumeral = i;
				break;
		}
			if (i == 0) {
				lastNonNumeral = -1;
				break;
			}
			i--;
		}
	}
}