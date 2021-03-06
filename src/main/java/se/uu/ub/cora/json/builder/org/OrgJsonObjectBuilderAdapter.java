/*
 * Copyright 2015 Uppsala University Library
 * Copyright 2016 Olov McKie
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.uu.ub.cora.json.builder.org;

import se.uu.ub.cora.json.builder.JsonArrayBuilder;
import se.uu.ub.cora.json.builder.JsonObjectBuilder;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.org.OrgJsonObjectAdapter;

public class OrgJsonObjectBuilderAdapter implements JsonObjectBuilder {
	private static final int TEXT_INDENT = 4;
	private org.json.JSONObject orgJsonObject = new org.json.JSONObject();

	@Override
	public void addKeyString(String key, String value) {
		orgJsonObject.put(key, value);
	}

	@Override
	public void addKeyJsonObjectBuilder(String key, JsonObjectBuilder jsonObjectBuilder) {
		OrgJsonObjectBuilderAdapter objectBuilderAdapter = (OrgJsonObjectBuilderAdapter) jsonObjectBuilder;
		orgJsonObject.put(key, objectBuilderAdapter.getWrappedBuilder());
	}

	org.json.JSONObject getWrappedBuilder() {
		return orgJsonObject;
	}

	@Override
	public void addKeyJsonArrayBuilder(String key, JsonArrayBuilder jsonArrayBuilder) {
		OrgJsonArrayBuilderAdapter arrayBuilderAdapter = (OrgJsonArrayBuilderAdapter) jsonArrayBuilder;
		orgJsonObject.put(key, arrayBuilderAdapter.getWrappedBuilder());
	}

	@Override
	public JsonObject toJsonObject() {
		return OrgJsonObjectAdapter.usingOrgJsonObject(orgJsonObject);
	}

	@Override
	public String toJsonFormattedString() {
		return orgJsonObject.toString();
	}

	@Override
	public String toJsonFormattedPrettyString() {
		return orgJsonObject.toString(TEXT_INDENT);
	}
}
