package com.company.enroller.controllers;

import java.util.Collection;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company.enroller.model.Meeting;
import com.company.enroller.persistence.MeetingService;


@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

    @Autowired
    MeetingService meetingService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetings() {
        Collection<Meeting> meetings = meetingService.getAll();
        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {
        Meeting meeting = meetingService.getById(id);
        if (meeting == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting){
        if(meetingService.getById(meeting.getId()) != null){
            return new ResponseEntity("Unable to create. User already exists",HttpStatus.CONFLICT);
        }
        meetingService.registerMeeting(meeting);
        return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMeeting(@PathVariable("id") long id){
        Meeting meeting = meetingService.getById(id);
        if (meeting == null){
            return new ResponseEntity("Unable to delete. This user does not exist", HttpStatus.NOT_FOUND);
        }
        meetingService.deleteMeeting(meeting);
        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMeeting(@PathVariable("id") long id, @RequestBody Meeting meeting){
        Meeting meetingFound = meetingService.getById(id);
        if (meetingFound == null){
            return new ResponseEntity("Unable to update. This user does not exist", HttpStatus.NOT_FOUND);
        }
        else {
            BeanUtils.copyProperties(meeting,meetingFound);
            meetingService.updateMeeting(meetingFound);
            return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
        }
    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    public ResponseEntity<?> updateParticipant(@PathVariable("id") String login, @RequestBody Participant participant) {
//        Participant foundP = participantService.findByLogin(login);
//
//        if(foundP==null){
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//
//        else {
//            BeanUtils.copyProperties(participant, foundP);
//            participantService.updateParticipant(foundP);
//            return new ResponseEntity<Participant>(participant, HttpStatus.OK);
//        }




}
