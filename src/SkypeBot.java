import com.skype.Skype;
import com.skype.SkypeException;

public class SkypeBot {

	public static void main(String[] args) throws SkypeException, InterruptedException {
		System.out.printf("\tSkype\n[Installed]:%b\n[Running]:%b\n[DisplayName]:%s\n",Skype.isInstalled(),Skype.isRunning(),Skype.getProfile().getFullName());
		Skype.addChatMessageListener(new CleverBotChatMessageListener());
		Skype.setDaemon(false);
	}
}
