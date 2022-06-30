package com.jaw.common.constants;

//Constant Class for Application
public class ApplicationConstant {

	public static final int SIBLING_UITEXTBOX_COUNT = 4;

	// File Type for FileMaster DB
	public static final String INSTITUTE_BRANCH_LOGO_KEY = "PH_LOGO";
	public static final String STUDENT_PHOTO_KEY = "PH_SPHO";
	public static final String STUDENT_BIRTHC_KEY = "FL_BIRT";
	public static final String STUDENT_CASTEC_KEY = "FL_CAST";
	public static final String FATHER_PHOTO_KEY = "PH_FPHO";
	public static final String MOTHER_PHOTO_KEY = "PH_MPHO";
	public static final String GUARDIAN_PHOTO_KEY = "PH_GPHO";
	public static final String TRANSFER_CERT_KEY = "FL_TRAN";
	public static final String STUDENT_MARKS_KEY = "FL_MARK";
	public static final String TRASSIT_PHOTO_KEY = "PH_APHO";
	public static final String MANAGEMENT_PHOTO = "PH_MGMT";
	public static final String NON_STAFF_PHOTO = "PH_NSTA";
	public static final String STAFF_PHOTO = "PH_STA";
	public static final String STUDENT_MEDICALC_KEY = "FL_MEDI";
	public static final String STUDENT_SPORTS_CERT_KEY = "FL_SPCR";
	public static final String STAFF_CERT_KEY = "FL_STCR";
	public static final String STAFF_BIODATA_KEY = "FL_STBD";
	public static final String PAYMENT_STATUS_FEE_PAID = "FPS1";

	// Constant For Cache used in controller classes
	public static final String APPLICATION_CACHE = "application";
	public static final String SESSION_CACHE_KEY = "session";

	// Profile Group
	public static final String PG_STUDENT = "STU";
	public static final String PG_PARENT = "PAR";
	public static final String PG_STAFF = "STF";
	public static final String PG_NON_STAFF = "NSF";
	public static final String PG_MGMT = "MGT";
	// Menu Profile
	public static final String BRANCH_ADMIN = "BRADM";
	public static final String MANAGEMENT_MENU_PROFILE = "MGMT";
	public static final String STUDENT = "STUDT";
	public static final String PARENT = "PARNT";
	public static final String SUPER_ADMIN = "SUADM";
	public static final String STAFF = "STAFF";
	public static final String REGISTRAR = "REGR";
	public static final String TEACHING_STAFF = "TSF";

	// BatchSize for sequence Generation
	public static final int BATCH_ID_GEN_INC = 100;
	public static final int SIMPLE_ID_GEN_INC = 1;

	// UserCreation Default values
	public static final String LAST_LOGOUT_TIME = "2013-03-18 12:18:35";

	// Excel file upload - Constant for different Data Types
	public static final String BATCH_PGMS_XML_STUDENTINFORMATION = "ADSI";
	public static final String BATCH_PGMS_XML_PARENTINFORMATION = "ADPI";
	public static final String BATCH_PGMS_XML_TRANSPORTINFORMATION = "ADTI";
	public static final String BATCH_PGMS_XML_CONSOLIDATED = "ADCO";

	// Constants for Batch programs
	public static final String BATCH_PGMS_EXCEL_IMPORT = "EXIM";
	public static final String BATCH_PGMS_EXCEL_EXPORT = "EXEX";
	public static final String BATCH_PGMS_INITIALIZATION = "BINIT";
	public static final String BATCH_PGMS_SUSPENDED = "BFAIL";
	public static final String BATCH_PGMS_SUCCESS = "BSUCC";
	public static final String BATCH_PGMS_FILE_IMPORT = "FLIM";
	public static final String FILE_IMPORT_DATA_TYPE = "Not Applicable";
	public static final String BATCH_PGMS_FILE_TRANS_DB_TO_D = "BDBD";
	public static final String BATCH_PGMS_FILE_TRANS_D_TO_DB = "BDDB";

