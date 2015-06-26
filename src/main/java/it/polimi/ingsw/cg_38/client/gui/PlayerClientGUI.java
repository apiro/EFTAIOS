package it.polimi.ingsw.cg_38.client.gui;

import it.polimi.ingsw.cg_38.client.Client;
import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.client.notifyAction.NotifyAction;
import it.polimi.ingsw.cg_38.client.notifyAction.NotifyActionCreator;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
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

/**
 * Classe principale del Client connesso con GUI
 * */
public class PlayerClientGUI implements PlayerClient {

	private Map map;
	private EventSubscribe evt;
	
	/**
	 * Oggetto Runnable che serve per lanciare il thread di invio messaggi al server
	 * */
	private Client client;
	private PlayerClientState playerClientState;
	private it.polimi.ingsw.cg_38.model.Player player;
	private Boolean isMyTurn = false;
	private Boolean clientAlive = false;
	private Boolean isInterfaceBlocked = true;
	/**
	 * Thread che invia i messaggi al server. Indifferentemente ClientRMI o ClientSocket perche il loro funzionamento
	 * è mascherato dall'interfaccia comune Client
	 * */
	private Thread gameEventSender;
	/**
	 * Logger assegnato alla gui
	 * */
	private Logger logger;
	/**
	 * Gestore della Gui
	 * */
	private UXStarter uxHandler;

	final Boolean[] alive = new Boolean[1];
	/**
	 * Numero colonne della board
	 * */
	final static int BSIZEW = 23;
	/**
	 * Numero di righe della board
	 * */
	final static int BSIZEH = 14;
	/**
	 * Altezza dell'esagono
	 * */
	final static int HEXSIZE = 45;
	final static int BORDERS = 15;

	/**
	 * Array multidimensionale che definisce la composizioen della scacchiera
	 * */
	private int[][] board = new int[BSIZEH][BSIZEW];
	/**
	 * coda di messaggi da inviare generati dalla gui
	 * */
	private Queue<Event> toSend;
	/**
	 * coda di messaggi da processare inviati dal server
	 * */
	private Queue<Event> toProcess;
	private String connection;
	private Logger loggerChat;

	public Boolean getClientAlive() {
		return clientAlive;
	}

