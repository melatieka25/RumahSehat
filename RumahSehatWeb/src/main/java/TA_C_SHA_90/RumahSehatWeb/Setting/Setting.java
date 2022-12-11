package TA_C_SHA_90.RumahSehatWeb.Setting;

public class Setting {
	// UNCOMMENT THIS BEFORE COMMITTING, COMMENT THIS BEFORE DEVELOPING
    // public static final String CLIENT_BASE_URL = "https://apap-090.cs.ui.ac.id";
	// UNCOMMENT THIS BEFORE DEVELOPING, COMMENT THIS BEFORE COMMITTING
    public static final String CLIENT_BASE_URL = "http://localhost:8080";
    public static final String CLIENT_LOGIN = CLIENT_BASE_URL + "/validate-ticket";
    public static final String CLIENT_LOGOUT = CLIENT_BASE_URL + "/logout";
    public static final String SERVER_BASE_URL = "https://sso.ui.ac.id/cas2";
    public static final String SERVER_LOGIN = SERVER_BASE_URL + "/login?service=";
    public static final String SERVER_LOGOUT = SERVER_BASE_URL + "/logout?url=";
    public static final String SERVER_VALIDATE_TICKET = SERVER_BASE_URL + "/serviceValidate?ticket=%s&service=%s";
}
