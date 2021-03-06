/*
 * Copyright 2015 Uppsala University Library
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

package se.uu.ub.cora.json.parser.org;

import org.testng.annotations.Test;
import se.uu.ub.cora.json.parser.JsonArray;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonParseException;
import se.uu.ub.cora.json.parser.JsonString;
import se.uu.ub.cora.json.parser.JsonValue;
import se.uu.ub.cora.json.parser.JsonValueType;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class OrgJsonArrayAdapterTest {
	@Test
	public void testUsingOrgJsonArray() {
		org.json.JSONArray orgJsonArray = new org.json.JSONArray("[\"value1\",\"value2\"]");
		JsonValue jsonValue = OrgJsonArrayAdapter.usingOrgJsonArray(orgJsonArray);
		assertTrue(jsonValue instanceof JsonArray);
	}

	@Test
	public void testGetValueType() {
		org.json.JSONArray orgJsonArray = new org.json.JSONArray("[\"value1\",\"value2\"]");
		JsonValue jsonValue = OrgJsonArrayAdapter.usingOrgJsonArray(orgJsonArray);
		assertEquals(jsonValue.getValueType(), JsonValueType.ARRAY);
	}

	@Test
	public void testGetValue() {
		JsonArray jsonArray = parseStringAsJsonArray("[\"value\",\"value2\"]");
		JsonString value = (JsonString) jsonArray.getValue(0);
		assertEquals(value.getStringValue(), "value");
	}

	private JsonArray parseStringAsJsonArray(String json) {
		org.json.JSONArray orgJsonArray = new org.json.JSONArray(json);
		JsonArray jsonArray = OrgJsonArrayAdapter.usingOrgJsonArray(orgJsonArray);
		return jsonArray;
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testGetValueNotFound() {
		JsonArray jsonArray = parseStringAsJsonArray("[\"value\",\"value2\"]");
		jsonArray.getValue(10);
	}

	@Test
	public void testGetValueAsJsonString() {
		JsonArray jsonArray = parseStringAsJsonArray("[\"value\",\"value2\"]");
		assertTrue(jsonArray.getValueAsJsonString(0) instanceof JsonString);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testGetValueAsJsonStringNotFound() {
		JsonArray jsonArray = parseStringAsJsonArray("[\"value\",\"value2\"]");
		jsonArray.getValueAsJsonString(10);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testGetValueAsJsonStringNotAString() {
		JsonArray jsonArray = parseStringAsJsonArray("[[\"value\",\"value2\"]]");
		jsonArray.getValueAsJsonString(0);
	}

	@Test
	public void testGetValueAsJsonObject() {
		JsonArray jsonArray = parseStringAsJsonArray("[{\"value\":\"value2\"}]");
		assertTrue(jsonArray.getValueAsJsonObject(0) instanceof JsonObject);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testGetValueAsJsonObjectNotFound() {
		JsonArray jsonArray = parseStringAsJsonArray("[{\"value\":\"value2\"}]");
		jsonArray.getValueAsJsonObject(10);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testGetValueAsJsonObjectNotAnObject() {
		JsonArray jsonArray = parseStringAsJsonArray("[\"value\",\"value2\"]");
		jsonArray.getValueAsJsonObject(0);
	}

	@Test
	public void testGetValueAsJsonArray() {
		JsonArray jsonArray = parseStringAsJsonArray("[[\"value\",\"value2\"]]");
		assertTrue(jsonArray.getValueAsJsonArray(0) instanceof JsonArray);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testGetValueAsJsonArrayNotFound() {
		JsonArray jsonArray = parseStringAsJsonArray("[[\"value\",\"value2\"]]");
		jsonArray.getValueAsJsonArray(10);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testGetValueAsJsonArrayNotAnArray() {
		JsonArray jsonArray = parseStringAsJsonArray("[{\"value\":\"value2\"}]");
		jsonArray.getValueAsJsonArray(0);
	}

	@Test
	public void testIterator() {
		JsonArray jsonArray = parseStringAsJsonArray("[\"value\",\"value2\"]");
		int counter = 0;
		JsonValue afterIterator = null;
		for (JsonValue jsonValue : jsonArray) {
			afterIterator = jsonValue;
			counter++;
		}
		assertEquals(((JsonString) afterIterator).getStringValue(), "value2");
		assertEquals(counter, 2);
	}
}
