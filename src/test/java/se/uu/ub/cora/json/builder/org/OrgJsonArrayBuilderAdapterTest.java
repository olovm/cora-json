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

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.json.builder.JsonArrayBuilder;
import se.uu.ub.cora.json.builder.JsonObjectBuilder;
import se.uu.ub.cora.json.parser.JsonArray;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonString;

public class OrgJsonArrayBuilderAdapterTest {
	private JsonArrayBuilder jsonArrayBuilder;

	@BeforeMethod
	public void beforeMethod() {
		jsonArrayBuilder = new OrgJsonArrayBuilderAdapter();
	}

	@Test
	public void testAddStringValue() {
		jsonArrayBuilder.addString("value");
		JsonArray jsonArray = jsonArrayBuilder.toJsonArray();
		JsonString jsonString = jsonArray.getValueAsJsonString(0);
		assertEquals(jsonString.getStringValue(), "value");
	}

	@Test
	public void testAddJsonObjectBuilder() {
		JsonObjectBuilder jsonObjectBuilderChild = new OrgJsonObjectBuilderAdapter();
		jsonObjectBuilderChild.addKeyString("keyChild", "valueChild");

		jsonArrayBuilder.addJsonObjectBuilder(jsonObjectBuilderChild);
		JsonArray jsonArray = jsonArrayBuilder.toJsonArray();

		JsonObject jsonObjectChild = jsonArray.getValueAsJsonObject(0);

		JsonString jsonString = jsonObjectChild.getValueAsJsonString("keyChild");
		assertEquals(jsonString.getStringValue(), "valueChild");
	}

	@Test
	public void testAddJsonArrayBuilder() {
		JsonArrayBuilder jsonArrayBuilderChild = new OrgJsonArrayBuilderAdapter();
		jsonArrayBuilderChild.addString("value");

		jsonArrayBuilder.addJsonArrayBuilder(jsonArrayBuilderChild);
		JsonArray jsonArray = jsonArrayBuilder.toJsonArray();

		JsonArray jsonArrayChild = jsonArray.getValueAsJsonArray(0);

		JsonString jsonString = jsonArrayChild.getValueAsJsonString(0);
		assertEquals(jsonString.getStringValue(), "value");
	}

	@Test
	public void testToJsonFormattedString() {
		JsonObjectBuilder jsonObjectBuilderChild = new OrgJsonObjectBuilderAdapter();
		jsonObjectBuilderChild.addKeyString("keyChild", "valueChild");

		jsonArrayBuilder.addJsonObjectBuilder(jsonObjectBuilderChild);

		String json = jsonArrayBuilder.toJsonFormattedString();
		assertEquals(json, "[{\"keyChild\":\"valueChild\"}]");
	}

	@Test
	public void testToJsonFormattedPrettyString() {
		JsonObjectBuilder jsonObjectBuilderChild = new OrgJsonObjectBuilderAdapter();
		jsonObjectBuilderChild.addKeyString("keyChild", "valueChild");

		jsonArrayBuilder.addJsonObjectBuilder(jsonObjectBuilderChild);

		String json = jsonArrayBuilder.toJsonFormattedPrettyString();
		assertEquals(json, "[{\"keyChild\": \"valueChild\"}]");
	}
}
