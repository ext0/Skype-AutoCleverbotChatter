import java.util.ArrayList;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import com.skype.Chat;
import com.skype.ChatMessage;
import com.skype.ChatMessageListener;
import com.skype.SkypeException;
import com.skype.User;

public class CleverBotChatMessageListener implements ChatMessageListener{
	ArrayList<ChatterBotSession>sessions=new ArrayList<ChatterBotSession>();
	ArrayList<User>locks=new ArrayList<User>();
	String[]userIgnoreList={""};
	ChatterBotFactory factory = new ChatterBotFactory();
	public void chatMessageReceived(ChatMessage message) throws SkypeException {
		System.out.println("[DEBUG] Message received");
		String thought;
		boolean found=false;
		Chat c=message.getChat();
		String display=message.getSenderDisplayName();
		for (String name:userIgnoreList){
			if (display.contains(name)){ System.out.println("Message sender is on ignore list! ABORT!"); return; }
		}
		System.out.printf("Incoming chat:\n[%s:%s]\n\n",display,message.getContent());
		for (ChatterBotSession s:sessions){
			if ((s.getOwner().equals(display))&&(!isIn(locks,message.getSender()))){
				locks.add(message.getSender());
				System.out.printf("Chat message from existing session:\n[%s:%s]\n\n",display,message.getContent());
				found=true;
				try {
					thought=s.think(message.getContent());
					c.send(thought);
					removeIn(locks,message.getSender());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (isIn(locks,message.getSender())){
			System.out.printf("Overflow chat message from existing session:\n[%s:%s]\n\n",display,message.getContent());
			c.send("[Still processing!]");
			found=true;
		}
		else if (found==false){
			locks.add(message.getSender());
			System.out.printf("Chat message from user without session:\n[%s:%s]\n\n",display,message.getContent());
			ChatterBot bot1 = null;
			try {
				bot1 = factory.create(ChatterBotType.CLEVERBOT);
			} catch (Exception e) {
				e.printStackTrace();
			}
			sessions.add(bot1.createSession(display));
			String s=null;
			try {
				s = sessions.get(sessions.size()-1).think(message.getContent());
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.printf("Sending thought back to sender:\n[%s > %s]\n\n",s,display);
			c.send(s);
			removeIn(locks,message.getSender());
		}
	}

	public void chatMessageSent(ChatMessage message) throws SkypeException {

	}
	public static boolean isIn(ArrayList<User>a,User b){
		for (Object c:a){
			if (c.equals(b)) return true;
		}
		return false;
	}
	public static void removeIn(ArrayList<User>a,User b){ //??????
		for (int i=a.size()-1; i> -1; i--) {			  //do not touch, magic
		    if (a.get(i).equals(b) ) {
		        a.remove(i);
		    }
		}
	}

}
