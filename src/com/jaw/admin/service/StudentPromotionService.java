package com.jaw.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaw.admin.controller.StudentPromotionVO;
import com.jaw.admin.dao.IStudentPromotionDAO;
import com.jaw.admin.dao.IStudentPromotionListDAO;
import com.jaw.admin.dao.StudentPromotionKey;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.StudentPromoteException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.core.dao.AcademicTermDetails;
import com.jaw.core.dao.AcademicTermDetailsKey;
import com.jaw.core.dao.CourseTermLinking;
import com.jaw.core.dao.CourseTermLinkingKey;
import com.jaw.core.dao.IAcademicTermDetailsDAO;
import com.jaw.core.dao.IStudentGroupMasterDAO;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.core.dao.StudentGroupMasterKey;
import com.jaw.core.service.IStudentGroupMasterService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.dao.IStudentMasterListDAO;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.admission.dao.StudentMasterKey;

@Service
public class StudentPromotionService implements IStudentPromotionService {

	Logger logger = Logger.getLogger(StudentPromotionService.class);
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IAcademicTermDetailsDAO academicTermDetailsDAO;
	@Autowired
	IStudentMasterListDAO studentMasterListDAO;
	@Autowired
	DoAudit doAudit;
	@Autowired
	IStudentPromotionListDAO studentPromotionListDAO;
	@Autowired
	IStudentGroupMasterDAO studentGroupMasterDAO;
	@Autowired
	IStudentPromotionDAO studentPromotionDAO;
	@Autowired
	IDropDownListService dropDownListService;

	@Autowired
	CommonCodeUtil commonCodeUtil;
	ArrayList<String> errorList;

	@Override
	public int checkAcademicTermStatus(StudentPromotionVO studentPromotionVO,
			UserSessionDetails userSessionDetails, String term) {
		AcademicTermDetailsKey academicTermDetailsKey = new AcademicTermDetailsKey();
		academicTermDetailsKey.setAcTerm(studentPromotionVO
				.getFromAcademicTerm());
		academicTermDetailsKey.setBranchId(studentPromotionVO.getBranchId());
		academicTermDetailsKey.setInstId(userSessionDetails.getInstId());
		if (term.equals("from")) {
			academicTermDetailsKey
					.setAcTermSts(ApplicationConstant.ACT_TRM_PRESENT);
		} else {
			academicTermDetailsKey
					.setAcTermSts(ApplicationConstant.ACT_TRM_FUTURE);
		}
		Integer result = academicTermDetailsDAO
				.checkAcademicTermStatusForStudentPromotion(academicTermDetailsKey);
		System.out.println("service resultttttttt   : " + result);
		return result;
	}

