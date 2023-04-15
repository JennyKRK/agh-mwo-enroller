package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Participant;

@Component("participantService")
public class ParticipantService {

	DatabaseConnector connector;

	public ParticipantService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Participant> getAll() {
		String hql = "FROM Participant";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public Participant findByLogin(String login){
		return (Participant) connector.getSession().get(Participant.class, login);
	}

	public void registerParticipant(Participant participant){
		Session session = connector.getSession();
		Transaction transaction = session.beginTransaction();
		session.save(participant);
		transaction.commit();
	}

	public void deleteParticipant(String login){
		Participant participant = findByLogin(login);
		Session session = connector.getSession();
		Transaction transaction = session.beginTransaction();
		session.delete(participant);
		transaction.commit();
	}


	public void updateParticipant(Participant participant){
		Session session = connector.getSession();
		Transaction transaction = session.beginTransaction();

		session.update(participant);
		transaction.commit();

	}

	public Collection<Participant> getAllParticipantsSorted(String sortBy, String sortOrder) {
		String hql = "FROM Participant";
		if (sortBy.equals("login")){
			hql += " ORDER BY login";
		}
		if (sortOrder.equals("ASC")) {
			hql += " ASC";
		}
		if (sortOrder.equals("DESC")) {
			hql += " DESC";
		}

		Query query = connector.getSession().createQuery(hql, Participant.class);
		return query.list();
	}


	// this one would work too
//	public Participant registerParticipant(Participant participant){
//		Session session = connector.getSession();
//		Transaction transaction = session.beginTransaction();
//		session.save(participant);
//		transaction.commit();
//		return participant;
//	}




}
