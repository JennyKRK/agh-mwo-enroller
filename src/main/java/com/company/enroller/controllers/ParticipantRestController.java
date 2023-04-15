package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/participants")
public class ParticipantRestController {

	@Autowired
	ParticipantService participantService;
	//dependency injection by Spring

//	@RequestMapping(value = "", method = RequestMethod.GET)
//	public ResponseEntity<?> getParticipants() {
//		Collection<Participant> participants = participantService.getAll();
//		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
//	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipants(@RequestParam(value = "sortBy", defaultValue = "",required=false) String sortBy,
											 @RequestParam(value = "sortOrder", defaultValue = "",required=false) String sortOrder	)
											  {
		Collection<Participant> participants = participantService.getAllParticipantsSorted(sortBy, sortOrder);
		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipant(@PathVariable("id") String login) {
		Participant participant = participantService.findByLogin(login);
		if (participant == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Participant>(participant, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> registerParticipant(@RequestBody Participant participant){
		if(participantService.findByLogin(participant.getLogin()) != null){
		return new ResponseEntity("Unable to create. A participant with login " + participant.getLogin() + " already exist.", HttpStatus.CONFLICT);}
		else {
			participantService.registerParticipant(participant);
			return new ResponseEntity<Participant>(participant, HttpStatus.CREATED);
		}
		}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteParticipant(@PathVariable("id") String login) {
		Participant participant = participantService.findByLogin(login);
		if (participant == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		else{
			participantService.deleteParticipant(login);
			return new ResponseEntity<Participant>(participant, HttpStatus.OK);
		}




	}


	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateParticipant(@PathVariable("id") String login, @RequestBody Participant participant) {
		Participant foundP = participantService.findByLogin(login);

		if(foundP==null){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		else {
			BeanUtils.copyProperties(participant, foundP);
			participantService.updateParticipant(foundP);
			return new ResponseEntity<Participant>(participant, HttpStatus.OK);
		}




	}

//	@RequestMapping(value = "?sortBy=login", method = RequestMethod.GET)
//	public ResponseEntity<?> sortByLogin() {
//		Collection<Participant> participants = participantService.sortByLogin();
//		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
//	}



	//http://localhost:8080/participants?sortBy=login&sortOrder=DESC
}
