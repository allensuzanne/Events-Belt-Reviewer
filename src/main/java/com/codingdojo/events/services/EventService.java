package com.codingdojo.events.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.events.models.Event;
import com.codingdojo.events.models.User;
import com.codingdojo.events.models.UserEvent;
import com.codingdojo.events.repositories.EventRepository;
import com.codingdojo.events.repositories.MessageRepository;
import com.codingdojo.events.repositories.UserEventRepository;
import com.codingdojo.events.repositories.UserRepository;

@Service
public class EventService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserEventRepository userEventRepo;
	
	@Autowired
	private EventRepository eventRepo;
	
	@Autowired
	private MessageRepository messageRepo;
	
//  ----------------------------------------------------------------
//  find 
//  ----------------------------------------------------------------

	public User findUserById(Long userId) {
    	Optional<User> u = userRepo.findById(userId);
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }

	public List<Event> eventsInState(String state) {
    	return eventRepo.findByState(state);
	}

	public List<Event> eventsOutOfState(String state) {
    	return eventRepo.findByStateIsNot(state);
	}
	
	public Event findEventById(Long event_id) {
		return eventRepo.findEventById(event_id);
	}
	
//  ----------------------------------------------------------------
//  create and delete
//  ----------------------------------------------------------------
	

	public Event createEvent(Event event) {
		return eventRepo.save(event);
		
	}

	public UserEvent createRelationship(UserEvent userEvent) {
		return userEventRepo.save(userEvent);
		
	}

	public void deleteEvent(Long event_id) {
		eventRepo.deleteById(event_id);
	}

	public Event updateEvent(Event event) {
		return this.eventRepo.save(event);
		
	}

	public void updateUser(User attendee) {
		userRepo.save(attendee);
		
	}


	
	

	
}
