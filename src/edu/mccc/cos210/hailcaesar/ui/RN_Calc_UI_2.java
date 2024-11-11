
package edu.mccc.cos210.hailcaesar.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.Icon;
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
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import edu.mccc.cos210.hailcaesar.calc.RN_Calc;


public class RN_Calc_UI_2 {
	
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
	
	
	public RN_Calc_UI_2(RN_Calc rnc) {
		this.rnc = rnc;
		Font tnr = new Font("Times New Roman", Font.PLAIN, 16);
		JFrame jf = new JFrame("Computus Roman numeral");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Display
		JLabel inputLabel = new JLabel(" ");
		inputLabel.setPreferredSize(new Dimension(205, 50));
		inputLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
		inputLabel.setFont(tnr);
		JScrollPane jsp = new JScrollPane(inputLabel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.getViewport().setPreferredSize(new Dimension(205, 50));
		jsp.getHorizontalScrollBar().setUnitIncrement(70);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		 
		//Drop Down Menu 
		JMenuBar options = new JMenuBar();
		 
		JMenu helpMenu = new JMenu("Help Caesar");
		helpMenu.setFont(tnr);
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.getAccessibleContext().setAccessibleDescription("Help menu ");
		options.add(helpMenu);
		helpMenu.addSeparator();
		 	 
		JMenu aboutMenu = new JMenu("About Us");
		JMenuItem aboutItem = new JMenuItem("Project Team");
		ImageIcon ic = new ImageIcon("./popupFiles/CaesarTeam.png");
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
		 
		JMenuItem romanMenu = new JMenuItem("Roman Number");
		JTextArea textArea = new JTextArea(getRomanRules());
		JScrollPane sp;
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setOpaque(false);
		textArea.setFont(tnr);
		textArea.setWrapStyleWord(true);
		sp = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(500, 300));
		aboutMenu.setMnemonic(KeyEvent.VK_R);
		aboutMenu.getAccessibleContext().setAccessibleDescription("A guide to Roman Numerals");
		romanMenu.addActionListener(
				ae -> {
					JOptionPane.showMessageDialog(
							jf, 
							sp,
							"Roman Numerals",
							JOptionPane.PLAIN_MESSAGE
							);
				});
		helpMenu.add(romanMenu);
		 														
		JMenuItem guideMenu = new JMenuItem("Guide");
		ImageIcon ic1 = new ImageIcon("./popupFiles/Guide.jpg");
		JScrollPane si = null;
		guideMenu.addActionListener(
				ae -> {
					JOptionPane.showMessageDialog(
							jf,
							si,
							"Guide",
							JOptionPane.INFORMATION_MESSAGE,
							ic1
							);
				});
		helpMenu.add(guideMenu);
	
		//Buttons
		JPanel contentPanel =new JPanel();
		contentPanel.setLayout(new GridLayout(4,4));
		//First Row
		contentPanel.add(backspaceBtn);
		contentPanel.add(clearBtn);
		contentPanel.add(parLTBtn);
		contentPanel.add(parRTBtn);
		//Second Row
		contentPanel.add(dollarSnBtn);
		contentPanel.add(MBtn);
		contentPanel.add(carrotBtn);
		contentPanel.add(multBtn);
		//3rd Row
		contentPanel.add(LBtn);
		contentPanel.add(CBtn);
		contentPanel.add(DBtn);
		contentPanel.add(plusBtn);
		//4thRow
		contentPanel.add(IBtn);
		contentPanel.add(VBtn);
		contentPanel.add(XBtn);
		contentPanel.add(minBtn);
		Component [] com = contentPanel.getComponents();
		setTNR(com, tnr);
		
		JPanel calculatePane = new MainJPanel();
		calculatePane.setLayout(new BorderLayout());
		calculatePane.add(equalBtn, BorderLayout.EAST);
		calculatePane.add(contentPanel, BorderLayout.WEST);
		
		jf.add(options, BorderLayout.NORTH);
		//jf.add(inputLabel, BorderLayout.CENTER);
		jf.add(jsp, BorderLayout.CENTER);
		jf.add(calculatePane,BorderLayout.SOUTH);
		jf.pack();
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);		
	
