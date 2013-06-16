package de.tu_dresden.crowd_db.remote.crowd_flower.data_mapper;

import org.json.simple.JSONObject;

import de.tu_dresden.crowd_db.remote.crowd_flower.entity.Entity;

public interface DataMapper<E extends Entity> {
	public E jsonToEntity(JSONObject json, E entity);
	public String entityToRequestString(E entity);
}
