/*
 * Copyright (c) 2016, Luis Filipe de Figueiredo (ldpf@ebi.ac.uk). All rights reserved.
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
package org.openscience.cdk.knime.nodes.sugarremover;

import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.openscience.cdk.knime.core.CDKSettings;

/**
 * @author Luis Filipe de Figueiredo, European Bioinformatics Institute
 */
public class SugarRemoverSettings implements CDKSettings {

	private String m_molColumnName;

	private boolean m_replaceColumn = true;
	private String m_appendColumnName;

	/**
	 * Returns the name of the column containing the molecules.
	 * 
	 * @return the molecules' column name
	 */
	public String targetColumn() {
		return m_molColumnName;
	}

	/**
	 * Sets the name of the column containing the molecules.
	 * 
	 * @param colName the molecules' column name
	 */
	public void targetColumn(final String colName) {
		m_molColumnName = colName;
	}

	/**
	 * Returns if the molecule column should be replaced or if a new column should be appended.
	 * 
	 * @return <code>true</code> if the column should be replaced, <code>false</code> if a new column should be appended
	 */
	public boolean replaceColumn() {
		return m_replaceColumn;
	}

	/**
	 * Sets if the molecule column should be replaced or if a new column should be appended.
	 * 
	 * @param replace <code>true</code> if the column should be replaced, <code>false</code> if a new column should be
	 *        appended
	 */
	public void replaceColumn(final boolean replace) {
		m_replaceColumn = replace;
	}

	/**
	 * @return the appendColumnName
	 */
	public String appendColumnName() {
		return m_appendColumnName;
	}

	/**
	 * @param appendColumnName the appendColumnName to set
	 */
	public void appendColumnName(final String appendColumnName) {
		m_appendColumnName = appendColumnName;
	}

	/**
	 * Saves the settings into the given node settings object.
	 * 
	 * @param settings a node settings object
	 */
	public void saveSettings(final NodeSettingsWO settings) {

		settings.addString("molColumn", m_molColumnName);
		settings.addBoolean("replaceColumn", m_replaceColumn);
		settings.addString("appendColName", appendColumnName());
	}

	/**
	 * Loads the settings from the given node settings object.
	 * 
	 * @param settings a node settings object
	 * @throws InvalidSettingsException if not all required settings are available
	 */
	public void loadSettings(final NodeSettingsRO settings) throws InvalidSettingsException {

		m_molColumnName = settings.getString("molColumn");
		m_replaceColumn = settings.getBoolean("replaceColumn");
		m_appendColumnName = settings.getString("appendColName", m_molColumnName + " (light)");
	}
}
