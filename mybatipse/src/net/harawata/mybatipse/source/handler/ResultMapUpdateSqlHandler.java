/*-******************************************************************************
 * Copyright (c) 2018 Sc122.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Sc122 - initial API and implementation and/or initial documentation
 *******************************************************************************/

package net.harawata.mybatipse.source.handler;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.harawata.mybatipse.util.XpathUtil;

/**
 * Adds <sql> insert template to mapper file.
 * 
 * @author kdavidson
 */
public class ResultMapUpdateSqlHandler extends ResultMapSqlSourceHandler
{

	@Override
	protected String buildSqlId(Element resultMap)
	{
		return resultMap.getAttribute("id").replaceAll("Map", "") + "UpdateColumns";
	}

	@Override
	protected String buildSqlStatement(Element resultMap)
	{
		final StringBuilder sql = new StringBuilder("\n");

		try
		{
			NodeList nodes = XpathUtil.xpathNodes(resultMap, "id|result");

			for (int i = 0; i < nodes.getLength(); i++)
			{
				if (i > 0)
				{
					sql.append(",\n");
				}

				sql.append(nodeColumnValue(nodes.item(i))).append(" = ");
				sql.append("#{" + nodeParameterValue(nodes.item(i)) + "}");
			}
		}
		catch (XPathExpressionException e)
		{
			// no child nodes
		}

		return sql.toString();
	}

}