	// Constants for Date Format
	public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

	// Sequence String to be appended with Sequence
	public static final String STRING_BRANCH_SEQ = "BR";
	public static final String STRING_MANAGEMENT_USER_SEQ = "MGMT";
	public static final String STRING_NON_STAFF_SEQ = "NSTF";
	public static final String STRING_FILE_MASTER_SEQ = "FR";
	public static final String STRING_FILE_HISTORY_SEQ = "FHR";
	public static final String STRING_ACAD_CALENDAR_SEQ = "ACCL";
	public static final String STRING_COURSE_MASTER_SEQ = "CM";
	public static final String STRING_STUD_GRP_MASTER_SEQ = "SGM";
	public static final String ADDL_HOLIDAYS_SEQ = "ADDH";
	public static final String STRING_COURSE_SUBJECT_LINK_SEQ = "CSL";
	public static final String STRING_COURSE_CLASSES_LINK_SEQ = "CCL";
	public static final String STRING_MARK_SUBJECT_LINK_SEQ = "MSL";
	public static final String TR_SUB_LINK_SEQ = "TRSL";
	public static final String SPL_CLS_SEQ = "SPLC"; // Constant For Data Log
	public static final String DATA_LOG_CONSTANT = "D";
	public static final String MARK_MASTER_SEQ = "MM";
	public static final String ATT_SEQ = "ATT";
	public static final String ATT_MTR_SEQ = "ATMR";
	public static final String ATT_ABS_SEQ = "ATAB";
	public static final String SUB_MTR_SEQ = "SUB"; // Constant For Event Log
	public static final String EVENT_LOG_CONSTANT = "F";
	public static final String STRING_NOTICE_BOARD_SEQ = "NCBD";
	public static final String STRING_ALERT_SEQ = "ALRT";
	public static final String STRING_SMS_RQST_SEQ = "SMSR";
	public static final String STRING_CLS_TCHR_ALTMNT_SEQ = "CLTA";
	public static final String STRING_FEE_RECEIPT = "FRCT";
	public static final String STRING_STUD_FEE_DEMAND_SEQ = "SDM";
	public static final String STRING_TRANSPORT_MASTER_SEQ = "TRS";
	// xml file location
	public static final String XML_FILE_LOCATION = "//WEB-INF//properties//";

	// Request Constant
	public static final String REQUEST_TYPE_NEW_PASSWORD = "R1";
	public static final String REQUEST_STATUS_REQUESTED = "R";
	public static final String REQUEST_STATUS_CLOSED = "C";
	// Constant For Academic Term Item Type
	public static final String ACT_TRM_ITEM_HOL = "H";
	// User constant
	public static final String PASSWORD_RESET_REQUIRED = "Y";
	public static final String PASSWORD_RESET_NOT_REQUIRED = "N";
	public static final String USER_ELABLE_FLG = "Y";
	public static final String USER_DISABLE_FLG = "N";
	public static final String USER_MAX_INVALID_ATTEMPTS_HOLD = "D";

	//
	public static final String INSP = "Institute Specified";

	// Parent type in New Admission-Radio Button Constants
	public static final String PARENT_NEW = "N";
	public static final String PARENT_EXISTING = "E";
	public static final String CREATE_STUDENT_USR = "YES";
	public static final String PARENT_USR_NEW = "PARENT_USER_ID_YES";
	public static final String PARENT_USR_NO = "PARENT_USER_ID_NO";
	public static final String PARENT_USR_EXISTING = "PARENT_USER_ID_EXISTING";

	// Create User Constants
	public static final String CREATE_PARENT_USER_FLAG = "YES";
	public static final String CREATE_STUDENT_USER_FLAG = "YES";

