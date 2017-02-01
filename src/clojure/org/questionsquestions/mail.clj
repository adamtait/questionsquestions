(ns org.questionsquestions.mail
  (:require [org.questionsquestions.log :refer [log log-error]])
  (:import
   (java.util Properties)
   (javax.mail Message MessagingException Session Transport)
   (javax.mail.internet AddressException InternetAddress MimeMessage)))

(defn ^InternetAddress email-address
  [address name]
  (InternetAddress. address name))

(defn source-email-address []
  (email-address
   "noreply@questionsquestions.appspotmail.com"
   "questionsquestions.org form"))

(defn send-email [^String dest-address subject message]
  (let [props (Properties.)
        session (Session/getDefaultInstance props)]
    (try
      (let [src-address (source-email-address)
            msg (doto
                    (MimeMessage. session)
                  (.setFrom src-address)
                  (.addRecipients javax.mail.Message$RecipientType/TO dest-address)
                  (.setSubject subject)
                  (.setText message))]
        (Transport/send msg))
      (catch AddressException e
        (log-error e "Unable to send mail"))
      (catch MessagingException e
        (log-error e "Unable to send mail")))))



(def dest-addresses [])  ;; should load from environment variable

(defn send-new [from-name message]
  (doseq [addr dest-addresses]
    (send-email
     addr
     (str "New question from " from-name)
     message)))