		//1ST ROW
		backspaceBtn.addActionListener(
				ae -> {
					System.out.println("<-");
					display(inputLabel, "\u2190");
				});
		clearBtn.addActionListener(
				ae -> {
					System.out.println("Clear");
					display(inputLabel, "Clear");
				});
		dollarSnBtn.addActionListener(
				ae -> {
					System.out.println("$");
					display(inputLabel, "$");
				});
		carrotBtn.addActionListener(
				ae -> {
					System.out.println("^");
					display(inputLabel, "^");
				});
		multBtn.addActionListener(
				ae -> {
					System.out.println("*");
					display(inputLabel, "*");
				});
		//2ND ROW
		parLTBtn.addActionListener(
				ae -> {
					System.out.println("(");
					display(inputLabel, "(");
				});
		parRTBtn.addActionListener(
				ae -> {
					System.out.println(")");
					display(inputLabel, ")");
				});
		MBtn.addActionListener(
				ae -> {
					System.out.println("M");
					display(inputLabel, "M");
				});
		minBtn.addActionListener(
				ae -> {
					System.out.println("-");
					display(inputLabel, "-");
				});
		//3RD ROW
		LBtn.addActionListener(
				ae -> {
					System.out.println("L");
					display(inputLabel, "L");
				});
		CBtn.addActionListener(
				ae -> {
					System.out.println("C");
					display(inputLabel, "C");
				});
		DBtn.addActionListener(
				ae -> {
					System.out.println("D");
					display(inputLabel, "D");
				});
		plusBtn.addActionListener(
				ae -> {
					System.out.println("+");
					display(inputLabel, "+");
				});
		//4TH ROW
		IBtn.addActionListener(
				ae -> {
					System.out.println("I");
					display(inputLabel, "I");
				});
		VBtn.addActionListener(
				ae -> {
					System.out.println("V");
					display(inputLabel, "V");
				});
		XBtn.addActionListener(
				ae -> {
					System.out.println("X");
					display(inputLabel, "X");
				});
		equalBtn.addActionListener(
				ae -> {
					System.out.println("=");
					display(inputLabel, "=");
				});			
	}
	private String getTeamText() {
		StringBuilder sb = new StringBuilder("");
		try (
				BufferedReader br = new BufferedReader(new FileReader("./popupFiles/TeamText.txt"))
			) {
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			
		} catch (Exception e) {
			sb.append("Data not available.");
		}
		return sb.toString();
	}
	private String getRomanRules() {
		StringBuilder sb = new StringBuilder("");
		try (
				BufferedReader br = new BufferedReader(new FileReader("./popupFiles/RomanRules.txt"))
			) {
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			
		} catch (Exception e) {
			sb.append("Data not available.");
		}
		return sb.toString();
	}
	private void setTNR(Component[] com, Font tnr) {
		for (Component c : com) {
			c.setFont(tnr);
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(
			() -> new RN_Calc_UI_2(new RN_Calc())
		);
	}
	private int leftP = 0;
	private int rightP = 0;
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
				}
				break;
			case "Clear":
				sb.delete(0, sb.length());
				leftP = 0;
				rightP = 0;
				break;
			case "(":
				leftP++;
				sb.append(s);
				break;
			case ")":
				rightP++;
				sb.append(s);
				break;
			default:
				sb.append(s);
				break;
		}
		return sb.toString();
	}
	private boolean isValid(String s) {
		if (sb.length() == 0) {
			switch (s) {
				case "^":
					return false;
				case "*":
					return false;
				case "+":
					return false;
				case "-":
					return false;
				case ")":
					return false;
				case "=":
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
		if (lastChar.equals("^") || lastChar.equals("*") || lastChar.equals("+") 
				|| lastChar.equals("-") || lastChar.equals("(")) {
			switch (s) {
				case "^":
					return false;
				case "*":
					return false;
				case "+":
					return false;
				case "-":
					return false;
				case ")":
					return false;
				case "I":
					return true;
				case "V":
					return true;
				case "X":
					return true;
				case "L":
					return true;
				case "C":
					return true;
				case "D":
					return true;
				case "M":
					return true;
			}
		}
		if (lastChar.equals("I") || lastChar.equals("V") || lastChar.equals("X")
				|| lastChar.equals("L") || lastChar.equals("C") || lastChar.equals("D")
				|| lastChar.equals("M")) {
			switch (s) {
				case "$":
					return false;
				case "^":
					return true;
				case "*":
					return true;
				case "+":
					return true;
				case "-":
					return true;
				case ")":
					return true;
			}
		}
		if (lastChar.equals("$")) {
			switch (s) {
				case "I":
					return false;
				case "V":
					return false;
				case "X":
					return false;
				case "L":
					return false;
				case "C":
					return false;
				case "D":
					return false;
				case "M":
					return false;
			}
		}
		int lastOp = 0;
		int i = sb.length() - 1;
		while (lastOp == 0) {
			switch (sb.charAt(i)) {
				case '^':
					lastOp = i + 1;
					break;
				case '*':
					lastOp = i + 1;
					break;
				case '+':
					lastOp = i + 1;
					break;
				case '-':
					lastOp = i + 1;
					break;
			}
			if (i == 0) {
				break;
			}
			i--;
		}
		
		if (s.equals("I") || s.equals("V") || s.equals("X") 
				|| s.equals("L") || s.equals("C") || s.equals("D") ||
				s.equals("M")) {
			String currentNumeral = sb.append(s).substring(lastOp);
			if (this.rnc.checkNumeralOrder(currentNumeral)) {
			//if (!this.rnc.catchErrors(sb.append(s).substring(lastOp)).equals("Error")) {
				System.out.println(sb.substring(lastOp));
				sb.deleteCharAt(sb.length() - 1);
				return true;
			} else { 
				sb.deleteCharAt(sb.length() - 1);
				return false;
			}
		}
		return true;
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
	class MainJPanel extends JPanel { 
			private static final long serialVersionUID = 1L;
			public MainJPanel() {
				super();				
			 JFrame frame = new JFrame("Hail Ceasar"); 
			 frame.setLayout(new BorderLayout()); 
			 frame.setBackground(new Color(207,181,59));
			 frame.setSize(7000, 24000); 
			}
		}
}