	// COCD
	public static final String COCD_COMPULSARY_SUBJECT = "CSUB";
	public static final String COCD_OPTIONAL_SUBJECT = "OSUB";
	public static final String COCD_LANGUAGE1 = "LANG2";
	public static final String COCD_LANGUAGE2 = "LANG3";
	public static final String CURRENT_YEAR_CONSTANT = "P";

	// Constant For Academic Term Status
	public static final String ACT_TRM_CLOSED = "C";
	public static final String ACT_TRM_FUTURE = "F";
	public static final String ACT_TRM_PRESENT = "P";
	public static final String ACT_TRM_PREVIOUS = "R";
	// Constant for Mark Subject Linking
	public static final String MARK_SUB_LINK_OPEN = "O";

	// constant For Attendance Present/Absent
	public static final String ATT_PRESENT = "P";
	public static final String ATT_ABSENT = "A";
	// constant For Subject Type
	public static final String SUB_TYPE_COMPULSARY = "C";
	public static final String SUB_TYPE_ELECTIVE1 = "E1";
	public static final String SUB_TYPE_ELECTIVE2 = "E2";
	public static final String SUB_TYPE_LANGUAGE3 = "L3";
	public static final String SUB_TYPE_LANGUAGE2 = "L2";
	public static final String SUB_TYPE_RELIGIOUS = "R";

	// constant For Class Type
	public static final String CLASS_TYPE_PRACTICAL = "CLSP";
	public static final String CLASS_TYPE_THEORY = "CLST";
	// Constant for Attendance
	public static final String ATTENDANCE_STATUS_ENTERED = "E";
	public static final String ATTENDANCE_STATUS_LOCKED = "L";
	// Marks Constant
	public static final String MARKS_STATUS_OPEN = "O";
	public static final String MARKS_STATUS_ENTERED = "E";
	public static final String MARKS_STATUS_ENTERED_ALL = "A";
	public static final String REMARKS_ENTERED = "R";
	public static final String MARKS_LOCKED = "L";
	public static final String REPORT_CARD_GENERATED = "G";
	public static final String REPORT_CARD_PUBLISHED = "P";
	public static final String MARKS_CLOSED = "C";

	// Constant File Serial No
	public static final String DEFAULT_FILE_SRL_NO = "1";
	// Marks Or Grade
	public static final String MARK = "MG1";
	public static final String GRADE = "MG2";

	// Theory Or Practical

	public static final String THEORY = "CLST";
	public static final String PRACTICAL = "CLSP";

	// Del Flg

	public static final String DEL_FLG_TRANSFERED = "T";

	// Column Name Constants
	public static final String COURSE_VARIANT = "COURSE_VARIANT";
	public static final String SEC_LANG = "SECOND_LANG";
	public static final String THIRD_LANG = "THIRD_LANG";
	public static final String ELE_1 = "ELECTIVE_1";
	public static final String ELE_2 = "ELECTIVE_2";
	public static final String STU_BAT = "STUDENT_BATCH";
	public static final String LAB_BAT = "LAB_BATCH";
	public static final String REL_SUB = "RELIGIOUS_SUB";
	public static final String HOU_NAM = "HOUSE_NAME";
	public static final String REG_NO = "REG_NO";

	// Constant for Roll No Generation Orders
	public static final String ORDER_2_GEN1 = "001";
	public static final String ORDER_2_GEN2 = "002";
	public static final String ORDER_1_SORT1 = "001";
	public static final String ORDER_1_SORT2 = "002";
	// Constant for Not Applicable
	public static final String NOT_APPLICABLE = "NA";

