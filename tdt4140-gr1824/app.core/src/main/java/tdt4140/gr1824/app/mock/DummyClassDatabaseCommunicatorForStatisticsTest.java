package tdt4140.gr1824.app.mock;

public class DummyClassDatabaseCommunicatorForStatisticsTest {
	public int[] getUserStats(int userID) {
		int[] testStatUser = {50, 40, 37, 70};
		return testStatUser;
	}

	public int[] getAllStats() {
		int[] testStatAllUsers = {60, 30, 37, 70};
		return testStatAllUsers;
	}

	public int[] getGroupStats(String groupID) {
		int[] testStatGroup = {120, 320, 420, 200};
		return testStatGroup;
	}
}
