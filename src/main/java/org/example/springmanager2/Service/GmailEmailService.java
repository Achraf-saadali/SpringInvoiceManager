package org.example.springmanager2.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.apache.commons.codec.binary.Base64;
import org.example.springmanager2.Config.GoogleOAuth2Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

@Service
public class GmailEmailService {

    @Autowired
    private GoogleOAuth2Service googleOAuth2Service;

    @Autowired
    private GoogleOAuth2Config googleConfig;

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    /**
     * Envoyer un email de vérification
     */
    public void sendVerificationEmail(String recipientEmail, String verificationCode) throws Exception {
        String subject = "Vérification de votre email";
        String htmlBody = String.format("""
            <html>
                <body style="font-family: Arial, sans-serif; background-color: #f5f5f5; padding: 20px;">
                    <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 40px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                        <h2 style="color: #333; margin-bottom: 20px;">Vérification de votre email</h2>
                        <p style="color: #666; font-size: 16px;">Bonjour,</p>
                        <p style="color: #666; font-size: 16px;">Utilisez le code suivant pour vérifier votre email:</p>
                        <div style="background-color: #f0f0f0; padding: 20px; border-radius: 5px; text-align: center; margin: 30px 0;">
                            <h1 style="color: #007bff; letter-spacing: 5px; margin: 0; font-family: monospace;">%s</h1>
                        </div>
                        <p style="color: #999; font-size: 14px;">Ce code expire dans 15 minutes.</p>
                        <hr style="border: none; border-top: 1px solid #eee; margin: 30px 0;">
                        <p style="color: #999; font-size: 14px;">Cordialement,<br/><strong>L'équipe Facturation</strong></p>
                    </div>
                </body>
            </html>
            """, verificationCode);

        sendEmail(recipientEmail, subject, htmlBody);
    }

    /**
     * Envoyer un email de confirmation de facture
     */
    public void sendInvoiceEmail(String recipientEmail, String invoiceId, String amount) throws Exception {
        String subject = "Votre facture #" + invoiceId;
        String htmlBody = String.format("""
            <html>
                <body style="font-family: Arial, sans-serif; background-color: #f5f5f5; padding: 20px;">
                    <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 40px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                        <h2 style="color: #333; margin-bottom: 20px;">Facture #%s</h2>
                        <p style="color: #666; font-size: 16px;">Bonjour,</p>
                        <p style="color: #666; font-size: 16px;">Veuillez trouver ci-joint votre facture.</p>
                        <div style="background-color: #f9f9f9; padding: 20px; border-left: 4px solid #007bff; margin: 30px 0;">
                            <p style="margin: 0; color: #666;"><strong>Montant:</strong> %s €</p>
                            <p style="margin: 10px 0 0 0; color: #666;"><strong>Date:</strong> %s</p>
                        </div>
                        <p style="color: #666; font-size: 16px;">Merci pour votre confiance.</p>
                        <hr style="border: none; border-top: 1px solid #eee; margin: 30px 0;">
                        <p style="color: #999; font-size: 14px;">Cordialement,<br/><strong>L'équipe Facturation</strong></p>
                    </div>
                </body>
            </html>
            """, invoiceId, amount, java.time.LocalDate.now());

        sendEmail(recipientEmail, subject, htmlBody);
    }

    /**
     * Envoyer un email de réinitialisation de mot de passe
     */
    public void sendPasswordResetEmail(String recipientEmail, String resetToken) throws Exception {
        String subject = "Réinitialisation de votre mot de passe";
        String resetLink = "http://localhost:3000/reset-password?token=" + resetToken;
        String htmlBody = String.format("""
            <html>
                <body style="font-family: Arial, sans-serif; background-color: #f5f5f5; padding: 20px;">
                    <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 40px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                        <h2 style="color: #333; margin-bottom: 20px;">Réinitialisation de mot de passe</h2>
                        <p style="color: #666; font-size: 16px;">Bonjour,</p>
                        <p style="color: #666; font-size: 16px;">Vous avez demandé une réinitialisation de mot de passe. Cliquez sur le bouton ci-dessous:</p>
                        <div style="text-align: center; margin: 30px 0;">
                            <a href="%s" style="background-color: #007bff; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block;">Réinitialiser mon mot de passe</a>
                        </div>
                        <p style="color: #999; font-size: 14px;">Ce lien expire dans 1 heure.</p>
                        <p style="color: #999; font-size: 14px;">Si vous n'avez pas demandé cette réinitialisation, ignorez cet email.</p>
                        <hr style="border: none; border-top: 1px solid #eee; margin: 30px 0;">
                        <p style="color: #999; font-size: 14px;">Cordialement,<br/><strong>L'équipe Facturation</strong></p>
                    </div>
                </body>
            </html>
            """, resetLink);

        sendEmail(recipientEmail, subject, htmlBody);
    }

    /**
     * Envoyer un email personnalisé
     */
    public void sendEmail(String to, String subject, String htmlBody) throws Exception {
        try {
            // Récupérer le token d'accès valide
            String accessToken = googleOAuth2Service.getValidAccessToken(googleConfig.getSenderEmail());

            // Créer les credentials Google avec le token
            GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);

            // Créer le service Gmail
            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName("Facturation App")
                    .build();

            // Créer le message
            Message message = createMessage(
                    googleConfig.getSenderEmail(),
                    to,
                    subject,
                    htmlBody
            );

            // Envoyer
            service.users().messages().send("me", message).execute();
            System.out.println("✅ Email envoyé à: " + to);

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'envoi de l'email: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Créer un message Gmail encodé en Base64
     */
    private Message createMessage(String from, String to, String subject, String htmlBody) throws Exception {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setContent(htmlBody, "text/html; charset=utf-8");

        // Encoder le message en Base64URL
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);

        Message message = new Message();
        message.setRaw(encodedEmail);

        return message;
    }
}