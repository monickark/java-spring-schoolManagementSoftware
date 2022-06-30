package com.jaw.student.admission.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;

public interface IPreSportsPartDao {
	public void insertPreSportsPart(PreSportParticipationDetails preSportsPart)
			throws DuplicateEntryException;

	public PreSportParticipationDetails retrivePreSportsPart(PreSportsPartDetailsKey studentAdmisNo) throws NoDataFoundException;

	public void updatePreSportsPart(final PreSportParticipationDetails preSportsPart,
			final PreSportsPartDetailsKey preSportsPartKey);

	

}
