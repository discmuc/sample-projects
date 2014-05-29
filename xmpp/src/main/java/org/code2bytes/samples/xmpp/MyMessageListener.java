package org.code2bytes.samples.xmpp;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;

public class MyMessageListener implements MessageListener {

	private XMPPConnection connection;

	public MyMessageListener(XMPPConnection connection) {
		this.connection = connection;
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		if (message.getBody() != null) {
			System.out.println("Received message ("
					+ chat.getParticipant()
					+ " / "
					+ connection.getRoster()
							.getPresences(chat.getParticipant()) + " / "
					+ chat.getThreadID() + "): " + message);
		} else {
			System.err.println("Received status (" + chat.getParticipant()
					+ " / " + chat.getThreadID() + "): " + message);
		}
		if (message.toString().matches(".*EXIT.*")) {
			try {
				System.out.println("Leaving ...");
				connection.disconnect();
				System.exit(0);
			} catch (NotConnectedException e) {
				e.printStackTrace();
			}
		}

	}
}