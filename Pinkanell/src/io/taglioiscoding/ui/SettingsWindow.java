package io.taglioiscoding.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import io.ghostyjade.pinkanell.PinkanellMain;
import io.ghostyjade.utils.Constants;
import io.ghostyjade.utils.Settings;

/**
 * This is the settings window. It allows to set some program constants, such as
 * language and the field dimension.
 * 
 * @author taglioIsCoding
 */
public class SettingsWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JLabel lblCm;
	private JLabel lblInsertTheField_1, lblInsertTheField;
	private JLabel lblCm_1;
	private JComboBox<String> comboWidth; // TODO change to JTextArea
	private JComboBox<String> comboHeight; // TODO change to JTextArea
	private JComboBox<String> comboLang;
	private JButton btnSave;
	private JTextField textFieldWidth;
	private JTextField textFieldHeigth;

	/**
	 * Create the frame.
	 */
	public SettingsWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblInsertTheField = new JLabel(PinkanellMain.getI18n().getTranslationString("ui.insertWidth"));
		lblInsertTheField.setBounds(6, 135, 175, 16);
		contentPane.add(lblInsertTheField);

		lblCm = new JLabel("Cm");
		lblCm.setBounds(303, 135, 61, 16);
		contentPane.add(lblCm);

		lblInsertTheField_1 = new JLabel(PinkanellMain.getI18n().getTranslationString("ui.insertHeight"));
		lblInsertTheField_1.setBounds(6, 169, 175, 16);
		contentPane.add(lblInsertTheField_1);

		lblCm_1 = new JLabel("Cm");
		lblCm_1.setBounds(303, 169, 61, 16);
		contentPane.add(lblCm_1);

		comboLang = new JComboBox<String>();
		comboLang.setModel(new DefaultComboBoxModel<String>(new String[] { "Italiano", "English" }));
		comboLang.setBounds(141, 46, 123, 27);
		contentPane.add(comboLang);

		comboWidth = new JComboBox<String>();
		comboWidth.setModel(new DefaultComboBoxModel<String>(new String[] { "10", "20", "30" }));
		comboWidth.setBounds(168, 131, 123, 27);
		contentPane.add(comboWidth);

		comboHeight = new JComboBox<String>();
		comboHeight.setModel(new DefaultComboBoxModel<String>(new String[] { "100", "110", "120", "130", "140" }));
		comboHeight.setBounds(168, 165, 123, 27);
		contentPane.add(comboHeight);

		btnSave = new JButton(PinkanellMain.getI18n().getTranslationString("ui.btnsave"));
		btnSave.setBounds(315, 225, 117, 29);
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setNewLocale();
				try {
					Settings.updateSettings(Constants.LOCALE_NAME,
							Integer.valueOf(comboWidth.getItemAt(comboWidth.getSelectedIndex())),
							Integer.valueOf(comboHeight.getItemAt(comboHeight.getSelectedIndex())));
				} catch (NumberFormatException ex) {
					System.err.println("The specified value is invalid.");
				}
				PinkanellMain.getI18n().loadLocale(Constants.LOCALE_NAME);
				reloadTexts();
			}
		});
		contentPane.add(btnSave);

		textFieldHeigth = new JTextField();
		textFieldHeigth.setBounds(193, 130, 98, 26);
		contentPane.add(textFieldHeigth);
		textFieldHeigth.setColumns(10);

		textFieldWidth = new JTextField();
		textFieldWidth.setBounds(193, 163, 98, 26);
		contentPane.add(textFieldWidth);
		textFieldWidth.setColumns(10);

		Constants.FIELD_HEIGHT = Integer.parseInt(comboHeight.getItemAt(comboHeight.getSelectedIndex()));
	}

	/**
	 * Set the locale using the specified value.
	 */
	private void setNewLocale() {
		String s = comboLang.getItemAt(comboLang.getSelectedIndex());
		switch (s) {
		case "Italiano":
			Constants.LOCALE_NAME = "it_IT";
			break;
		case "English":
			Constants.LOCALE_NAME = "en_US";
			break;
		}
	}

	/**
	 * Reloads the UI texts.
	 */
	public void reloadTexts() {
		PinkanellMain.getWindow().reloadTexts();
		lblInsertTheField.setText(PinkanellMain.getI18n().getTranslationString("ui.insertWidth"));
		lblInsertTheField_1.setText(PinkanellMain.getI18n().getTranslationString("ui.insertHeight"));
		btnSave.setText(PinkanellMain.getI18n().getTranslationString("ui.btnsave"));
	}

	/**
	 * Shows the window.
	 */
	public void showWindow() {
		setVisible(true);
	}
}
