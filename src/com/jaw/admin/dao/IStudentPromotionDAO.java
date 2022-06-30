package com.jaw.admin.dao;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.CourseTermLinking;
import com.jaw.core.dao.CourseTermLinkingKey;

public interface IStudentPromotionDAO {
	CourseTermLinking selectCourseTermForPromotion(CourseTermLinkingKey courseTermLinkingKey) throws NoDataFoundException;

}