	/**
	 *  Questo costruttore è particolarmente importante perchè chiama alcuni metodi di questa classe con una 
	 *  sequenza tale tale da costruire in modo rigoroso l'oggetto client e renderlo utilizzabile dalle azioni
	 *  di gioco.
	 * */
	public PlayerClientGUI() {
		alive[0] = true;
		logger = new LoggerCLI();
		this.waitForLoadingUX();
		this.initFields();
		this.createClient();
		this.startSender();
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

	/**
	 * Questo metodo manda un messaggio alla Gui in modo tale da renderla visibile
	 * */
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

	/**
	 * Inizializza il gestore della GUI
	 * */
	public void waitForLoadingUX() {
		Logger[] params = new Logger[1];
		
		params[0] = logger;
		uxHandler = new UXStarter(params);
	}

	/**
	 * Lancia la Gui e crea l'interfaccia grafica 
	 * */
	public void createClient() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				Queue<Event>[] queue =(Queue<Event>[]) new Queue<?>[1];
				queue[0] = toSend;
				connection = uxHandler.getUx().getConnection();
				player = uxHandler.getUx().prepareGUI(queue);
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

	/**
	 * Inizializza i campi del client
	 * */
	public void initFields() {
		playerClientState = PlayerClientState.INIT;
		this.toProcess = new ConcurrentLinkedQueue<Event>();
		this.toSend = new ConcurrentLinkedQueue<Event>();
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

	/**
	 * Metodo che in base alla variabile isInterfaceIsBlocked setta i componenti dell'interfaccia. 
	 * La variabile isInterfaceBlocked è modificata costantemente dai metodi render delle azioni di notifica
	 * facendo cosi che l'utilizzo dell'interfaccia sia guidata dalle azioni dell'utente.
	 * */
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

	/**
	 * Metodo che prende un evento dalla coda ogni ciclo di while e lo processa. Dopo di che aggiorna le carte del
	 * giocatore per avere sempre una situazione aggiornata.
	 * */
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

	/**
	 * Metodo del client che processa l'evento che gli viene passato, ne genera l'azione di notifica e renderizza l'effetto 
	 * sull' interfaccia.
	 * @param msg evento da processare
	 * */
	@Override
	public void process(Event msg) {
		if(((NotifyEvent)msg).getType() != null) {
			System.out.println("----------------------------------------------------------------------\n");
			System.err.println("Recieving " + msg.toString() + " ...\n");
			NotifyAction action = (NotifyAction) NotifyActionCreator
					.createNotifyAction(msg);
			GameEvent gamEvt = null;
			if (action.isPossible(this)) {
				gamEvt = action.render(this);
				uxHandler.getUx().setTitle("ESCAPE FROM THE ALIENS IN OUTER SPACE - PLAYER: " + player.getName() + " - TURNO: " + player.getNumTurniGiocati());
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
	}

	/**
	 * Metodo che setta alcune variabili della classe HexagonHandler e inizializza la board trasformando un array 
	 * monodimensionale in uno multidimensionale
	 * */
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

	/**
	 * Metodo che fa partire il thread di invio messaggi di gioco al server
	 * */
	@Override
	public void startSender() {
		client = Client.clientCreator(connection, toSend, toProcess, evt);
		gameEventSender = new Thread(client, "GameEventSender");
		gameEventSender.start();
	}

	/**
	 * Metodo che aggiunge gli actionlistener agli oggetti della GUI
	 * */
	private void handleActionListeners() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				uxHandler.getUx().addButtonActionListener(0,
						new ActionListener() {
							
							/**
							 * Quando viene premuto il pulsante invia un evento di ritiro
							 * */
							@Override
							public void actionPerformed(ActionEvent e) {
								synchronized (toSend) {
									toSend.add(new EventRetired(player));
								}
							}

						});

				uxHandler.getUx().getInput()
						.addActionListener(new ActionListener() {

							/**
							 * Quando viene premuto il pulsante invia un evento di Chat
							 * */
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

							/**
							 * Quando viene premuto il pulsante invia un evento di Fine Turno
							 * */
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
								
								/**
								 * Genera dinamicamente una classe a partire dal pulsante schiacciato
								 * */
								private Class<?> generateClass(ActionEvent e, String s) {
									Class<?> myClass = null;
									
									try {
										myClass = Class.forName(s);
									} catch (ClassNotFoundException e1) {
										logger.print("You haven't selected a card ! retry ...");
									}
									return myClass;
								}
								
								/**
								 * Metodo che gestisce l'invio dell'evento relativo al pulsante schiacciato dall'utente.
								 * Costruisce il costruttore della classe appena creata e gestisce l'invio dell'evento
								 * corrispondente
								 * */
								@Override
								public void actionPerformed(ActionEvent e) {
									String choice = uxHandler.getUx()
											.askForUseCardOrRejectCard();
									Sector sec = null;
									ObjectCard card = null;
									String s = "it.polimi.ingsw.cg_38.controller.gameEvent.Event"
											+ ((JButton) e.getSource())
													.getText();
									Class<?> myClass = this.generateClass(e,s);
									
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
										if (("R").equals(choice)) {
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
										if (("it.polimi.ingsw.cg_38.controller.gameEvent.EventSPOTLIGHT").equals(s)) {
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
									} catch (SecurityException e1) {
										logger.print("Security Exception !");
									}
									Object instance = null;
									try {
										if (("it.polimi.ingsw.cg_38.controller.gameEvent.EventSPOTLIGHT").equals(s)) {
											instance = constructor.newInstance(
													player, sec, card);
										} else {
											instance = constructor.newInstance(
													player, card);
										}
									} catch (InstantiationException e1) {
										logger.print("Problems with the instantiation of the object ...");
									} catch (IllegalAccessException e1) {
										logger.print("Problems with access...");
									} catch (IllegalArgumentException e1) {
										logger.print("Problems with arguments...");
									} catch (InvocationTargetException e1) {
										logger.print("Problems with the invocation target...");
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

	/**
	 * Termina il processo Client dopo 15 sec dalla sua chiamata
	 * */
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

	/**
	 * Richiama il metodo colora hatch della gui, in un ambiente protetto dato da SwingUtilities che permette di 
	 * lavorare dentro l'EDT
	 * */
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

	/**
	 * Richiama il metodo updateCards() della gui, in un ambiente protetto dato da SwingUtilities che permette di 
	 * lavorare dentro l'EDT
	 * */
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

	/**
	 * Richiama il metodo updateMovements() della gui, in un ambiente protetto dato da SwingUtilities che permette di 
	 * lavorare dentro l'EDT
	 * */
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
