package com.jaw.communication.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface INoticeBoardDAO {
	void insertNoticeBoardRec(NoticeBoard noticeBoardRecord)
			throws DuplicateEntryException;

	void updateNoticeBoardRec(NoticeBoard noticeBoardRecord,final NoticeBoardKey noticeBoardKey)
			throws UpdateFailedException;

	void deleteNoticeBoardRec(NoticeBoard noticeBoardRecord,final NoticeBoardKey noticeBoardKey) throws DeleteFailedException;

	NoticeBoard selectNoticeBoardRec(
			final NoticeBoardKey noticeBoardKey)
			throws NoDataFoundException;
}
