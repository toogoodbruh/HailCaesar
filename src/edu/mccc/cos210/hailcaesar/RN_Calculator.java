package edu.mccc.cos210.hailcaesar;

import java.awt.EventQueue;

import edu.mccc.cos210.hailcaesar.calc.RN_Calc;
import edu.mccc.cos210.hailcaesar.ui.RN_Calc_UI;
import edu.mccc.cos210.hailcaesar.ui.RN_Calc_UI_3;

public class RN_Calculator {
	public static void main(String[] args) {
		RN_Calc rnc = new RN_Calc();
		EventQueue.invokeLater(() -> new RN_Calc_UI(rnc));
	}
}