	@Override
	public String studentPromoted(final StudentPromotionVO studentPromotionVO,
			final SessionCache sessionCache, ApplicationCache applicationCache)
			throws NoDataFoundException, DuplicateEntryException,
			CommonCodeNotFoundException, DatabaseException,
			UpdateFailedException, StudentPromoteException {
		String thraedId = "";

		errorList = new ArrayList<String>();

		// Check the promoted status
		AcademicTermDetailsKey academicTermDetailsKey = new AcademicTermDetailsKey();
		academicTermDetailsKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		academicTermDetailsKey.setBranchId(studentPromotionVO.getBranchId());
		academicTermDetailsKey.setAcTerm(studentPromotionVO
				.getFromAcademicTerm());
		academicTermDetailsKey
				.setAcTermSts(ApplicationConstant.ACT_TRM_PRESENT);
		int available = academicTermDetailsDAO
				.checkAcademicTermPromotionStatus(academicTermDetailsKey);
		if (available == 0) {
			throw new StudentPromoteException();
		}

		// Create id for thread
		UUID idOne = UUID.randomUUID();
		thraedId = idOne.toString();
		System.out.println("UUID One: " + idOne.toString());
		// Update Academic Term Promote status
		updateAcademicTermDetailsStatus(studentPromotionVO, sessionCache,
				thraedId);

		Thread t = new Thread(new Runnable() {
			public void run() {
				String stugrp = "";
				StudentMasterKey studentMasterKey = new StudentMasterKey();
				studentMasterKey.setInstId(sessionCache.getUserSessionDetails()
						.getInstId());
				studentMasterKey.setBranchId(studentPromotionVO.getBranchId());
				studentMasterKey.setAcademicYear(studentPromotionVO
						.getFromAcademicTerm());
				// Get the distinct of student group id
				List<StudentMaster> listStudentMaster = null;
				try {
					listStudentMaster = studentMasterListDAO
							.selectStudentGroupList(studentMasterKey);
				} catch (NoDataFoundException e) {
					errorList.add("No data Found Exception");
				}
				List<String> studentGrpids = new ArrayList<String>();
				for (int lsm = 0; lsm < listStudentMaster.size(); lsm++) {
					studentGrpids.add(listStudentMaster.get(lsm).getStuGrpId());
					System.out.println("Student group ids:"
							+ listStudentMaster.get(lsm).getStuGrpId());
				}

				System.out.println("student grp size   : "
						+ studentGrpids.size());
				if (errorList.size() == 0) {
					// Student promoted for each group
					for (int stg = 0; stg < studentGrpids.size(); stg++) {
						System.out.println("student group in first :" + stg);
						ArrayList<String> stugList = new ArrayList<String>();

						System.out.println("student group  : "
								+ studentGrpids.get(stg));
						// Get course and Term from stgm
						StudentGroupMasterKey studentGrpMasterKey = new StudentGroupMasterKey();
						studentGrpMasterKey.setInstId(sessionCache
								.getUserSessionDetails().getInstId());
						studentGrpMasterKey.setBranchId(studentPromotionVO
								.getBranchId());
						studentGrpMasterKey.setStudentGrpId(studentGrpids
								.get(stg));
						StudentGroupMaster studentGrpMastr = null;
						try {
							studentGrpMastr = studentGroupMasterDAO
									.selectStudentGrpMasterRec(studentGrpMasterKey);
							System.out.println("Selected student group :"
									+ studentGrpMastr.toString());
						} catch (NoDataFoundException e) {
							System.out.println("select student group master rec no data found exception"+stg);
							stugList.add("No data Found Exception");
						}

						System.out.println("student group master values  : "
								+ studentGrpMastr.toString());
						// Get new course and term from crtl
						CourseTermLinkingKey courseTermLinkingKey = new CourseTermLinkingKey();
						courseTermLinkingKey.setInstId(sessionCache
								.getUserSessionDetails().getInstId());
						courseTermLinkingKey.setBranchId(studentPromotionVO
								.getBranchId());
						courseTermLinkingKey.setCourseMasterId(studentGrpMastr
								.getCourseMasterId());
						courseTermLinkingKey.setTermId(studentGrpMastr
								.getTermId());
						CourseTermLinking courseTermLinking = null;
						try {
							courseTermLinking = studentPromotionDAO
									.selectCourseTermForPromotion(courseTermLinkingKey);
						} catch (NoDataFoundException e) {
							System.out.println("select course master no data found for stgroup "+stg);
							stugList.add("No data Found Exception");
						}

						System.out.println("before if stugList size  student group  : "
								+ studentGrpids.get(stg));
						System.out.println("student group  size before if : "
								+ stugList);
						if (stugList.size() == 0) {
							System.out.println("after if stugList size  student group  : "
									+ studentGrpids.get(stg));
							System.out.println("get into detain list  ");
							// Get the detain list
							StudentPromotionKey studentPromotionKey = new StudentPromotionKey();
							studentPromotionKey.setInstId(sessionCache
									.getUserSessionDetails().getInstId());
							studentPromotionKey.setBranchId(studentPromotionVO
									.getBranchId());
							studentPromotionKey.setAcTerm(studentPromotionVO
									.getFromAcademicTerm());
							studentPromotionKey.setStudentGroupId(studentGrpids
									.get(stg));
							List<StudentMaster> stuMstrDetainList = null;
							List<StudentMaster> toBePromoted = new ArrayList<StudentMaster>();
							try {
								stuMstrDetainList = studentPromotionListDAO
										.selectStudentDetainList(studentPromotionKey);
								System.out.println("detain list size   : "
										+ stuMstrDetainList.size());

								for (int dsl = 0; dsl < stuMstrDetainList
										.size(); dsl++) {
									StudentMaster studentmaster = new StudentMaster();
									commonBusiness.changeObject(studentmaster,
											stuMstrDetainList.get(dsl));
									studentmaster
											.setAcademicYear(studentPromotionVO
													.getToAcademicTerm());
									studentmaster.setrCreId(sessionCache
											.getUserSessionDetails()
											.getUserId());
									studentmaster.setrModId(sessionCache
											.getUserSessionDetails()
											.getUserId());
									studentmaster.setDbTs(1);
									studentmaster.setDelFlg("N");
									studentmaster.setInstId(sessionCache
											.getUserSessionDetails()
											.getInstId());
									studentmaster
											.setBranchId(studentPromotionVO
													.getBranchId());
									System.out.println("detained students  : "
											+ studentmaster.toString());
									// Add to common list to insert
									toBePromoted.add(studentmaster);
								}
							} catch (NoDataFoundException e) {
								/*
								 * System.out.println("");
								 * stugList.add("No data Found Exception");
								 */
							}
							// Get all students except detain list
							List<StudentMaster> stuMstrLists = null;
							try {
								stuMstrLists = studentPromotionListDAO
										.selectStudentListForPromotion(studentPromotionKey);

								for (int d = 0; d < stuMstrLists.size(); d++) {
									System.out.println("promotion list  : "
											+ stuMstrLists.get(d).toString());
								}
								System.out.println("student list size   : "
										+ stuMstrLists.size());
								// Create STudent master for each student
								for (int sl = 0; sl < stuMstrLists.size(); sl++) {
									StudentMaster studentmaster = new StudentMaster();
									commonBusiness.changeObject(studentmaster,
											stuMstrLists.get(sl));
									studentmaster
											.setAcademicYear(studentPromotionVO
													.getToAcademicTerm());
									studentmaster.setCourse(courseTermLinking
											.getCourseMasterId());
									studentmaster.setStandard(courseTermLinking
											.getTermId());
									studentmaster.setrCreId(sessionCache
											.getUserSessionDetails()
											.getUserId());
									studentmaster.setrModId(sessionCache
											.getUserSessionDetails()
											.getUserId());
									studentmaster.setDbTs(1);
									studentmaster.setDelFlg("N");
									studentmaster.setInstId(sessionCache
											.getUserSessionDetails()
											.getInstId());
									studentmaster
											.setBranchId(studentPromotionVO
													.getBranchId());

									if ((!stuMstrLists.get(0).getSec()
											.equals(""))
											&& (sl == 0)) {
										String studentGroupId = studentGroupMasterDAO
												.getStuGrpIdForSTUM(
														sessionCache
																.getUserSessionDetails()
																.getInstId(),
														studentPromotionVO
																.getBranchId(),
														courseTermLinking
																.getCourseMasterId(),
														courseTermLinking
																.getTermId(),
														stuMstrLists.get(0)
																.getSec());
										stugrp = studentGroupId;
									}
									studentmaster.setStuGrpId(stugrp);
									// Add to common list to insert
									toBePromoted.add(studentmaster);
								}
								if (stuMstrDetainList != null) {
									System.out.println("detain list size  : "
											+ stuMstrDetainList.size());
								}
								System.out.println("new student grps   : "
										+ stugrp);
								System.out.println("student group  index: "
										+ stg);
								System.out.println("student group  : "
										+ studentGrpids.get(stg));
								System.out.println("to be promoted size   : "
										+ toBePromoted.size());
								for (int i = 0; i < toBePromoted.size(); i++) {
									System.out
											.println("promoted students         : "
													+ toBePromoted.get(i)
															.toString());
								}
								// Insert all details
								if (stugList.size() == 0) {
									try {
										studentPromotionListDAO
												.insertPromotedStudentMaster(toBePromoted);
									} catch (DuplicateEntryException e) {
										System.out.println("insert promoted student master duplicate entry exception"+stg);
										stugList.add("Duplicate Entry Exception");
									}
								}
								System.out.println("error list size   : "
										+ errorList.size());
								System.out.println("error stgm list size   : "
										+ stugList.size());
								for (int e = 0; e < stugList.size(); e++) {
									System.out
											.println("error inside student grp list values  : "
													+ stugList.get(e));
								}

								for (int e = 0; e < errorList.size(); e++) {
									System.out.println("error list values  : "
											+ errorList.get(e));
								}
							} catch (NoDataFoundException e) {
								System.out.println("seelect student list for promotion no data found exception"+stg);
								stugList.add("No data Found Exception");
							}
							System.out.println("student group in lastbut one :" + stg);
						}
						System.out.println("student group in last :" + stg);
					}
				}

				// Update academic term promote status to generated
				if (errorList.size() == 0) {
					try {
						updateAcademicTermDetailsStatus(studentPromotionVO,
								sessionCache, "");
					} catch (NoDataFoundException e) {
						errorList.add("No data Found Exception");
					} catch (UpdateFailedException e) {
						errorList.add("Update Exception");
					}
				}
				// functional audit
				if (errorList.size() == 0) {
					try {
						doAudit.doFunctionalAudit(
								sessionCache.getUserSessionDetails(),
								AuditConstant.HOL_GENERATE,
								"Student promoted "
										+ "successfully from "
										+ studentPromotionVO
												.getFromAcademicTerm()
										+ " to"
										+ studentPromotionVO
												.getToAcademicTerm()
										+ "in Branch : "
										+ studentPromotionVO.getBranchId());
					} catch (DuplicateEntryException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DatabaseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				logger.debug("Completed Functional Auditing");

			}
		});
		t.start();

		return thraedId;
	}

	// Update Academic term promote status
	void updateAcademicTermDetailsStatus(StudentPromotionVO studentPromotionVO,
			SessionCache sessionCache, String threadId)
			throws NoDataFoundException, UpdateFailedException {
		AcademicTermDetailsKey academicTermDetailsKey = new AcademicTermDetailsKey();
		academicTermDetailsKey.setAcTerm(studentPromotionVO
				.getFromAcademicTerm());
		academicTermDetailsKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		academicTermDetailsKey.setBranchId(studentPromotionVO.getBranchId());
		AcademicTermDetails academicTermDetails = academicTermDetailsDAO
				.selectAcademicTermDetailsRec(academicTermDetailsKey);
		commonBusiness
				.changeObject(academicTermDetailsKey, academicTermDetails);
		academicTermDetails.setrModId(sessionCache.getUserSessionDetails()
				.getInstId());
		if ((threadId == "") || (threadId.equals("")) || (threadId == null)) {
			academicTermDetails
					.setPromotionStatus(ApplicationConstant.STUDENT_PROMOTION_GENERATED);
		} else {
			academicTermDetails.setPromotionId(threadId);
			academicTermDetails
					.setPromotionStatus(ApplicationConstant.STUDENT_PROMOTION_STARTED);
		}
		academicTermDetailsDAO.updateAcademicTermDetailsStatusRec(
				academicTermDetails, academicTermDetailsKey);
	}

	@Override
	public String checkAcademicTermPromotiontatus(String threadId,
			String branchId, SessionCache sessionCache)
			throws NoDataFoundException {
		AcademicTermDetailsKey academicTermDetailsKey = new AcademicTermDetailsKey();
		academicTermDetailsKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		academicTermDetailsKey.setBranchId(branchId);
		academicTermDetailsKey.setPromoteStsId(threadId);
		System.out.println("Acaddeic term details key :"
				+ academicTermDetailsKey.toString());
		return academicTermDetailsDAO
				.checkAcademicTermPromoteStatus(academicTermDetailsKey);
	}
}
