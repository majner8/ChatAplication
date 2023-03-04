package TaskConstant;

import java.time.LocalDateTime;

import Exception.SocketComunicationException.CertificatIsNotSameException;
import Exception.SocketComunicationException.EnumSyntaxeError;
import Exception.SocketComunicationException.IncorectCertifikateSocketComunicationException;
import Exception.SocketComunicationException.IncorectFormatTaskSocketComunicationException;
import Exception.SocketComunicationException.IncorrectFormatSocketComunicationException;
import TaskConstant.MainSocketComunication.AplicationInteraction.AplicationInteractionEnum;
import TaskConstant.MainSocketComunication.ChatComunication.ChatComunicationEnum;
import TaskConstant.MainSocketComunication.MainSocketComunicationEnum;
import TaskConstant.MainSocketComunication.UserInteraction.UserInteractionEnum;

// certifikat-unique kod z databaze
// kod odesilatele, UniqueKod odeslane tasku.
//Unique Kod odeslaneho tasku
// nasleduji tasku, v poradi prvni task(Slouzi k Rozrazeni Serverovy apd..) za nim mezera a cislo vyjadrujici soucet vsech nasledujicich tasku
// pote jsou tasky oddeleny separatorem a nasleduji bez mezzer tasky. 

//po posleednim tasku nasleduje v pripade zpravy hraci adresat, v opacnem pripade misto adresata musi byt zadano null!
//popte samotna zprava


/**Class pro zpracovani zaslanych socketu**/
public class SocketComunication {
	
	
	private final static String DevideCharacter="/";
	private final static String finalDevideCharacter="\\/";
	// asdads\UUID zpravy\prvniTask\tasky v poradi\/message
	// UUID adresata\/\/UUID sender\/message
	// pokud je treba napsat \/ pise se s escapovanim tj /\/
	
	private String UNIQUDECodeMessage;
	private final String UUIDSender;
	private final String Recipient;
	private final String Message;
	private final MainSocketComunicationEnum FirstTask;

	
	private UserInteractionEnum [] UserTask=null;
	private ChatComunicationEnum [] ChatTask=null;
	private AplicationInteractionEnum []AplicationTask=null;
	
	private LocalDateTime TimeOfMessage;

	/** Method create new SocketComunication
	 * @param message received message
	 * @param  Certificate Certificate Code for comunication, if certificate was not assign, put null 
	 *@throws IncorectFormatTaskSocketComunicationException If a message does not write by general format, or if a received Enum was not recognize // optat se rodileho mluvciho
	 * @throws CertificatIsNotSameException If a certificat putted as argument is not same as certificate inside message
	 */
	public static SocketComunication CreateMessage(String message,String Certificate) throws IncorectFormatTaskSocketComunicationException, CertificatIsNotSameException {
		// certificat
		// if a recipient is server, value is null
		// if a certificat has not gived yet, certifilat ""
		// certifikat/FirstTask/listOFTask/\/UUID adresata\/\/UUID sender\/message
		//Pokud skupinovy chatListOFTask/skupinovy/OdesilatelUUIDServeruzaToAdresat
		
		String[] tasks=message.split(SocketComunication.finalDevideCharacter,4);
		String RestMessage;
		String recipient;
		String finalMessage;
		String sender;
		String UUIDMessage=tasks[1];
		MainSocketComunicationEnum firstTask=null;
		try {
		RestMessage=tasks[0];
		recipient=tasks[1].trim();
		sender=tasks[2].trim();
		finalMessage=tasks[3].trim();
		}
		catch(IndexOutOfBoundsException e) {
			throw new IncorectFormatTaskSocketComunicationException(String.format("Receive Message %s", message));
		}
		tasks=RestMessage.split(SocketComunication.DevideCharacter);
		if(tasks[0].equals("")) {
			tasks[0]=null;
		}
		if(!tasks[0].equals(Certificate)) {
			throw new CertificatIsNotSameException(String.format("Certificat of received socket is incorenct \n Received certifikat %s \n Certifikat patern %s ",tasks[0],Certificate));
		}


		try {

			firstTask=MainSocketComunicationEnum.valueOf(MainSocketComunicationEnum.class,tasks[2]);

		}
		catch(IndexOutOfBoundsException e) {
			throw new IncorectFormatTaskSocketComunicationException(String.format("Receive Message %s", message));
		}
		catch(IllegalArgumentException e) {
			throw new IncorectFormatTaskSocketComunicationException(String.format("Receive First tasks was not recognize %s", tasks[1]));

		}
		return SocketComunication.CreateSocketComunicationByFirstTask(firstTask, tasks, recipient, finalMessage, sender, UUIDMessage);		//dodelat pokud returns =null;
		
}		

	private static SocketComunication CreateSocketComunicationByFirstTask(MainSocketComunicationEnum firstTask,String[] tasks,String recipient,String finalMessage,String sender,String UUIDMessage
	) {
		if(firstTask==MainSocketComunicationEnum.PotvrzeniPrijetiZpravy) {
			return new SocketComunication(UUIDMessage,sender,recipient,finalMessage,firstTask);
		}
		
		
		if(firstTask==MainSocketComunicationEnum.AplicationInteraction) {
			return new SocketComunication(UUIDMessage,sender,recipient,finalMessage,firstTask,AplicationInteractionEnum.getListOFEnumValue(tasks,3));
		}
		
		if(firstTask==MainSocketComunicationEnum.ChatComunication) {
			return new SocketComunication(UUIDMessage,sender,recipient,finalMessage,firstTask,ChatComunicationEnum.getListOFEnumValue(tasks,3));

		}
		if(firstTask==MainSocketComunicationEnum.UserInteraction) {
			return new SocketComunication(UUIDMessage,sender,recipient,finalMessage,firstTask,UserInteractionEnum.getListOFEnumValue(tasks,3));

		}
		return null;
	}
	
