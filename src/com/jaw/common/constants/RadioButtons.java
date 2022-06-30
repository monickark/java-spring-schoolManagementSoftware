package com.jaw.common.constants;

public enum RadioButtons {
	  
	  PARENT_USER_ID_NO("No Need to create Parent User Id"),
	  PARENT_USER_ID_YES("Create Parent User Id"),
	  PARENT_USER_ID_EXISTING("Link With Existing Parent User Id");
	 
	   private String description;
	   RadioButtons(String description) {
		   this.description = description;
		   }
	   public String getDescription() {
		   return description;
		   }
	  
}
