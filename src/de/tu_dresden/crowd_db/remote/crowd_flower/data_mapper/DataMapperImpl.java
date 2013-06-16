package de.tu_dresden.crowd_db.remote.crowd_flower.data_mapper;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONObject;

import de.tu_dresden.crowd_db.remote.crowd_flower.entity.Entity;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.enumeration.Countries;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.enumeration.EnumWithCode;

public abstract class DataMapperImpl<E extends Entity> implements DataMapper<E> {

	protected abstract String getModelAPIName();
	
	protected String propertyToRequestString(String key, String property) {
		if (property == null) {
			return "";
		}
		return "&" + getModelAPIName() + "[" + key + "]=" +  encodeString(property);
	}
	
	protected String propertyToRequestString(String key, int property) {
		if (property == 0) {
			return "";
		}
		return propertyToRequestString(key, String.valueOf(property));
	}	
	
	@SuppressWarnings("hiding")
	protected <E extends EnumWithCode> String propertyToRequestString(String key, List<E> list) {
		if (list == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		
		for (E e : list) {
			sb.append("&" + getModelAPIName() + "[" + key + "][]=" + encodeString(e.getCode()));
		}
		return sb.toString();
	}	
	
	protected String encodeString(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (Exception e) {
			return s;
		}
	}	
	
	
	protected String parseString(Object o) {
		if (o == null) {
			return null;
		}
		
		return o.toString();
	}
	
	protected int parseInt(Object o) {
		if (o == null) {
			return 0;
		}
		
		return Integer.valueOf(o.toString());
	}
	
	protected Date parseDate(Object o) {
		if (o == null) {
			return null;
		}
		return DatatypeConverter.parseDateTime(o.toString()).getTime();
	}
		
	
	protected void parseCountries(JSONObject json, String key, List<Countries> list) {
		@SuppressWarnings("unchecked")
		List<JSONObject> countries = (List<JSONObject>) json.get(key);
		for (JSONObject c: countries) {
			Countries country = Countries.findByCode(c.get("code").toString());
			if (country != null) {
				list.add(country);
			}
		}
	}		
	
	
}
