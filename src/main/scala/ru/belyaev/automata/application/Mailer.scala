package ru.belyaev.automata.application

import java.util.Properties

import com.typesafe.config.{Config, ConfigFactory}
import javax.mail.internet.{InternetAddress, MimeBodyPart, MimeMessage, MimeMultipart}
import javax.mail.{Authenticator, Message, PasswordAuthentication, Session}


class Mailer {

  private val conf: Config = ConfigFactory.load()

  private val props = new Properties()
  props.put("mail.smtp.auth", Boolean.box(true))
  props.put("mail.smtp.host", conf.getString("smtp.server"))
  props.put("mail.smtp.port", "25")

  private val session = Session.getInstance(props, new Authenticator {
    override def getPasswordAuthentication: PasswordAuthentication = {
      val username = conf.getString("smtp.username")
      val password = conf.getString("smtp.password")
      new PasswordAuthentication(username, password)
    }
  })

  private val mailFromAddr = conf.getString("smtp.mail-from-addr")
  private val mailToAddr = conf.getString("smtp.mail-to-addr")

  def sendMsg(content: String): Unit = {
    val mimeBodyPart = new MimeBodyPart()
    mimeBodyPart.setContent(content, "text/html")

    val multipart = new MimeMultipart()
    multipart.addBodyPart(mimeBodyPart)

    val message = new MimeMessage(this.session)
    message.setFrom(new InternetAddress(this.mailFromAddr))
    message.setRecipient(Message.RecipientType.TO, new InternetAddress(this.mailToAddr))
    message.setSubject("Cloud resources have exceeded runtime threshold")
    message.setContent(multipart)

    import javax.mail.Transport
    Transport.send(message)
  }
}
