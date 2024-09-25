package com.example.demo.controller;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import jakarta.mail.*;
public class email {


private static Message prepareMessage(Session session,String my_AccountEmail,String recepient) {

		Message message=new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(my_AccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("Notification concernant votre demande");
			message.setText("Bonjour,                                                                                                                                                 Nous sommes heureux de vous informer que votre demande a été traitée avec succès. Toutes les informations ont été vérifiées et votre demande a été acceptée.Si vous avez des questions supplémentaires ou si vous avez besoin de plus d'informations, n'hésitez pas à nous contacter.Cordialement,Votre service de demande");
			
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return message;
	}


	public static void sendMail(String recepient){
		
	        Properties properties=new Properties();
	        properties.put("mail.smtp.host", "smtp.gmail.com");
	        properties.put("mail.smtp.port", "587");
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
	        properties.put("mail.smtp.starttls.enable", "true");
	        String my_AccountEmail="testenvoie6@gmail.com";
	        String password="hspcyjbjehektwzw";
	        		
	        Session session=Session.getInstance(properties,new Authenticator(){
	            @Override
	             protected PasswordAuthentication getPasswordAuthentication(){
	            	
	            	return new PasswordAuthentication(my_AccountEmail,password);
	            }
	        });
	        
	        
	
	        Message message =prepareMessage(session,my_AccountEmail,recepient);
	        try {
				Transport.send(message);
			
	        System.out.println("Message sent successfully !");
	        } catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

}



}
