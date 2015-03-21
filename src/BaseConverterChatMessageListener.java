import java.util.ArrayList;

import com.skype.Chat;
import com.skype.ChatMessage;
import com.skype.ChatMessageListener;
import com.skype.SkypeException;
import com.skype.User;

public final class BaseConverterChatMessageListener implements ChatMessageListener {
	ArrayList<User>binary=new ArrayList<User>();
	ArrayList<User>hex=new ArrayList<User>();
	public void chatMessageReceived(final ChatMessage message) throws SkypeException {
		final Chat chat = message.getChat();
		if (message.getContent().contains("binary")){
			chat.send("Switching to BASE 2");
			if (find(message.getSender(),hex)!=-1){
				hex.remove(find(message.getSender(),hex));
			}
			binary.add(message.getSender());
		}
		else if (message.getContent().contains("hex")){
			chat.send("Switching to BASE 16");
			if (find(message.getSender(),binary)!=-1){
				binary.remove(find(message.getSender(),binary));
			}
			hex.add(message.getSender());
		}
		else if ((message.getContent().contains("what"))||(message.getContent().equals("?"))){
			chat.send("This is just a test for a hexadecimal/binary Java API converter.");
			chat.send("You are not talking to a person, but to a GNU public licensed program.");
			chat.send("Say \"binary\" or \"hex\" to swap conversions.");
		}
		if (has(message.getSender(),hex)) chat.send(asciiToHex(message.getContent()));
		else chat.send(asciiToBinary(message.getContent()));
	}
	public void chatMessageSent(ChatMessage message){
		
	}
	public static boolean has(Object a,ArrayList<?>list){
		if (find(a,list)!=-1) return true;
		return false;
	}
	public static int find(Object a,ArrayList<?>list){
		int i=0;
		for (Object b:list){
			if (b.equals(a)) return i;
			i++;
		}
		return -1;
	}
	public static String asciiToHex(String s){
		StringBuilder toRet=new StringBuilder();
		for (char c:s.toCharArray())
			toRet.append(Integer.toHexString(c));
		return "0x"+toRet.toString();
	}
	public static String asciiToBinary(String s){
		StringBuilder toRet=new StringBuilder();
		for (char c:s.toCharArray())
			toRet.append(Integer.toBinaryString(c));
		return "0b"+toRet.toString();
	}
}

