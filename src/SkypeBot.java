import com.skype.Skype;
import com.skype.SkypeException;

public class SkypeBot {

	public static void main(String[] args) throws SkypeException, InterruptedException {
		System.out.printf("[Installed]  :%b\n[Running]    :%b\n[DisplayName]:%s\n[Status]     :%s\n\n",Skype.isInstalled(),Skype.isRunning(),Skype.getProfile().getFullName(),Skype.getProfile().getStatus().name());
		Skype.addChatMessageListener(new CleverBotChatMessageListener());
		Skype.setDaemon(false);
		if (!Skype.isRunning()){ System.out.printf("Permissions failure! Accept access request on Skype\nclient and restart.\n\n"); System.exit(-1);}
		System.out.printf("Hooked in successfully to Skype V%s\n\n",Skype.getVersion());
	}
}
