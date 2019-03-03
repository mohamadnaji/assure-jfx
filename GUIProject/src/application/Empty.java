package application;

public class Empty {
	private static int Just_Empty_Int;

	public static int getJust_Empty_Int() {
		return Just_Empty_Int;
	}

	public static void setJust_Empty_Int(int just_Empty_Int) {
		Just_Empty_Int = just_Empty_Int;
	}

	@Override
	public String toString() {
		String test="notice a question mark on file ";
		return "Empty [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}


}
