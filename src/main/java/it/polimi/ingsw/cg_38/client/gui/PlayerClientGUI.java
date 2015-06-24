package it.polimi.ingsw.cg_38.client.gui;

import it.polimi.ingsw.cg_38.client.Client;
import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.client.notifyAction.NotifyAction;
import it.polimi.ingsw.cg_38.client.notifyAction.NotifyActionCreator;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventChat;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRejectCard;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRetired;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;
import it.polimi.ingsw.cg_38.controller.logger.LoggerGUI;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.map.Map;
import it.polimi.ingsw.cg_38.model.map.Sector;

import java.awt.event.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.*;

public class PlayerClientGUI implements PlayerClient {

	private Map map;
	private EventSubscribe evt;
	private Client client;
	private PlayerClientState playerClientState;
	private it.polimi.ingsw.cg_38.model.Player player;
	private Boolean isMyTurn = false;
	private Boolean clientAlive = false;
	private Boolean isInterfaceBlocked = true;
	private Thread gameEventSender;
	private Logger logger;
	private UXStarter uxHandler;

	final Boolean[] alive = new Boolean[1];
	final static int BSIZEW = 23;
	final static int BSIZEH = 14;
	final static int HEXSIZE = 45;
	final static int BORDERS = 15;

	private int[][] board = new int[BSIZEH][BSIZEW];
	private Queue<Event> toSend;
	private Queue<Event> toProcess;
	private String connection;
	private Logger loggerChat;

	public Boolean getClientAlive() {
		return clientAlive;
	}

