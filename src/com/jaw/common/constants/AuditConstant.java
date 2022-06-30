package com.jaw.common.constants;

//Class for Audit Constants
public class AuditConstant {

	// Audit Constants
	public static final String TYPE_OF_OPER_UPDATE = "TYPE_OF_OPER=U";
	public static final String TYPE_OF_OPER_INSERT = "TYPE_OF_OPER=I";
	public static final String TYPE_OF_OPER_DELETE = "TYPE_OF_OPER=D";

	public static final String AUDIT_FLAG_FUNCTIONAL = "F";
	public static final String AUDIT_FLAG_DATABASE = "D";

	public static final String AUDIT_TYPEOFOPER_SEPERATOR = "|";
	public static final String AUDIT_REC_SEPERATOR = "^";

	// AUDIT
	public static final String INSER_AUDIT = "I";

	// Audit sequence
	public static final String AUDIT_SRL_NO_SEQ = "AUDIT_SRL_NO_SEQ";

	// Login codes
	public static final String LOGIN_SUCCESS = "LGN";
	public static final String INVALID_USERNAME = "IUN";
	public static final String WRONG_PASSWORD = "WPW";
	public static final String USER_DISABLED = "UDS";
	public static final String DISABLED_USER = "DSUSR";
	public static final String PASSWORD_RESET_REQUIRED = "PRR";
	public static final String PASSWORD_EXPIRED = "PEX";
	public static final String INVALID_ATTEMPTS = "INA ";

	// Logout codes
	public static final String LOGOUT = "LGO";

	// password change
	public static final String PASSWORD_CHANGE = "LPC";
	public static final String INSM_UPDATE = "IMUS";

	// Roll No Updated code
	public static final String ROLL_NO_UPDATED = "RNU";

	// User Maintenance
	public static final String USER_ENABLE = "A1";
	public static final String USER_DISABLE = "A2";

	// Request flow codes
	public static final String PENDING_REQUEST_UPDATE_SUCCESS = "PRUS";
	public static final String PENDING_REQUEST_UPDATE_FAIL = "PRUF";
	public static final String REQUESTED_FOR_PASSWORDRESET = "RFPRS";

	// Batch Pgm success,failure activity code
	public static final String BATCH_PROGRAM_SUCCESS = "BSUCC";
	public static final String BATCH_PROGRAM_FAIL = "BFAIL";

	// Branch Master
	public static final String BRCM_UPDATE_SUCCESS = "BRUS";
	public static final String BRCM_UPDATE_FAIL = "BRUF";
	public static final String BRCM_INSERT_SUCCESS = "BRAS";
	public static final String BRCM_INSERT_FAIL = "BRAF";
	public static final String BRCM_DELETE_SUCCESS = "BRDS";
	public static final String BRCM_DELETE_FAIL = "BRDF";

	// User Creation
	public static final String MGMT_USER_CREATE = "MGA";
	public static final String NSTF_USER_CREATE = "NSFA";
	public static final String STF_USER_CREATE = "SFA";
	public static final String STF_BR_CREATE = "STBRP";

	// Staff
	public static final String STAFF_UPDATED = "SFM";
	public static final String STAFF_DELETD = "SFD";
	public static final String STAFF_TRANSFERED = "SFT";

	// New Admission
	public static final String NEW_ADMISSION_SUCCESS = "ADSU";
	// Holiday Generation
	public static final String HOL_GENERATE = "HOLGE";
	public static final String HOL_UPDATE = "HOLUP";
	// courseclasses
	public static final String COURSE_CLASSES_BATCH_CREATE = "CCBAS";
	public static final String COURSE_CLASSES_CREATE = "CCAS";
	public static final String COURSE_CLASSES_UPDATE = "CCUS";
	public static final String COURSE_CLASSES_DELETE = "CCDS";

	// courseSub
	public static final String COURSE_SUBJECT_CREATE = "CSAS";
	public static final String COURSE_SUBJECT_UPDATE = "CSUS";
	public static final String COURSE_SUBJECT_DELETE = "CSDS";

	// Course Master
	public static final String CRM_INSERT = "CRMI";
	public static final String CRM_UPDATE = "CRMU";
	public static final String CRM_DELETE = "CRMD";
	// Student Group Master
	public static final String SGM_INSERT = "SGMI";
	public static final String SGM_UPDATE = "SGMU";
	public static final String SGM_DELETE = "SGMD";
	// Academic Calendar
	public static final String ACADCAL_INSERT = "ACAI";
	public static final String ACADCAL_UPDATE = "ACAU";
	public static final String ACADCAL_DELETE = "ACAD";
	// Academic Term
	public static final String ACADTERM_INSERT = "ACATI";
	public static final String ACADTERM_UPDATE = "ACATU";
	public static final String ACADTERM_DELETE = "ACATD";
	// Teacher Subject Link
	public static final String TSL_INSERT = "TSLI";
	public static final String TSL_UPDATE = "TSLU";
	public static final String TSL_DELETE = "TSLD";
	// Mark Master
	public static final String MRK_MTR_INSERT = "MMTRI";
	public static final String MRK_MTR_UPDATE = "MMTRU";
	public static final String MRK_MTR_DELETE = "MMTRD";
	// Attendance
	public static final String ATTENDANCE_UPDATE_SUCCESS = "ATUS";
	// Mark Attendance
	public static final String MARK_ATTENDANCE = "MKATT";
	// Marks
	public static final String MARK_ADD = "MKADD";
	public static final String MARK_UPDATE = "MKUPD";
	public static final String MARK_DELETE = "MKDEL";

