package com.jaw.student.admission.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;

public interface IPreSportspartListDao {
	public void insertPreSportspartList(final List<PreSportParticipationDetails> preSportParticipationDetails) ;
	List<PreSportParticipationDetails> retrivePreSportParticipationDetailsList(PreSportsPartDetailsKey partDetailsKey) ;
}
