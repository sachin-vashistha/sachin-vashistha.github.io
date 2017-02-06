package com.example;

import java.io.IOException;
import java.security.Security;
import java.util.Properties;
import javax.ejb.EJB;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sachin vashistha
 */
public class EmailServlet extends HttpServlet {
    Proxy_info proxy_info;
    String[] prox=new String[2];
    String host,port;
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println("inside servlet");
    proxy_info=new Proxy_info();
    prox=proxy_info.checkProxy();
    host=prox[0];
    port=prox[1];
    String name=request.getParameter("send_name");
    String email=request.getParameter("send_email");
    String mess_=request.getParameter("send_mess");
     String to="sachinvashistha6916@gmail.com";
     System.out.println(mess_);
  Properties props = new Properties();  
 /*  props.setProperty("proxySet","true");
   props.setProperty("socksProxyHost",host);
   props.setProperty("socksProxyPort",port);*/
   props.put("mail.smtp.user", to);
   props.put("mail.smtp.host", "smtp.gmail.com");  
   props.put("mail.smtp.socketFactory.port", "587");  
   props.put("mail.smtp.port", "587");
   props.put("mail.smtp.starttls.enable","true");
   props.put("mail.smtp.debug", "true");
   props.put("mail.smtp.auth", "true");
   props.put("mail.smtp.socketFactory.class",  
            "javax.net.ssl.SSLSocketFactory");  
  props.put("mail.smtp.socketFactory.fallback", "false");
     Session session = Session.getInstance(props,  
   new javax.mail.Authenticator() {  
   @Override
   protected PasswordAuthentication getPasswordAuthentication() {  
   return new PasswordAuthentication("sachinkuk6196@gmail.com","omomom33sacv"); 
   }  
  });  
     try {  
   MimeMessage message = new MimeMessage(session);  
   message.setFrom(new InternetAddress("sachinkuk6196@gmail.com"));
   message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
   message.setSubject("Queries regarding service provided by CyberAin Solutions Private Limited");  
   Multipart multipart = new MimeMultipart("alternative");
   BodyPart messageBodyPart = new MimeBodyPart();
   messageBodyPart.setText("This message send by "+name+" whose email id is "+email+". Message send by "+name+" is:"+"\n\n\n"+mess_);
     multipart.addBodyPart(messageBodyPart);
    message.setContent(multipart);
   Transport.send(message);  
  
   System.out.println("message sent successfully");  
   
  } catch (MessagingException e) {throw new RuntimeException(e);}  
     
      RequestDispatcher rd=request.getRequestDispatcher("result.jsp");
     rd.forward(request, response);
     System.out.println("done everything");
}
}