	// Mark Sub
	public static final String MARK_SUBJECT_CREATE = "MSAS";
	public static final String MARK_SUBJECT_UPDATE = "MSUS";
	public static final String MARK_SUBJECT_DELETE = "MSDS";
	// Special Class
	public static final String SPL_CLS_INSERT = "SPLCI";
	public static final String SPL_CLS_UPDATE = "SPLCU";
	public static final String SPL_CLS_DELETE = "SPLCD";
	// Subject Master
	public static final String SUB_MASTER_INSERT = "SUBMI";
	public static final String SUB_MASTER_UPDATE = "SUBMU";
	public static final String SUB_MASTER_DELETE = "SUBMD";
	public static final String SUB_MASTER_DEL_INSERT = "SUBDI";
	// Constant for Student update
	public static final String STU_UPDATE_SUCCESS = "STUS";
	// Additional Holidays
	public static final String ADDL_HOL_INSERT = "ADDLI";
	public static final String ADDL_HOL_UPDATE = "ADDLU";
	public static final String ADDL_HOL_DELETE = "ADDLD";

	// Report Card
	public static final String REMARKS_ENTERED_SUCCESSFULLY = "RCRME";
	public static final String REMARKS_UPDATED_SUCCESSFULLY = "RCRMU";
	public static final String REPORTCARD_GENERATED_SUCCESSFULLY = "RCGEN";
	public static final String RESULT_PUBLISHED_SUCCESSFULLY = "RCPUB";
	public static final String MARKS_LOCKED = "MRKLK";
	public static final String MARKS_CLOSED = "MRKCL";
	// Notice Board
	public static final String NOTICE_BOARD_INSERT = "NCBDI";
	public static final String NOTICE_BOARD_UPDATE = "NCBDU";
	public static final String NOTICE_BOARD_DELETE = "NCBDD";
	// Alert
	public static final String ALERT_INSERT = "ALRTI";
	public static final String ALERT_UPDATE = "ALRTU";
	public static final String ALERT_DELETE = "ALRTD";
	// SMS Request
	public static final String SMS_RQST_INSERT = "SMSRI";
	public static final String SMS_RQST_UPDATE = "SMSRU";
	// SMS Details
	public static final String SMS_DETAILS_INSERT = "SMSDI";
	public static final String SMS_DETAILS_UPDATE = "SMSDU";
	// SMS Config
	public static final String SMS_CONFIG_UPDATE = "SMSCU";
	// CLass teacher allotment
	public static final String CLASS_TEACHER_ALLOTTED = "CLTA";
	public static final String CLASS_TEACHER_DELETED = "CLTD";
	// Fee Category Linking
	public static final String FEE_CATEGORY_LINKING_ADDED = "FCLA";
	public static final String FEE_CATEGORY_LINKING_DELETED = "FCLD";
	// Course Term Linking
	public static final String COURSE_TRM_LINK_INSERT = "CRTLI";
	public static final String COURSE_TRM_LINK_DELETE = "CRTLD";
	// Transport 
	public static final String TRANSPORT_MASTER_ADD = "TRNMA";
	public static final String TRANSPORT_MASTER_UPDATE = "TRNMU";
	public static final String TRANSPORT_MASTER_DELETE = "TRNMD";
	// Student transport
	public static final String STU_TRANSPORT_ADD = "STRNA";
	
	// Fee Term
	public static final String FEES_TERM_INSERT = "FTRMI";
	public static final String FEES_TERM_DELETE = "FTRMD";
	// Fee Payment Detail
	public static final String FEES_PAYMENT_DETAIL_INSERT = "FPTDI";
	public static final String FEES_PAYMENT_DETAIL_DELETE = "FPTDD";
	public static final String SEC_ALLOC_SUCCESS = "SALS";
	// Fee master Linking
	public static final String FEE_MASTER_ADDED = "FMSA";
	public static final String FEE_MASTER_UPDATED = "FMSU";
	public static final String FEE_MASTER_DELETED = "FMSD";
	public static final String UPDATE_STU_DETAIN_SUCCESS = "USDS";
	public static final String DELETE_STU_DETAIN_SUCCESS = "DSDS";
	public static final String BATCH_INSERT_STU_DETAIN_SUCCESS = "SBDS";
	// Student Promotion
	public static final String STUDENT_PROMOTION = "STUPN";
	public static final String STUDENT_TRAN_SUCCESS = "STRS";
}
