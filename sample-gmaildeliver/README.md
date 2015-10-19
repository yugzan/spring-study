Gmail Deliver
=============
A sample code for gmail send a e-mail

##Usage
```yaml

maildeliver:
  username: "sender gmail account"
  password: "password"
---
```

```java
//      create MailDeliver handler mail sender
      MailDeliver mailDeliver = MailDeliver.builder()
              .setHost(prop.getHost())
              .setPort(prop.getPort())
              .setUserName(prop.getUsername())
              .setPassword(prop.getPassword())
              .build();
//      setting mail address which is receiver address.
      mailDeliver.setRecipients(recipientsArray);
//      send mail (title , message)
      mailDeliver.send("[important]Check your account is current", "This message is fake.");
```