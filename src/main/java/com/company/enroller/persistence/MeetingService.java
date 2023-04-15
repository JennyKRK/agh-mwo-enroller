package com.company.enroller.persistence;

import java.util.Collection;

import com.company.enroller.model.Participant;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public Meeting getById(long id){
		return (Meeting) connector.getSession().get(Meeting.class, id);
	}

	public void registerMeeting(Meeting meeting){
		Session session = connector.getSession();
		Transaction transaction = session.beginTransaction();
		session.save(meeting);
		transaction.commit();
	}

	public void deleteMeeting(Meeting meeting){
		Session session = connector.getSession();
		Transaction transaction = session.beginTransaction();
		session.delete(meeting);
		transaction.commit();
	}

	public void updateMeeting(Meeting meeting){
		Session session = connector.getSession();
		Transaction transaction = session.beginTransaction();
		session.update(meeting);
		transaction.commit();
	}


}
