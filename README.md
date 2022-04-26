# Assignment6 project: Build a Chatroom
CS5010 repo for Spring2022; Northeastern Seattle.

## Features
### Server
Once the server starts up, it would be able to handle up to 10 clients connected at
a single time. It is responsible for listening for commands from the client as well
as sending messages to the client. 
### Client
The client will maintain the socket to listen for incoming messages from the server (public or private messages),
as well as listen to the UI (terminal) for messages from the user to send to the server. 
The client would list all commands when the user types a ?
• logoff: sends a DISCONNECT_MESSAGE to the server

• who: sends a QUERY_CONNECTED_USERS to the server

• @user: sends a DIRECT_MESSAGE to the specified user to the server

• @all: sends a BROADCAST_MESSAGE to the server, to be sent to all users connected

• !user: sends a SEND_INSULT message to the server, to be sent to the specified user

No messages are sent unless the @user, @all, !user commands are used. 
### Chatroom Protocol
The application data for the chatroom consists of a sequence of data. 
The format depends on
what kind of message is being sent. 
All frames of application data begin with a message
identifier. 



## Usage
In the client User Interface, server's IP address (or localhost) and port are required to be passed in.


## Assumptions
In the client:
1. if there are multiple commands (for @user,!user and @all), only the command at the
top of the input will be use. 
2. if the user's input doesn't match any command, the program will treat it as "@all".
3. @all including the client itself.
4. if CONNECT_RESPONSE returns false, the client won't be able to log off. Only it return true
can the program be terminated.
5. if the server logs off before client, client would keeps connecting server before server back online.


