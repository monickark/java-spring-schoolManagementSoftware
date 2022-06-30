package com.jaw.communication.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.AcademicCalendarListKey;

public interface INoticeBoardListDAO {
	public List<NoticeBoard> getNoticeBoardList(final NoticeBoardListKey noticeBoardListKey)
			throws NoDataFoundException;
	public List<NoticeBoard> getNoticeBoardListForDashBoard(final NoticeBoardListKey noticeBoardListKey,String profileGroup)
			throws NoDataFoundException;
}