	public PlayerClientGUI() {
		alive[0] = true;
		logger = new LoggerCLI();
		this.waitForLoadingUX();
		this.createClient();
		this.initFields();
		while (!clientAlive) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				logger.print("Can't awake the thread !");
			}
			Event msg = toProcess.poll();
			if (msg != null) {
				this.process(msg);
			}
		}
		init();
		showGUI();
		this.handleActionListeners();
		logger = new LoggerGUI(uxHandler.getUx().getText1(), uxHandler.getUx());
		loggerChat = new LoggerGUI(uxHandler.getUx().getText3(),
				uxHandler.getUx());
	}

	public void showGUI() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				uxHandler.getUx().showUX(board, toSend, player, map);
			}
		};
		try {
			SwingUtilities.invokeAndWait(r);
		} catch (InvocationTargetException e) {
			logger.print("Invocation Exception in PlayerClientGUI ...");
		} catch (InterruptedException e) {
			return;
		}
	}

	public void waitForLoadingUX() {
		Logger[] arrLog = new Logger[1];
		arrLog[0] = logger;
		uxHandler = new UXStarter(arrLog);
	}

	public void createClient() {
		Runnable r = new Runnable() {
			@Override
			public void run() {

				connection = uxHandler.getUx().getConnection();
				player = uxHandler.getUx().prepareGUI();
				evt = uxHandler.getUx().generateEventSub(player);
			}
		};
		try {
			SwingUtilities.invokeAndWait(r);
		} catch (InvocationTargetException e) {
			logger.print("Invocation Exception in PlayerClientGUI ...");
		} catch (InterruptedException e) {
			return;
		}
	}

	public void initFields() {
		playerClientState = PlayerClientState.INIT;
		this.toProcess = new ConcurrentLinkedQueue<Event>();
		this.toSend = new ConcurrentLinkedQueue<Event>();
		this.startSender();
	}

	@Override
	public void setClientAlive(Boolean clientAlive) {
		this.clientAlive = clientAlive;
	}

	public void setAlive(Boolean b) {
		this.alive[0] = b;
	}

	public static void main(String[] args) {

		PlayerClientGUI plcGUI = new PlayerClientGUI();
		plcGUI.run();
	}

	public void blockInterf() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				uxHandler.getUx().blockInterf(isInterfaceBlocked);
			}
		};
		try {
			SwingUtilities.invokeAndWait(r);
		} catch (InvocationTargetException e) {
			logger.print("Invocation Exception in PlayerClientGUI ...");
		} catch (InterruptedException e) {
			return;
		}
	}

	@Override
	public void run() {
		while (alive[0]) {
			Event msg = toProcess.poll();
			if (msg != null) {
				this.process(msg);
				this.updateCards();
			}
			this.blockInterf();
		}
		logger.print("GoodBye !");
		Thread.currentThread().interrupt();
	}

	@Override
	public void process(Event msg) {
		System.out
				.println("----------------------------------------------------------------------\n");
		System.err.println("Recieving " + msg.toString() + " ...\n");
		NotifyAction action = (NotifyAction) NotifyActionCreator
				.createNotifyAction(msg);
		GameEvent gamEvt = null;
		if (action.isPossible(this)) {
			gamEvt = action.render(this);
			if (gamEvt != null) {
				if (gamEvt instanceof EventContinue)
					return;
				this.toSend.add(gamEvt);
			} else {
				if (!isInterfaceBlocked) {
				} else
					return;
			}
		} else {
			System.out.println("Error in parsing the notifyEvent ...");
		}
	}

	@Override
	public void init() {

		HexagonHandler.setXYasVertex(false);

		HexagonHandler.setHeight(HEXSIZE);
		HexagonHandler.setBorders(BORDERS);

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = map.getConfiguration()[j + 23 * i];
			}
		}
	}

	@Override
	public void startSender() {
		client = Client.clientCreator(connection, toSend, toProcess, evt);
		gameEventSender = new Thread(client, "GameEventSender");
		gameEventSender.start();
	}

	private void handleActionListeners() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				uxHandler.getUx().addButtonActionListener(0,
						new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								synchronized (toSend) {
									toSend.add(new EventRetired(player));
								}
							}

						});

				uxHandler.getUx().getInput()
						.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								synchronized (toSend) {
									toSend.add(new EventChat(player,
											((JTextField) e.getSource())
													.getText()));
								}
								((JTextField) e.getSource()).setText("");
							}

						});

				uxHandler.getUx().addButtonActionListener(1,
						new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								synchronized (toSend) {
									Event evt = new EventFinishTurn(player);
									toSend.add(evt);
								}
							}
						});

				for (int i = 2; i < 5; i++) {
					uxHandler.getUx().addButtonActionListener(i,
							new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									String choice = uxHandler.getUx()
											.askForUseCardOrRejectCard();
									Sector sec = null;
									ObjectCard card = null;
									String s = "it.polimi.ingsw.cg_38.controller.gameEvent.Event"
											+ ((JButton) e.getSource())
													.getText();
									System.out.println(s);
									Class<?> myClass = null;
									try {
										myClass = Class.forName(s);
									} catch (ClassNotFoundException e1) {
										logger.print("The requested Class is not available !");
									}
									Constructor<?> constructor = null;
									try {
										for (ObjectCard c : player.getAvatar()
												.getMyCards()) {
											if (c.getType()
													.toString()
													.equals(((JButton) e
															.getSource())
															.getText())) {
												card = c;
											}
										}
										if (choice.equals("R")) {
											player.getAvatar().getMyCards()
													.remove(card);
											uxHandler.getUx().updateCards(
													player, toSend, map);
											((JButton) e.getSource())
													.setText("Use Card");
											synchronized (toSend) {
												toSend.add(new EventRejectCard(
														player, card));
												return;
											}
										}
										if (s.equals("it.polimi.ingsw.cg_38.controller.gameEvent.EventSpotLight")) {
											sec = uxHandler.getUx()
													.askForMoveCoordinates(map);
											constructor = myClass
													.getConstructor(
															it.polimi.ingsw.cg_38.model.Player.class,
															it.polimi.ingsw.cg_38.model.map.Sector.class,
															it.polimi.ingsw.cg_38.model.deck.Card.class);
										} else {
											constructor = myClass
													.getConstructor(
															it.polimi.ingsw.cg_38.model.Player.class,
															it.polimi.ingsw.cg_38.model.deck.Card.class);
										}
									} catch (NoSuchMethodException e1) {
										logger.print("The constructor is not available for the requested class !");
										e1.printStackTrace();
									} catch (SecurityException e1) {
										e1.printStackTrace();
									}
									Object instance = null;
									try {
										if (s.equals("it.polimi.ingsw.cg_38.controller.gameEvent.EventSpotLight")) {
											instance = constructor.newInstance(
													player, sec, card);
										} else {
											instance = constructor.newInstance(
													player, card);
										}
									} catch (InstantiationException e1) {
										logger.print("Problems with the instantiation of the object ...");
										e1.printStackTrace();
									} catch (IllegalAccessException e1) {
										e1.printStackTrace();
									} catch (IllegalArgumentException e1) {
										e1.printStackTrace();
									} catch (InvocationTargetException e1) {
										e1.printStackTrace();
									}
									player.getAvatar().getMyCards()
											.remove(card);
									uxHandler.getUx().updateCards(player,
											toSend, map);
									((JButton) e.getSource())
											.setText("Use Card");
									GameEvent evt = (GameEvent) instance;
									synchronized (toSend) {
										toSend.add(evt);
									}
								}
							});
				}
			}
		};
		try {
			SwingUtilities.invokeAndWait(r);
		} catch (InvocationTargetException e) {
			logger.print("Invocation Exception in PlayerClientGUI ...");
		} catch (InterruptedException e) {
			return;
		}
	}

	public Boolean getIsInterfaceBlocked() {
		return isInterfaceBlocked;
	}

	@Override
	public void setIsInterfaceBlocked(Boolean isInterfaceBlocked) {
		this.isInterfaceBlocked = isInterfaceBlocked;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public it.polimi.ingsw.cg_38.model.Player getPlayer() {
		return player;
	}

	@Override
	public void setPlayerClientState(PlayerClientState playerClientState) {
		this.playerClientState = playerClientState;
	}

	@Override
	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public PlayerClientState getPlayerClientState() {
		return playerClientState;
	}

	@Override
	public void closeClient() {
		logger.print("CLOSING CLIENT TERMINAL ...");
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			logger.print("Problems with interrupting process ...");
		}

		System.exit(0);
	}

	@Override
	public void setMap(Map map) {
		this.map = map;
	}

	@Override
	public Map getMap() {
		return this.map;
	}

	@Override
	public void setIsMyTurn(Boolean b) {
		this.isMyTurn = b;
	}

	@Override
	public void paintHatch(final Boolean bool, final Sector sec) {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				uxHandler.getUx().paintHatch(bool, sec);
			}
		};
		try {
			SwingUtilities.invokeAndWait(r);
		} catch (InvocationTargetException e) {
			logger.print("Invocation Exception in PlayerClientGUI ...");
		} catch (InterruptedException e) {
			return;
		}
	}

	@Override
	public Sector askForMoveCoordinates() {
		return this.uxHandler.getUx().askForMoveCoordinates(map);
	}

	@Override
	public void updateCards() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				uxHandler.getUx().updateCards(player, toSend, map);
			}
		};
		try {
			SwingUtilities.invokeAndWait(r);
		} catch (InvocationTargetException e) {
			logger.print("Invocation Exception in PlayerClientGUI ...");
		} catch (InterruptedException e) {
			return;
		}
	}

	@Override
	public void updateMovements() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				uxHandler.getUx().updateMovements(player);
			}
		};
		try {
			SwingUtilities.invokeAndWait(r);
		} catch (InvocationTargetException e) {
			logger.print("Invocation Exception in PlayerClientGUI ...");
		} catch (InterruptedException e) {
			return;
		}
	}

	public Boolean getIsMyTurn() {
		return isMyTurn;
	}

	@Override
	public Logger getLoggerChat() {
		return loggerChat;
	}
}
