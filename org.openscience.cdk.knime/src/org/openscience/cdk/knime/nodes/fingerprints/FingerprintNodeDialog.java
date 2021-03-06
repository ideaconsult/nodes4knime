/*
 * Copyright (C) 2003 - 2016 University of Konstanz, Germany and KNIME GmbH, Konstanz, Germany Website:
 * http://www.knime.org; Email: contact@knime.org
 * 
 * This file is part of the KNIME CDK plugin.
 * 
 * The KNIME CDK plugin is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * The KNIME CDK plugin is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with the plugin. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package org.openscience.cdk.knime.nodes.fingerprints;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.knime.core.data.DataTableSpec;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.NotConfigurableException;
import org.knime.core.node.util.ColumnSelectionComboxBox;
import org.openscience.cdk.knime.commons.CDKNodeUtils;
import org.openscience.cdk.knime.nodes.fingerprints.FingerprintSettings.FingerprintTypes;

/**
 * This is the dialog for the fingerprint node.
 * 
 * @author Thorsten Meinl, University of Konstanz
 */
public class FingerprintNodeDialog extends NodeDialogPane {

	@SuppressWarnings("unchecked")
	private final ColumnSelectionComboxBox m_molColumn = new ColumnSelectionComboxBox((Border) null,
			CDKNodeUtils.ACCEPTED_VALUE_CLASSES);

	private final JRadioButton m_standardFP = new JRadioButton("Standard");
	private final JRadioButton m_extendedFP = new JRadioButton("Extended");
	private final JRadioButton m_estateFP = new JRadioButton("EState");
	private final JRadioButton m_pubchemFP = new JRadioButton("Pubchem");
	private final JRadioButton m_maccsFP = new JRadioButton("MACCS");

	private final JRadioButton circularFP = new JRadioButton("Circular");
	private final JComboBox<FingerprintSettings.FingerprintClasses> circularFpClass = 
			new JComboBox<FingerprintSettings.FingerprintClasses>(FingerprintSettings.FingerprintClasses.values());

	private final FingerprintSettings m_settings = new FingerprintSettings();

	/**
	 * Creates a new dialog for the fingerprint node.
	 */
	public FingerprintNodeDialog() {

		JPanel p = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTHWEST;

		p.add(new JLabel("Column with molecules   "), c);
		c.gridx = 1;
		p.add(m_molColumn, c);

		c.gridy++;
		c.gridx = 0;
		p.add(new JLabel("Fingerprint type   "), c);
		c.gridx = 1;
		p.add(m_standardFP, c);
		c.gridy++;
		p.add(m_extendedFP, c);
		c.gridy++;
		p.add(m_estateFP, c);
		c.gridy++;
		p.add(m_pubchemFP, c);
		c.gridy++;
		p.add(m_maccsFP, c);
		c.gridy++;
		p.add(circularFP, c);
		c.gridy++;
		p.add(circularFpClass, c);
		
		circularFP.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {

				if (circularFP.isSelected()) {
					circularFpClass.setEnabled(true);
				} else {
					circularFpClass.setEnabled(false);
				}
			}
		});

		ButtonGroup bg = new ButtonGroup();
		bg.add(m_standardFP);
		bg.add(m_extendedFP);
		bg.add(m_estateFP);
		bg.add(m_pubchemFP);
		bg.add(m_maccsFP);
		bg.add(circularFP);

		circularFpClass.setEnabled(false);

		addTab("Fingerprint Options", p);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void loadSettingsFrom(final NodeSettingsRO settings, final DataTableSpec[] specs)
			throws NotConfigurableException {

		m_settings.loadSettingsForDialog(settings);

		m_molColumn.update(specs[0], m_settings.targetColumn());
		if (m_settings.fingerprintType().equals(FingerprintTypes.Standard)) {
			m_standardFP.setSelected(true);
		} else if (m_settings.fingerprintType().equals(FingerprintTypes.Extended)) {
			m_extendedFP.setSelected(true);
		} else if (m_settings.fingerprintType().equals(FingerprintTypes.EState)) {
			m_estateFP.setSelected(true);
		} else if (m_settings.fingerprintType().equals(FingerprintTypes.Pubchem)) {
			m_pubchemFP.setSelected(true);
		} else if (m_settings.fingerprintType().equals(FingerprintTypes.MACCS)) {
			m_maccsFP.setSelected(true);
		} else if (m_settings.fingerprintType().equals(FingerprintTypes.Circular)) {
			circularFP.setSelected(true);
			circularFpClass.setEnabled(true);
			circularFpClass.setSelectedItem(m_settings.fingerprintClass());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveSettingsTo(final NodeSettingsWO settings) throws InvalidSettingsException {

		m_settings.targetColumn(m_molColumn.getSelectedColumn());
		if (m_standardFP.isSelected()) {
			m_settings.fingerprintType(FingerprintTypes.Standard);
		} else if (m_extendedFP.isSelected()) {
			m_settings.fingerprintType(FingerprintTypes.Extended);
		} else if (m_estateFP.isSelected()) {
			m_settings.fingerprintType(FingerprintTypes.EState);
		} else if (m_pubchemFP.isSelected()) {
			m_settings.fingerprintType(FingerprintTypes.Pubchem);
		} else if (m_maccsFP.isSelected()) {
			m_settings.fingerprintType(FingerprintTypes.MACCS);
		} else if (circularFP.isSelected()) {
			m_settings.fingerprintType(FingerprintTypes.Circular);
			m_settings.fingerprintClass((FingerprintSettings.FingerprintClasses) circularFpClass.getSelectedItem());
		}
		m_settings.saveSettings(settings);
	}
}
