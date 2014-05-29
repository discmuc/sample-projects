package org.code2bytes.samples.xmpp;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManager.MatchMode;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.filetransfer.FileTransfer.Status;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;

public class Main {

	public static void main(String[] args) {
		try {
			ConnectionConfiguration config = new ConnectionConfiguration(
					"192.168.100.86", 5222);
			config.setSecurityMode(SecurityMode.disabled);
			final XMPPConnection connection = new XMPPTCPConnection(config);
			final Roster roster = connection.getRoster();
			roster.addRosterListener(new RosterListener() {

				@Override
				public void presenceChanged(Presence presence) {
					System.err.println("Presence change: " + presence.getFrom()
							+ " -> " + presence);
				}

				@Override
				public void entriesUpdated(Collection<String> addresses) {
					addresses.stream().forEach(
							a -> System.err.println("Update: " + a));
				}

				@Override
				public void entriesDeleted(Collection<String> addresses) {
					addresses.stream().forEach(
							a -> System.err.println("Remove: " + a));
				}

				@Override
				public void entriesAdded(Collection<String> addresses) {
					addresses.stream().forEach(
							a -> System.err.println("Add: " + a));
				}
			});

			connection.connect();
			connection.login("test", "secure");

			long count = connection.getRoster().getEntries().parallelStream()
					.count();
			System.out.println("Roster contains " + count + " entries.");

			connection
					.getRoster()
					.getEntries()
					.parallelStream()
					.forEach(
							r -> System.out.println("  -> " + r.getUser()
									+ " (" + roster.getPresence(r.getUser())
									+ "/" + r.getStatus() + ")"));

			Presence presence = new Presence(Type.subscribe);
			presence.setTo("disc@openfire");
			connection.sendPacket(presence);

			FileTransferManager transferManager = new FileTransferManager(
					connection);
			transferManager.addFileTransferListener(new FileTransferListener() {

				@Override
				public void fileTransferRequest(FileTransferRequest request) {
					try {
						final IncomingFileTransfer fileTransfer = request
								.accept();
						new Thread(new Runnable() {

							@Override
							public void run() {
								while (!fileTransfer.isDone()) {
									if (fileTransfer.getStatus().equals(
											Status.error)) {
										System.err.println("ERROR! "
												+ fileTransfer.getError());
										break;
									} else {
										System.err.println(fileTransfer
												.getStatus());
										System.err.println(fileTransfer
												.getProgress());
										try {
											Thread.sleep(500);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
								}
							}
						}).start();
						fileTransfer.recieveFile(new File("temp.dat"));
					} catch (SmackException e) {
						e.printStackTrace();
					}
				}
			});

			ChatManager manager = ChatManager.getInstanceFor(connection);
			MatchMode matchMode = manager.getMatchMode();
			System.out.println("Matchmode: " + matchMode);
			Chat chat = manager.createChat("disc@openfire",
					new MyMessageListener(connection));
			System.out.println("Chat created with #" + chat.getThreadID());
			chat.sendMessage("Howdy!");

			manager.addChatListener(new ChatManagerListener() {

				@Override
				public void chatCreated(Chat chat, boolean createdLocally) {
					chat.addMessageListener(new MyMessageListener(connection));
				}
			});

			System.out.println("Waiting for input ...");

			while (true) {
				// empty
			}

		} catch (SmackException | IOException | XMPPException e) {
			e.printStackTrace();
		}
	}

}
