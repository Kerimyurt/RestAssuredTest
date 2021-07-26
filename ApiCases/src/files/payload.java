package files;

public class payload {
	
	public static String MissingTitleField()
	{
		return "{\n"
				+ "    \"author\": \"John Smith\"\n"
				+ "}";
	}
	
	public static String MissingAuthorField()
	{
		return "{\n"
				+ "    \"title\": \"Reliability of late night deployments\"\n"
				+ "}";
	}
	
	public static String EmptyAuthorField()
	{
		return "{\n"
				+ "    \"author\": \"\",\n"
				+ "    \"title\": \"Reliability of late night deployments\"\n"
				+ "}";
	}
	
	public static String EmptyTitlerField()
	{
		return "{\n"
				+ "    \"author\": \"John Smith\",\n"
				+ "    \"title\": \"\"\n"
				+ "}";
	}
	
	public static String ContainsIdField()
	{
		return "{\n"
				+ "	\"id\": 1,\n"
				+ "    \"author\": \"John Smith\",\n"
				+ "    \"title\": \"Reliability of late night deployments\"\n"
				+ "}";
	}
	
	public static String RightPayload()
	{
		return "{\n"
				+ "    \"author\": \"John Smith\",\n"
				+ "    \"title\": \"Reliability of late night deployments\"\n"
				+ "}";
	}

}
