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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONObject;

import se.uu.ub.cora.json.parser.JsonArray;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonParseException;
import se.uu.ub.cora.json.parser.JsonString;
import se.uu.ub.cora.json.parser.JsonValue;
import se.uu.ub.cora.json.parser.JsonValueType;

public final class OrgJsonObjectAdapter implements JsonObject {

	public static OrgJsonObjectAdapter usingOrgJsonObject(JSONObject orgJsonObject) {
		return new OrgJsonObjectAdapter(orgJsonObject);
	}

	private JSONObject orgJsonObject;

	private OrgJsonObjectAdapter(JSONObject orgJsonObject) {
		this.orgJsonObject = orgJsonObject;
	}

	@Override
	public JsonValueType getValueType() {
		return JsonValueType.OBJECT;
	}

	@Override
	public JsonValue getValue(String key) {
		try {
			return tryGetValue(key);
		} catch (Exception e) {
			throw new JsonParseException("Json object does not contain requested key: " + key, e);
		}
	}

	private JsonValue tryGetValue(String key) {
		Object object = orgJsonObject.get(key);
		return OrgJsonValueFactory.createFromOrgJsonObject(object);
	}

	@Override
	public JsonString getValueAsJsonString(String key) {
		JsonValue jsonValue = getValue(key);
		if (JsonValueType.STRING == jsonValue.getValueType()) {
			return (JsonString) getValue(key);
		}
		throw new JsonParseException("Not a string");
	}

	@Override
	public JsonObject getValueAsJsonObject(String key) {
		JsonValue jsonValue = getValue(key);
		if (JsonValueType.OBJECT == jsonValue.getValueType()) {
			return (JsonObject) getValue(key);
		}
		throw new JsonParseException("Not an object");
	}

	@Override
	public JsonArray getValueAsJsonArray(String key) {
		JsonValue jsonValue = getValue(key);
		if (JsonValueType.ARRAY == jsonValue.getValueType()) {
			return (JsonArray) getValue(key);
		}
		throw new JsonParseException("Not an array");
	}

	@Override
	public boolean containsKey(String key) {
		return orgJsonObject.has(key);
	}

	@Override
	public Set<String> keySet() {
		return orgJsonObject.keySet();
	}

	@Override
	public Set<Entry<String, JsonValue>> entrySet() {
		Map<String, JsonValue> outSet = new HashMap<>(orgJsonObject.length());
		for (String key : orgJsonObject.keySet()) {
			Object child = orgJsonObject.get(key);
			JsonValue jsonValue = OrgJsonValueFactory.createFromOrgJsonObject(child);
			outSet.put(key, jsonValue);
		}
		return outSet.entrySet();
	}

	@Override
	public int size() {
		return orgJsonObject.length();
	}

	@Override
	public String toJsonFormattedString() {
		return orgJsonObject.toString();
	}
}