	// Constant for Yes
	public static final String INFORM_PARENT = "Y";
	// Constant for General Groups
	public static final String GENERIC_GRP_PARENT = "PAR";
	public static final String GENERIC_GRP_STUDENT = "STU";
	public static final String GENERIC_GRP_STAFF = "STA";
	public static final String GENERIC_GRP_MGMT = "MGT";
	// Constant for SMS Details Status
	public static final String RQST_ENTERED = "E";
	public static final String API_CALLED = "A";
	public static final String DELIVERED = "D";
	public static final String UNDELIVERED = "U";
	public static final String FAILED = "F";
	// Constant for SMS Request Status
	public static final String SMS_RQST_ENTERED = "E";
	public static final String IN_PROGRESS = "I";
	public static final String CLOSED = "C";
	// Constant for SMS Request Category
	public static final String SPECIFIC_MEMBER_LIST = "M";
	public static final String GENERIC_GROUPS = "G";
	public static final String SPECIFIC_GROUPS = "S";
	// Constant for , Seperator
	public static final String COMMA_SEPERATOR = ",";
	// Constant for , SMS Configuration type
	public static final String SMS_CONFIG_URL = "URL";
	// Attendance SEND sms Rqrd
	public static final String ABSENT_SMS_REQUIRED = "Y";
	// Admission SEND sms Rqrd
	public static final String ADMISSION_SMS_REQUIRED = "Y";
	// Constant for Alert type
	public static final String ATTENDANCE_ALERT = "ABST";
	public static final String ADMISSION_ALERT = "NAM";
	public static final String FEES_ALERT = "FEES";
	public static final String PASSWORD_RESET_ALERT = "PWRST";

	// Constant for file path for storage
	public static final String CONFIGURED_PATH_PRS_STU = "prs_stu/";
	public static final String CONFIGURED_PATH_PRS_STF = "prs_staff/";
	public static final String CONFIGURED_PATH_PRV_STU = "prv_stu/";
	public static final String CONFIGURED_PATH_PRV_STF = "prv_staff/";

	public static final String BATCH_OPERATION = "BATCH";
	public static final String FILE_IN_DB = "Y";
	public static final String PHOTO_CONSTANT = "PH";

	public static final String CONFIGURED_PATH_OLD_FILE_STORAGE_STU = "old_stu/";
	public static final String CONFIGURED_PATH_OLD_FILE_STORAGE_STF = "old_staff/";

	public static final String OLD_FILE_STORAGE = "OLD";

	public static final String BATCH_FILE_NAME_SEPERATOR_1 = "-";
	public static final String BATCH_FILE_NAME_SEPERATOR_2 = "/";

	// public static final String CATALINA_PATH =
	// System.getProperty("catalina.base");
	public static final String PROPERTY_FILE_FOR_FILE_STORAGE_NAME = "filesStoragePathConfig.properties";
	public static final String PROPERTY_IN_FILE_LOCATION_PROPFILE = "file_location";

	public static final String FILE_SEQ_CONSTANT = "FR";

	public static final String LINUX_OS = "Linux";

	public static final String DISK_TO_DB_CONSTANT = "FDDB";
	public static final String DB_TO_DISK_CONSTANT = "FDBD";
	public static final String MENTOR_IS_APPLICABLE = "Y";

	public static final String PREFIX_STUDENT_USERID = "S";
	public static final String PREFIX_PARENT_USERID = "P";
	public static final String CONST_SPLIT_DOB = "-";

	public static final String YES = "Y";
	public static final String NO = "N";

	// Fee Status
	public static final String FEE_STATUS_ENTERED = "E";
	public static final String FEE_STATUS_LOCKED = "L";
	public static final String FEE_STATUS_GENERATED = "G";

	public static final String COURSE_VARIANT_APPLICABLE_YES = "Y";
	public static final String COURSE_VARIANT_APPLICABLE_NO = "N";
	// Promotion status in academic term
	public static final String STUDENT_PROMOTION_OPENED = "O";
	public static final String STUDENT_PROMOTION_STARTED = "S";
	public static final String STUDENT_PROMOTION_GENERATED = "G";

	public static final String STU_TRANSFER_CONST = "Y";
	public static final String STU_TRANSFER_DEL_FLG = "T";
	
	public static final String TRANSPORT_UPDATE = "TRANS";




}
