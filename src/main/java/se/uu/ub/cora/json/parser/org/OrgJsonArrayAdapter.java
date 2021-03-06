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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;

import se.uu.ub.cora.json.parser.JsonArray;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonParseException;
import se.uu.ub.cora.json.parser.JsonString;
import se.uu.ub.cora.json.parser.JsonValue;
import se.uu.ub.cora.json.parser.JsonValueType;

public final class OrgJsonArrayAdapter implements JsonArray {

	public static OrgJsonArrayAdapter usingOrgJsonArray(JSONArray orgJsonArray) {
		return new OrgJsonArrayAdapter(orgJsonArray);
	}

	private JSONArray orgJsonArray;

	private OrgJsonArrayAdapter(JSONArray orgJsonArray) {
		this.orgJsonArray = orgJsonArray;
	}

	@Override
	public JsonValueType getValueType() {
		return JsonValueType.ARRAY;
	}

	@Override
	public JsonValue getValue(int index) {
		try {
			return tryGetValue(index);
		} catch (Exception e) {
			throw new JsonParseException("Json array does not contain requested index", e);
		}
	}

	private JsonValue tryGetValue(int index) {
		Object object = orgJsonArray.get(index);
		return OrgJsonValueFactory.createFromOrgJsonObject(object);
	}

	@Override
	public JsonString getValueAsJsonString(int index) {
		JsonValue jsonValue = getValue(index);
		if (JsonValueType.STRING == jsonValue.getValueType()) {
			return (JsonString) getValue(index);
		}
		throw new JsonParseException("Not a string");
	}

	@Override
	public JsonObject getValueAsJsonObject(int index) {
		JsonValue jsonValue = getValue(index);
		if (JsonValueType.OBJECT == jsonValue.getValueType()) {
			return (JsonObject) getValue(index);
		}
		throw new JsonParseException("Not an object");
	}

	@Override
	public JsonArray getValueAsJsonArray(int index) {
		JsonValue jsonValue = getValue(index);
		if (JsonValueType.ARRAY == jsonValue.getValueType()) {
			return (JsonArray) getValue(index);
		}
		throw new JsonParseException("Not an object");
	}

	@Override
	public Iterator<JsonValue> iterator() {
		List<JsonValue> list = new ArrayList<>(orgJsonArray.length());
		for (int i = 0; i < orgJsonArray.length(); i++) {
			list.add(OrgJsonValueFactory.createFromOrgJsonObject(orgJsonArray.get(i)));
		}
		return list.iterator();
	}

	@Override
	public String toJsonFormattedString() {
		return orgJsonArray.toString();
	}

}
