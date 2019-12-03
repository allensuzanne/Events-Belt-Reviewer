package com.codingdojo.events.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.events.models.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

	List<Event> findByState(String state);

	List<Event> findByStateIsNot(String state);

	Event findEventById(Long event_id);


}
