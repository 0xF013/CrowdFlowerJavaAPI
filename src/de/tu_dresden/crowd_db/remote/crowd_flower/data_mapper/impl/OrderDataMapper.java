package de.tu_dresden.crowd_db.remote.crowd_flower.data_mapper.impl;

import org.json.simple.JSONObject;

import de.tu_dresden.crowd_db.remote.crowd_flower.data_mapper.DataMapperImpl;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.Entity;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.Order;

public class OrderDataMapper extends DataMapperImpl<Order> {

	@Override
	public Order jsonToEntity(JSONObject json, Order entity) {
		entity.setId(parseInt(json.get("id")));
		entity.setType(parseString(json.get("type")));
		entity.setMeta(parseString(json.get("meta")));
		entity.setCreatedAt(parseDate(json.get("created_at")));
		entity.setUpdatedAt(parseDate(json.get("updated_at")));
		entity.setUserId(parseInt(json.get("user_id")));
		entity.setUserId(parseInt(json.get("job_id")));
		return entity;
	}

	@Override
	public String entityToRequestString(Order entity) {
		return null;
	}

	@Override
	protected String getModelAPIName() {
		return "order";
	}

}