	/** Metod create new SocketComunication Object with different parametr which is putted as argument 
	 * If you want to keep parametr, put null as argument
	 * Method does not support changing First Task*/
	public SocketComunication createCopyWithDifferentParametr(String[] tasks,String recipient,String finalMessage,String sender,String UUIDMessage
		) {
		if(tasks==null) {
			// az budou dodelane metody tasks.. 
			if(this.AplicationTask!=null) {
				tasks=
			}
			else if(this.ChatTask!=null) {
				tasks=
			}
			else if(this.UserTask!=null) {
				tasks=
			}
			else {
				throw new EnumSyntaxeError();
			}
		}
		
		
		if(recipient==null) {
			recipient=this.getRecipient();
		}
		if(finalMessage==null) {
			finalMessage=this.getMessage();
		}
		if(sender==null) {
			sender=this.getUUIDSender();
		}
		if(UUIDMessage==null) {
			UUIDMessage=this.getUNIQUDECodeMessage();
		} 
		
			
		return this.CreateSocketComunicationByFirstTask(this.getFirstTask(), tasks,recipient, finalMessage, sender, UUIDMessage);
	}

	
	
	/** Method return a String value which is representing message, in case with message*/
	public String getOutputMessage(String certifikat) {
	// asdads\UUID zpravy\prvniTask\tasky v poradi\/message
	// UUID adresata\/\/UUID sender\/message
	// pokud je treba napsat \/ pise se s escapovanim tj /\/
	
	String message=certifikat+this.DevideCharacter+this.UNIQUDECodeMessage+this.DevideCharacter+this.FirstTask.toString();
	Object [] tasks=null;
	if(this.UserTask!=null) {
		tasks=this.UserTask;
	}
	if(this.ChatTask!=null) {
		tasks=this.ChatTask;
	}
	if(this.AplicationTask!=null) {
		tasks=this.AplicationTask;
	}
	if(tasks!=null) {
		for(Object x:tasks) {
			message=message+this.DevideCharacter+x.toString();
		}	
	}
		message=message+this.finalDevideCharacter;
		message=message+this.Recipient+this.finalDevideCharacter+this.UUIDSender+this.finalDevideCharacter+this.Message;
		return message;
	
}

	private SocketComunication(String uNIQUDECodeMessage, String uUIDSender, String recipient, 
			String message,
			MainSocketComunicationEnum firstTask, AplicationInteractionEnum []aplicationTask){
		UNIQUDECodeMessage = uNIQUDECodeMessage;
		UUIDSender = uUIDSender;
		Recipient = recipient;
		Message = message;
		FirstTask = firstTask;
		AplicationTask = aplicationTask;
	}



	private SocketComunication(String uNIQUDECodeMessage, String uUIDSender, String recipient, String message,
			MainSocketComunicationEnum firstTask, ChatComunicationEnum[] chatTask) 
{
		
		UNIQUDECodeMessage = uNIQUDECodeMessage;
		UUIDSender = uUIDSender;
		Recipient = recipient;
		Message = message;
		FirstTask = firstTask;
		ChatTask = chatTask;
	}



	private SocketComunication(String uNIQUDECodeMessage, String uUIDSender, String recipient, String message,
			MainSocketComunicationEnum firstTask, UserInteractionEnum[] userTask) {
		
		UNIQUDECodeMessage = uNIQUDECodeMessage;
		UUIDSender = uUIDSender;
		Recipient = recipient;
		Message = message;
		FirstTask = firstTask;
		UserTask = userTask;
	}



	private SocketComunication(String uNIQUDECodeMessage, String uUIDSender, String recipient, String message,
			MainSocketComunicationEnum firstTask) {
		UNIQUDECodeMessage = uNIQUDECodeMessage;
		UUIDSender = uUIDSender;
		Recipient = recipient;
		Message = message;
		FirstTask = firstTask;
	}

	
	public LocalDateTime getTimeOfMessage() {
		return TimeOfMessage;
	}

	public void setTimeOfMessage(LocalDateTime timeOfMessage) {
		TimeOfMessage = timeOfMessage;
	}

	public String getUNIQUDECodeMessage() {
		return UNIQUDECodeMessage;
	}

	public String getUUIDSender() {
		return UUIDSender;
	}

	public String getRecipient() {
		return Recipient;
	}

	public String getMessage() {
		return Message;
	}

	public MainSocketComunicationEnum getFirstTask() {
		return FirstTask;
	}

	public UserInteractionEnum[] getUserTask() {
		return UserTask;
	}

	public ChatComunicationEnum[] getChatTask() {
		return ChatTask;
	}

	public AplicationInteractionEnum[] getAplicationTask() {
		return AplicationTask;
	}
	
	
	
	
	
	
	
}
	
	
	
	


	
	
	
		
		




		



	
	

