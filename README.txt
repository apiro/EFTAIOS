Welcome to EFTAIOS version by group cg_38.
Our version is based on a client/server, publisher/subscriber architecture, supported by GAME-event (GameEvent) and NOTIFICATION-event (NotifyEvent) communication, working as follows:
First, you must start the server, running the class it.polimi.ingsw.cg_38.controller.ServerController.
Then, start an arbitrary number of clients, running the class it.polimi.ingsw.cg_38.client.PlayerMain.
At this point, you can choose the user interface, either [CLI] or [GUI], and the communication mode, either [SOCKET] or [RMI].
The same game, in fact, can be played by users in [RMI] mode and others in [SOCKET] mode. To this end, we have added a communication layer that totally hides the communication mode.
The same happens with the user interface, since the players of the same game can be using either [GUI] or [CLI]. 
Network messages are based on event-driven architecture. The client generates the game events (GameEvent) and sends them to the server. The server generates the corresponding action (GameAction), performs it on the model, generates a series of notification events and decides whether they are to be sent based on the Pub/Sub paradigm or the Client/Server paradigm. 
When the client receives the notification event, it generates the corresponding action (NotifyAction), renders it on the chosen interface and returns either a game event, in case another communication with the server is necessary to manage the event, or a fixed value, if the event has already been fully managed.
